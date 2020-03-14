package net.faithgen.gallery

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_album.*
import net.faithgen.gallery.adapters.ImagesAdapter
import net.faithgen.gallery.dialogs.SlideshowDialog
import net.faithgen.gallery.models.Album
import net.faithgen.gallery.models.Image
import net.faithgen.gallery.utils.Constants
import net.faithgen.gallery.utils.ImagesData
import net.faithgen.sdk.FaithGenActivity
import net.faithgen.sdk.SDK
import net.faithgen.sdk.comments.CommentsSettings
import net.faithgen.sdk.http.ErrorResponse
import net.faithgen.sdk.http.FaithGenAPI
import net.faithgen.sdk.http.Pagination
import net.faithgen.sdk.http.types.ServerResponse
import net.faithgen.sdk.singletons.GSONSingleton
import net.faithgen.sdk.utils.Dialogs
import net.innoflash.iosview.recyclerview.RecyclerTouchListener
import net.innoflash.iosview.recyclerview.RecyclerViewClickListener

class AlbumActivity : FaithGenActivity(), RecyclerViewClickListener {
    private var images: MutableList<Image>? = null
    private var pagination: Pagination? = null
    private var imagesAdapter: ImagesAdapter? = null

    private val faithGenAPI: FaithGenAPI by lazy { FaithGenAPI(this) }

    private val album: Album by lazy { GSONSingleton.instance.gson.fromJson(intent.getStringExtra(Constants.ALBUM_), Album::class.java) }

    private val params: MutableMap<String, String> = mutableMapOf(
            Pair(Constants.ALBUM_ID, album.id),
            Pair(Constants.LIMIT, "100")
    )

    override fun getPageTitle(): String? {
        return Constants.ALBUM
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album)

        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
            imagesView.layoutManager = GridLayoutManager(this, 2)
        else imagesView.layoutManager = GridLayoutManager(this, 3)

        imagesView.addOnItemTouchListener(RecyclerTouchListener(this, imagesView, this))

        setOnOptionsClicked(R.drawable.ic_message_blue18dp) {
            SDK.openComments(this, CommentsSettings.Builder()
                    .setCategory("albums/")
                    .setItemId(album.id)
                    .setTitle(album.name)
                    .build())
        }
    }

    override fun onStart() {
        super.onStart()

        albumName.text = album.name
        albumDescription.text = album.description

        if (images.isNullOrEmpty())
            loadImages(Constants.ALBUMS_VIEW)
    }

    private fun getServerResponse(): ServerResponse? {
        return object : ServerResponse() {
            override fun onServerResponse(serverResponse: String) {
                pagination = GSONSingleton.instance.gson.fromJson(serverResponse, Pagination::class.java)
                val imagesData = GSONSingleton.instance.gson.fromJson(serverResponse, ImagesData::class.java)
                if (images.isNullOrEmpty()) {
                    images = imagesData.images
                    imagesAdapter = ImagesAdapter(this@AlbumActivity, images!!)
                    imagesView.adapter = imagesAdapter
                } else {
                    images!!.addAll(imagesData.images)
                    imagesAdapter!!.notifyDataSetChanged()
                }
            }

            override fun onError(errorResponse: ErrorResponse?) {
                Dialogs.showOkDialog(this@AlbumActivity, errorResponse!!.message, false)
            }
        }
    }

    fun loadImages(url: String?) {
        faithGenAPI
                .setParams(params as HashMap<String, String>)
                .setProcess(Constants.OPENING_ALBUM)
                .setServerResponse(getServerResponse())
                .request(url!!)
    }

    override fun onClick(view: View?, position: Int) {
        val slideshowDialog = SlideshowDialog(album.name, position, images)
        slideshowDialog.show(supportFragmentManager, SlideshowDialog.TAG)
    }

    override fun onLongClick(view: View?, position: Int) {
    }
}