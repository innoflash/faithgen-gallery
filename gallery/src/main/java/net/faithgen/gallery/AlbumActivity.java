package net.faithgen.gallery;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import net.faithgen.gallery.utils.Constants;
import net.faithgen.sdk.FaithGenActivity;

public class AlbumActivity extends FaithGenActivity {

    @Override
    public String getPageTitle() {
        return Constants.ALBUM;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
    }

}
