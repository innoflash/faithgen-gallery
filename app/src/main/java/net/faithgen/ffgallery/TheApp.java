package net.faithgen.ffgallery;

import android.annotation.SuppressLint;
import android.app.Application;

import net.faithgen.sdk.SDK;
import net.faithgen.sdk.enums.Subscription;
import net.faithgen.sdk.menu.MenuChoice;

import java.io.IOException;

public class TheApp extends Application {
    @SuppressLint("ResourceType")
    @Override
    public void onCreate() {
        super.onCreate();
        try {
            SDK.initializeThemeColor(getResources().getString(R.color.colorPrimaryDark));
            SDK.initializeSDK(this, this.getAssets().open("config.json"), MenuChoice.CONTEXTUAL_MENU, Subscription.Premium);
            SDK.initializeApiBase("http://192.168.0.112:8001/api/");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
