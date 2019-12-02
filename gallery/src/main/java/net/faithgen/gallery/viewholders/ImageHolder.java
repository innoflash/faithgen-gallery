package net.faithgen.gallery.viewholders;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.faithgen.gallery.R;

public class ImageHolder extends RecyclerView.ViewHolder {
    private ImageView imageView;

    public ImageHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.image_view);
    }

    public ImageView getImageView() {
        return imageView;
    }
}
