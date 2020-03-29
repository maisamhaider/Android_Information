package com.dragster.android.information.system.my.android.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import com.dragster.android.information.system.my.android.R;
import com.dragster.android.information.system.my.android.utilities.GetSomething;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class ScreenSize extends AppCompatActivity {

    private int screenHeight_int, screenWidth_int;
    private DisplayMetrics displayMetrics;
    private TextView screenHeight_Tv, screenInnerHeight_tv, screenInnerSizeWidth_tv;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_screen_size );
        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );

        displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics( displayMetrics );
        screenHeight_int = displayMetrics.heightPixels;
        screenWidth_int = displayMetrics.widthPixels;

        screenHeight_Tv = findViewById( R.id.screenWidthHeight_Tv );
        screenInnerHeight_tv = findViewById( R.id.screenInnerHeight_tv );
        screenInnerSizeWidth_tv = findViewById( R.id.screenInnerSizeWidth_tv );
        MainActivity mainActivity = new MainActivity();
        GetSomething getSomething = new GetSomething();
        AdView mAdView = findViewById( R.id.screenSize_adv );
        MobileAds.initialize( this, getResources().getString( R.string.banner ) );
        if (mainActivity.isPaid) {
            mAdView.setVisibility( View.GONE );
        } else {
            getSomething.bannerAD( mAdView );
        }
        screenHeight_Tv.setText( screenHeight_int + " X " + screenWidth_int );
        screenInnerHeight_tv.setText( screenHeight_int + "" );
        screenInnerSizeWidth_tv.setText( screenWidth_int + "" );

    }
}
