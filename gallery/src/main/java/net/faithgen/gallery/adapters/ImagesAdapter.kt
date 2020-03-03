package net.faithgen.gallery.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import net.faithgen.gallery.R
import net.faithgen.gallery.models.Image
import net.faithgen.gallery.viewholders.ImageHolder

/**
 * Adapter to render images in an album view
 */
final class ImagesAdapter(private val context: Context, private val images: List<Image>) : RecyclerView.Adapter<ImageHolder>(){
    private val layoutInflater: LayoutInflater by lazy { LayoutInflater.from(context) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val imageView = layoutInflater.inflate(R.layout.list_item_image, parent, false) as ImageView
        return ImageHolder(imageView)
    }

    override fun getItemCount() = images.size

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        Picasso.get()
                .load(images[position].avatar.thumb)
                .error(R.drawable.image_placeholder)
                .placeholder(R.drawable.image_placeholder)
                .into(holder.imageView)
    }
}