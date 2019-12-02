package net.faithgen.gallery.viewholders;

import android.view.View;
import android.widget.ImageView;

import com.smarteist.autoimageslider.SliderViewAdapter;

import net.faithgen.gallery.R;

public class SliderHolder extends SliderViewAdapter.ViewHolder {
    private ImageView imageView;
    public SliderHolder(View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.image_view);
    }

    public ImageView getImageView() {
        return imageView;
    }
}
