package net.faithgen.gallery.viewholders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.faithgen.gallery.views.AlbumView;

public class AlbumHolder extends RecyclerView.ViewHolder {
    private AlbumView albumView;
    public AlbumHolder(@NonNull View itemView) {
        super(itemView);
        this.albumView = (AlbumView) itemView;
    }

    public AlbumView getAlbumView() {
        return albumView;
    }
}
