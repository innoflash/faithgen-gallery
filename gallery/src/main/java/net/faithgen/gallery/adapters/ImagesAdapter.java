package net.faithgen.gallery.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import net.faithgen.gallery.R;
import net.faithgen.gallery.models.Image;
import net.faithgen.gallery.viewholders.ImageHolder;

import java.util.List;

public class ImagesAdapter extends RecyclerView.Adapter<ImageHolder> {

    private Context context;
    private List<Image> images;
    private LayoutInflater layoutInflater;

    public ImagesAdapter(Context context, List<Image> images) {
        this.context = context;
        this.images = images;
        this.layoutInflater = LayoutInflater.from(this.context);
    }

    @NonNull
    @Override
    public ImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImageHolder((ImageView) layoutInflater.inflate(R.layout.list_item_image, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ImageHolder holder, int position) {
        Picasso.get()
                .load(images.get(position).getAvatar().getThumb())
                .error(R.drawable.image_placeholder)
                .placeholder(R.drawable.image_placeholder)
                .into(holder.getImageView());
    }

    @Override
    public int getItemCount() {
        return images.size();
    }
}
