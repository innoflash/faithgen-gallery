package net.faithgen.gallery.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import net.faithgen.gallery.R;
import net.faithgen.gallery.models.Album;

public class AlbumView extends LinearLayout {
    private TextView nameTextView;
    private TextView detailsTextView;
    private ImageView imageView;
    private String name;
    private String details;
    private int image;
    private Album album;

    public AlbumView(Context context) {
        super(context);
        init();
    }

    public AlbumView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AlbumView);
        readAttributes(typedArray);
        typedArray.recycle();
    }

    private void readAttributes(TypedArray typedArray) {
        setName(typedArray.getString(R.styleable.AlbumView_album_name));
        setDetails(typedArray.getString(R.styleable.AlbumView_album_details));
        setImage(typedArray.getResourceId(R.styleable.AlbumView_album_image, R.drawable.image_placeholder));
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_album_item, this);
        nameTextView = findViewById(R.id.name);
        detailsTextView = findViewById(R.id.details);
        imageView = findViewById(R.id.image);
    }

    public TextView getNameTextView() {
        return nameTextView;
    }

    public TextView getDetailsTextView() {
        return detailsTextView;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public String getName() {
        return name;
    }

    public String getDetails() {
        return details;
    }

    public int getImage() {
        return image;
    }

    public void setName(String name) {
        this.name = name;
        getNameTextView().setText(name);
    }

    public void setDetails(String details) {
        this.details = details;
        getDetailsTextView().setText(details);
    }

    public void setImage(int image) {
        this.image = image;
        getImageView().setImageResource(image);
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
        setName(album.getName());
        setDetails(album.getDate().getApprox() + "\n" + album.getSize() + " images");

    }
}
