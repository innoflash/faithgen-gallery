package net.faithgen.gallery;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.View;

import net.faithgen.gallery.models.Album;
import net.faithgen.gallery.utils.AlbumsData;
import net.faithgen.gallery.utils.Constants;
import net.faithgen.sdk.FaithGenActivity;
import net.faithgen.sdk.http.API;
import net.faithgen.sdk.http.ErrorResponse;
import net.faithgen.sdk.http.Pagination;
import net.faithgen.sdk.http.types.ServerResponse;
import net.faithgen.sdk.singletons.GSONSingleton;
import net.innoflash.iosview.recyclerview.RecyclerTouchListener;
import net.innoflash.iosview.recyclerview.RecyclerViewClickListener;
import net.innoflash.iosview.swipelib.SwipeRefreshLayout;

import java.util.List;

public class GalleryActivity extends FaithGenActivity implements RecyclerViewClickListener, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView albumsView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private GridLayoutManager gridLayoutManager;
    private List<Album> albums;
    private Pagination pagination;
    private AlbumsData albumsData;

    @Override
    public String getPageTitle() {
        return Constants.GALLERY;
    }

    @Override
    public int getPageIcon() {
        return R.drawable.gallery_icon;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        albumsView = findViewById(R.id.albumsView);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            gridLayoutManager = new GridLayoutManager(this, 2);
        else gridLayoutManager = new GridLayoutManager(this, 3);

        swipeRefreshLayout.setPullPosition(Gravity.BOTTOM);
        swipeRefreshLayout.setOnRefreshListener(this);
        albumsView.setLayoutManager(gridLayoutManager);
        albumsView.addOnItemTouchListener(new RecyclerTouchListener(this, albumsView, this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (albums == null || albums.size() == 0)
            loadAlbums(Constants.ALBUMS);
    }

    private void loadAlbums(String url) {
        API.get(this, url, null, false, new ServerResponse() {
            @Override
            public void onServerResponse(String serverResponse) {
                swipeRefreshLayout.setRefreshing(false);
                Log.d("tag", "onServerResponse: " + serverResponse);
                pagination = GSONSingleton.getInstance().getGson().fromJson(serverResponse, Pagination.class);
                albumsData = GSONSingleton.getInstance().getGson().fromJson(serverResponse, AlbumsData.class);

                if(albums == null){
                    albums = albumsData.getAlbums();
                }else{
                    albums.addAll(albumsData.getAlbums());
                }
            }

            @Override
            public void onError(ErrorResponse errorResponse) {
                super.onError(errorResponse);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onClick(View view, int position) {

    }

    @Override
    public void onLongClick(View view, int position) {

    }

    @Override
    public void onRefresh() {
        if (pagination == null || pagination.getLinks().getNext() == null)
            swipeRefreshLayout.setRefreshing(false);
        else loadAlbums(pagination.getLinks().getNext());
    }
}
