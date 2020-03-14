package net.faithgen.gallery

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import br.com.liveo.searchliveo.SearchLiveo
import kotlinx.android.synthetic.main.activity_gallery.*
import net.faithgen.gallery.adapters.AlbumAdapter
import net.faithgen.gallery.models.Album
import net.faithgen.gallery.utils.AlbumsData
import net.faithgen.gallery.utils.Constants
import net.faithgen.sdk.FaithGenActivity
import net.faithgen.sdk.http.ErrorResponse
import net.faithgen.sdk.http.FaithGenAPI
import net.faithgen.sdk.http.Pagination
import net.faithgen.sdk.http.types.ServerResponse
import net.faithgen.sdk.singletons.GSONSingleton
import net.faithgen.sdk.singletons.GSONSingleton.Companion.instance
import net.faithgen.sdk.utils.Dialogs
import net.innoflash.iosview.recyclerview.RecyclerTouchListener
import net.innoflash.iosview.recyclerview.RecyclerViewClickListener
import net.innoflash.iosview.swipelib.SwipeRefreshLayout

class GalleryActivity : FaithGenActivity(), RecyclerViewClickListener, SwipeRefreshLayout.OnRefreshListener {
    private var filterText = ""
    private var albums: MutableList<Album>? = null
    private var pagination: Pagination? = null
    private var albumsData: AlbumsData? = null
    private var albumAdapter: AlbumAdapter? = null

    private val params : MutableMap<String, String> = mutableMapOf()

    private val faithGenAPI : FaithGenAPI  by lazy { FaithGenAPI(this) }

    override fun getPageTitle(): String? {
        return Constants.GALLERY
    }

    override fun getPageIcon(): Int {
        return R.drawable.gallery_icon
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
            albumsView.layoutManager = GridLayoutManager(this, 2)
        else albumsView.layoutManager = GridLayoutManager(this, 3)

        albumsView.addOnItemTouchListener(RecyclerTouchListener(this, albumsView, this))

        swipeRefreshLayout.setPullPosition(Gravity.BOTTOM)
        swipeRefreshLayout.setOnRefreshListener(this)

        search_liveo.with(this) { charSequence ->
            filterText = charSequence as String
            loadAlbums(Constants.ALBUMS, true)
        }.showVoice()
                .hideKeyboardAfterSearch()
                .hideSearch {
                    toolbar.visibility = View.VISIBLE
                    if (filterText.isNotEmpty()) {
                        filterText = ""
                        search_liveo.text(filterText)
                        loadAlbums(Constants.ALBUMS, true)
                    }
                }.build()
    }

    override fun onStart() {
        super.onStart()
        if (albums.isNullOrEmpty())
            loadAlbums(Constants.ALBUMS, true)
    }

    private fun loadAlbums(url: String, reload: Boolean) {
        params[Constants.FILTER_TEXT] = filterText
        faithGenAPI
                .setProcess(Constants.FETCHING_ALBUMS)
                .setParams(params as HashMap<String, String>)
                .setServerResponse(object : ServerResponse() {
                    override fun onServerResponse(serverResponse: String) {
                        swipeRefreshLayout.isRefreshing = false
                        pagination = GSONSingleton.instance.gson.fromJson(serverResponse, Pagination::class.java)
                        albumsData = GSONSingleton.instance.gson.fromJson(serverResponse, AlbumsData::class.java)

                        if (reload || albums.isNullOrEmpty()) {
                            albums = albumsData!!.albums
                            albumAdapter = AlbumAdapter(this@GalleryActivity, albums!!)
                            albumsView.adapter = albumAdapter
                        } else {
                            albums!!.addAll(albumsData!!.albums)
                            albumAdapter!!.notifyDataSetChanged()
                        }
                    }

                    override fun onError(errorResponse: ErrorResponse?) {
                        Dialogs.showOkDialog(this@GalleryActivity, errorResponse!!.message, pagination == null)
                        swipeRefreshLayout.isRefreshing = false
                    }
                })
                .request(url)
    }

    override fun onClick(view: View?, position: Int) {
       val intent = Intent(this, AlbumActivity::class.java)
        intent.putExtra(Constants.ALBUM_, instance.gson.toJson(albums!![position]))
        startActivity(intent)
    }

    override fun onLongClick(view: View?, position: Int) {
    }

    override fun onRefresh() {
        if (pagination == null || pagination!!.links.next.isNullOrBlank())
            swipeRefreshLayout.isRefreshing = false 
        else loadAlbums(pagination!!.links.next, false)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            if (requestCode == SearchLiveo.REQUEST_CODE_SPEECH_INPUT) {
                search_liveo.resultVoice(requestCode, resultCode, data)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        faithGenAPI.cancelRequests()
    }
}