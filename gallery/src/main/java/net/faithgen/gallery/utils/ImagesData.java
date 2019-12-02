package net.faithgen.gallery.utils;

import com.google.gson.annotations.SerializedName;

import net.faithgen.gallery.models.Image;

import java.util.List;

public class ImagesData {
    @SerializedName("data")
    private List<Image> images;

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}
