package net.faithgen.gallery.viewholders

import android.view.View
import android.widget.ImageView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.smarteist.autoimageslider.SliderViewAdapter
import net.faithgen.gallery.R

/**
 * Holder for the SliderAdapter
 */
final class SliderHolder(val itemView: View) : SliderViewAdapter.ViewHolder(itemView) {
    val imageView: ImageView by lazy { itemView.findViewById<ImageView>(R.id.image_view) }
    val floatingActionButton: FloatingActionButton by lazy { itemView.findViewById<FloatingActionButton>(R.id.image_view) }
}