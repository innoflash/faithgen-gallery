package net.faithgen.gallery.utils;

import com.google.gson.annotations.SerializedName;

import net.faithgen.gallery.models.Album;

import java.util.List;

public class AlbumsData {
    @SerializedName("data")
    private List<Album> albums;

    public List<Album> getAlbums() {
        return albums;
    }
}
