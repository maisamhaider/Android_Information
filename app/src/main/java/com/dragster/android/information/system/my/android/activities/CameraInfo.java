package com.dragster.android.information.system.my.android.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dragster.android.information.system.my.android.classes.Permissions;
import com.dragster.android.information.system.my.android.R;
import com.dragster.android.information.system.my.android.utilities.GetSomething;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class CameraInfo extends AppCompatActivity {
    private TextView backCameraPixel_Tv,cameraPixel_Tv,backCameraRemainingPics_Tv,frontCameraRemainingPics_Tv;
    float fRemainingPhotos,bRemainingPhotos ,freeStorage,fImageHeight, fImageWidth, bImageHeight, bImageWidth,fOnePicSize,bOneImageSize;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_camera_info );
        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Permissions permission = new Permissions(this);
        permission.permission();
        backCameraPixel_Tv = findViewById( R.id.backCameraPixel_Tv );
        cameraPixel_Tv = findViewById( R.id.cameraPixel_Tv );
        backCameraRemainingPics_Tv = findViewById( R.id.backCameraRemainingPics_Tv ) ;
        frontCameraRemainingPics_Tv = findViewById( R.id.frontCameraRemainingPics_Tv );



        MainActivity mainActivity = new MainActivity();
        GetSomething getSomething  = new GetSomething();
        AdView mAdView = findViewById( R.id.activityCameraInfo_adv );
        MobileAds.initialize( this,getResources().getString( R.string.banner ) );
        if (mainActivity.isPaid)
        {
            mAdView.setVisibility( View.GONE );
        }
        else
        {
            getSomething.bannerAD( mAdView );
        }

        backCameraPixel_Tv.setText( String.valueOf( GetSomething.getBackCameraPixels()));
        cameraPixel_Tv.setText(  String.valueOf( GetSomething.getFrontCameraPixels() ));

        bImageWidth = GetSomething.getBackCameraWidthPixels()/2;
        bImageHeight = GetSomething.getBackCameraHeightPixels()/2;
        fImageWidth = GetSomething.getFrontHeightCameraPixels()/2;
        fImageHeight = GetSomething.getFrontCameraWidthPixels()/2;
        freeStorage = GetSomething.getAvailableStorage(false);
        fOnePicSize = fImageHeight * fImageWidth*8/8/1024/1024 ;


        bOneImageSize = bImageWidth*bImageHeight*8/8/1024/1024;
        fRemainingPhotos = freeStorage /fOnePicSize;
        bRemainingPhotos = freeStorage / bOneImageSize;
        backCameraRemainingPics_Tv.setText( GetSomething.getLeftStringToThePoint( String.valueOf( bRemainingPhotos ),"." ));
        frontCameraRemainingPics_Tv.setText(  GetSomething.getLeftStringToThePoint( String.valueOf( fRemainingPhotos  ),".")  );

    }
}
