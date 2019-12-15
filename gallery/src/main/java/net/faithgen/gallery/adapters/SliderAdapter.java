package net.faithgen.gallery.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

import net.faithgen.gallery.R;
import net.faithgen.gallery.models.Image;
import net.faithgen.gallery.viewholders.SliderHolder;

import java.util.List;

public class SliderAdapter extends SliderViewAdapter<SliderHolder> {

    private Context context;
    private List<Image> images;
    private ImageListener imageListener;
    private LayoutInflater inflater;

    public SliderAdapter(Context context, List<Image> images, ImageListener imageListener) {
        this.context = context;
        this.images = images;
        this.imageListener = imageListener;
        this.inflater = LayoutInflater.from(this.context);
    }

    @Override
    public SliderHolder onCreateViewHolder(ViewGroup parent) {
        return new SliderHolder(inflater.inflate(R.layout.list_item_grid_image, parent, false));
    }

    @Override
    public void onBindViewHolder(SliderHolder viewHolder, int position) {
        Picasso.get()
                .load(images.get(position).getAvatar().getOriginal())
                .error(R.drawable.image_placeholder)
                .placeholder(R.drawable.image_placeholder)
                .into(viewHolder.getImageView());

        viewHolder.getFloatingActionButton().setOnClickListener(v -> {
            if(imageListener != null) imageListener.onImageClicked(images.get(position));
        });
    }

    @Override
    public int getCount() {
        return images.size();
    }

    public interface ImageListener{
        void onImageClicked(Image image);
    }
}
