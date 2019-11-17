package net.faithgen.ffgallery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import net.faithgen.gallery.GalleryActivity;

public class MainActivity extends AppCompatActivity {

    private Button openGallery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openGallery = findViewById(R.id.openGallery);
        openGallery.setOnClickListener(view -> startActivity(new Intent(this, GalleryActivity.class)));
    }
}
