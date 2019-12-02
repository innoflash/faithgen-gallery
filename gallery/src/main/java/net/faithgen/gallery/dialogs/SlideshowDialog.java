package net.faithgen.gallery.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import net.faithgen.gallery.R;
import net.faithgen.gallery.adapters.SliderAdapter;
import net.faithgen.gallery.models.Image;
import net.innoflash.iosview.DialogFullScreen;
import net.innoflash.iosview.DialogToolbar;

import java.util.List;
import java.util.Random;

public class SlideshowDialog extends DialogFullScreen {
    public static final String TAG = "slideshow_tagx";
    private View view;
    private DialogToolbar dialogToolbar;
    private SliderView sliderView;
    private SliderAdapter sliderAdapter;
    private String albumName;
    private int index;
    private List<Image> images;
    private Random random;

    public SlideshowDialog(String albumName, int index, List<Image> images) {
        this.albumName = albumName;
        this.index = index;
        this.images = images;
        this.random = new Random();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialogs_slide_show, container, false);
        dialogToolbar = view.findViewById(R.id.dialog_toolbar);
        sliderView = view.findViewById(R.id.sliderView);

        dialogToolbar.setDialogFragment(this);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        dialogToolbar.setTitle(albumName);

        sliderAdapter = new SliderAdapter(getActivity(), images);
        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimations.DROP);
        sliderView.setSliderTransformAnimation(SliderAnimations.values()[random.nextInt(SliderAnimations.values().length)]);

        sliderView.setCurrentPagePosition(index);
    }
}
