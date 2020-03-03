package net.faithgen.gallery.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.faithgen.gallery.R;
import net.faithgen.gallery.models.Album;
import net.faithgen.gallery.viewholders.AlbumHolder;
import net.faithgen.gallery.views.AlbumView;

import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumHolder> {

    private Context context;
    private List<Album> albums;
    private LayoutInflater layoutInflater;

    public AlbumAdapter(Context context, List<Album> albums) {
        this.context = context;
        this.albums = albums;
        this.layoutInflater = LayoutInflater.from(this.context);
    }

    @NonNull
    @Override
    public AlbumHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AlbumHolder((AlbumView) layoutInflater.inflate(R.layout.list_item_album, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumHolder holder, int position) {
        holder.getAlbumView().setAlbum(albums.get(position));
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }
}
