package net.faithgen.gallery.viewholders;

import android.view.View;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.smarteist.autoimageslider.SliderViewAdapter;

import net.faithgen.gallery.R;

public class SliderHolder extends SliderViewAdapter.ViewHolder {
    private ImageView imageView;
    private FloatingActionButton floatingActionButton;
    public SliderHolder(View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.image_view);
        floatingActionButton = itemView.findViewById(R.id.comment_image);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public FloatingActionButton getFloatingActionButton() {
        return floatingActionButton;
    }
}
