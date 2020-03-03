package net.faithgen.gallery.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.faithgen.gallery.R
import net.faithgen.gallery.models.Album
import net.faithgen.gallery.viewholders.AlbumHolder
import net.faithgen.gallery.views.AlbumView

/**
 * Adapter to wire up albums
 */
final class AlbumAdapter(private val context: Context, private val albums: List<Album>) : RecyclerView.Adapter<AlbumHolder>() {
    private val layoutInflater: LayoutInflater by lazy { LayoutInflater.from(context) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumHolder {
        val albumView = layoutInflater.inflate(R.layout.list_item_album, parent, false) as AlbumView
        return AlbumHolder(albumView)
    }

    override fun getItemCount() = albums.size

    override fun onBindViewHolder(holder: AlbumHolder, position: Int) {
        holder.albumView.album = albums[position]
    }
}