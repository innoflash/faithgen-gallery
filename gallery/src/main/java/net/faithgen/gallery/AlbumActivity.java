package net.faithgen.gallery;

import android.content.res.Configuration;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import net.faithgen.gallery.adapters.ImagesAdapter;
import net.faithgen.gallery.dialogs.SlideshowDialog;
import net.faithgen.gallery.models.Album;
import net.faithgen.gallery.models.Image;
import net.faithgen.gallery.utils.Constants;
import net.faithgen.gallery.utils.ImagesData;
import net.faithgen.sdk.FaithGenActivity;
import net.faithgen.sdk.SDK;
import net.faithgen.sdk.comments.CommentsSettings;
import net.faithgen.sdk.http.API;
import net.faithgen.sdk.http.ErrorResponse;
import net.faithgen.sdk.http.Pagination;
import net.faithgen.sdk.http.types.ServerResponse;
import net.faithgen.sdk.singletons.GSONSingleton;
import net.faithgen.sdk.utils.Dialogs;
import net.innoflash.iosview.recyclerview.RecyclerTouchListener;
import net.innoflash.iosview.recyclerview.RecyclerViewClickListener;
import net.innoflash.iosview.swipelib.SwipeRefreshLayout;

import java.util.HashMap;
import java.util.List;

public class AlbumActivity extends FaithGenActivity implements RecyclerViewClickListener, SwipeRefreshLayout.OnRefreshListener {

    private TextView albumName;
    private TextView albumDescription;
    private RecyclerView imagesView;
   // private SwipeRefreshLayout swipeRefreshLayout;
    private HashMap<String, String> params;
    private ImagesAdapter imagesAdapter;
    private GridLayoutManager gridLayoutManager;
    private ImagesData imagesData;
    private SlideshowDialog slideshowDialog;
    private List<Image> images;
    private Pagination pagination;
    private Album album;

    @Override
    public String getPageTitle() {
        return Constants.ALBUM;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        params = new HashMap<>();
        album = GSONSingleton.getInstance().getGson().fromJson(getIntent().getStringExtra(Album.ALBUM), Album.class);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            gridLayoutManager = new GridLayoutManager(this, 2);
        else gridLayoutManager = new GridLayoutManager(this, 3);

        albumName = findViewById(R.id.albumName);
        albumDescription = findViewById(R.id.albumDescription);
        imagesView = findViewById(R.id.imagesView);
    //    swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

    //    swipeRefreshLayout.setPullPosition(Gravity.BOTTOM);
    //    swipeRefreshLayout.setOnRefreshListener(this);
        imagesView.setLayoutManager(gridLayoutManager);
        imagesView.addOnItemTouchListener(new RecyclerTouchListener(this, imagesView, this));

        setOnOptionsClicked(R.drawable.ic_message_blue18dp, view -> {
            SDK.openComments(this, new CommentsSettings.Builder()
                    .setCategory("albums/")
                    .setItemId(album.getId())
                    .setTitle(album.getName())
                    .build());
        });
    }

    @Override
    protected void onStart() {

        super.onStart();


        params.put(Album.ALBUM_ID, album.getId());
        params.put(Album.LIMIT, "100");

        albumName.setText(album.getName());
        albumDescription.setText(album.getDescription());

        if (images == null || images.size() == 0)
            loadImages(Constants.ALBUMS_VIEW);
    }

    void loadImages(String url) {
        API.get(this, url, params, false, new ServerResponse() {
            @Override
            public void onServerResponse(String serverResponse) {
                Log.d("tag", "onServerResponse: " + serverResponse);
                pagination = GSONSingleton.getInstance().getGson().fromJson(serverResponse, Pagination.class);
                imagesData = GSONSingleton.getInstance().getGson().fromJson(serverResponse, ImagesData.class);

                if (images == null || images.size() == 0) {
                    images = imagesData.getImages();
                    imagesAdapter = new ImagesAdapter(AlbumActivity.this, images);
                    imagesView.setAdapter(imagesAdapter);
                } else {
                    images.addAll(imagesData.getImages());
                    imagesAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(ErrorResponse errorResponse) {
                super.onError(errorResponse);
                Dialogs.showOkDialog(AlbumActivity.this, errorResponse.getMessage(), false);
            }
        });
    }

    @Override
    public void onClick(View view, int position) {
        slideshowDialog = new SlideshowDialog(album.getName(), position, images);
        slideshowDialog.show(getSupportFragmentManager(), SlideshowDialog.TAG);
    }

    @Override
    public void onLongClick(View view, int position) {

    }

    @Override
    public void onRefresh() {
        /*if (pagination == null || pagination.getLinks().getNext() == null)
            swipeRefreshLayout.setRefreshing(false);
        else loadImages(pagination.getLinks().getNext());*/
    }
}
