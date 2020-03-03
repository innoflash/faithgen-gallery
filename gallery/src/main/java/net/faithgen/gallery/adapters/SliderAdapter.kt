package net.faithgen.gallery.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.smarteist.autoimageslider.SliderViewAdapter
import com.squareup.picasso.Picasso
import net.faithgen.gallery.R
import net.faithgen.gallery.models.Image
import net.faithgen.gallery.viewholders.SliderHolder

/**
 * Adapter to render slider images
 */
final class SliderAdapter(private val context: Context, private val images: List<Image>, private val imageListener: ImageListener?) : SliderViewAdapter<SliderHolder>() {
    private val layoutInflater: LayoutInflater by lazy { LayoutInflater.from(context) }

    override fun onCreateViewHolder(parent: ViewGroup?): SliderHolder {
        return SliderHolder(layoutInflater.inflate(R.layout.list_item_grid_image, parent, false))
    }

    override fun getCount() = images.size

    override fun onBindViewHolder(viewHolder: SliderHolder?, position: Int) {
        Picasso.get()
                .load(images[position].avatar.original)
                .error(R.drawable.image_placeholder)
                .placeholder(R.drawable.image_placeholder)
                .into(viewHolder!!.imageView)

        viewHolder.floatingActionButton.setOnClickListener { v: View? -> imageListener?.onImageClicked(images[position]) }
    }

    interface ImageListener {
        fun onImageClicked(image: Image?)
    }
}