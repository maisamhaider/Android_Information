package com.dragster.android.information.system.my.android.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.dragster.android.information.system.my.android.R;
import com.dragster.android.information.system.my.android.utilities.GetSomething;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class Memory_Act extends AppCompatActivity {

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_memory__frag );
        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );

        TextView deviceUsedSpace_Tv, sdTotalSpace_tv, sdAvailableSpacePercent_tv,
                sdUsageDataPercent_tv, sdAvailableSpace_Tv, deviceTotalSpace_tv, deviceAvailableSpacePercent_tv,
                deviceUsageDataPercent_tv, deviceAvailableSpace_Tv;
        LinearLayout sdCard_LL;
        RoundCornerProgressBar deviceStorageProgress = findViewById( R.id.deviceStorageRoundCornerP_pb );
        RoundCornerProgressBar sdStorageProgress = findViewById( R.id.sdCardStorageRoundCornerP_pb );


        deviceUsedSpace_Tv = findViewById( R.id.deviceUsedStorage_Tv );
        deviceTotalSpace_tv = findViewById( R.id.deviceTotalSpace_tv );
        deviceAvailableSpacePercent_tv = findViewById( R.id.deviceAvailableSpacePercent_tv );
        deviceUsageDataPercent_tv = findViewById( R.id.deviceUsageDataPercent_tv );
        deviceAvailableSpace_Tv = findViewById( R.id.deviceAvailableSpace_Tv );
        sdAvailableSpacePercent_tv = findViewById( R.id.sdAvailableSpacePercent_tv );
        sdUsageDataPercent_tv = findViewById( R.id.sdUsageDataPercent_tv );
        sdAvailableSpace_Tv = findViewById( R.id.sdAvailableSpace_Tv );


        MainActivity mainActivity = new MainActivity();
        GetSomething getSomething  = new GetSomething();
        AdView mAdView = findViewById( R.id.memoryUsage_adv );
        MobileAds.initialize( this,getResources().getString( R.string.banner ) );
        if (mainActivity.isPaid)
        {
            mAdView.setVisibility( View.GONE );
        }
        else
        {
            getSomething.bannerAD( mAdView );
        }
        sdCard_LL = findViewById( R.id.sdCard_LL );

        float totalStorageInGBs, availableStorageInGBs, usedStorageInGBs;

        totalStorageInGBs = (GetSomething.getTotalStorage( false ) / 1024) + 1;
        availableStorageInGBs = GetSomething.getAvailableStorage(false) / 1024;

        usedStorageInGBs = totalStorageInGBs - availableStorageInGBs;
        deviceUsedSpace_Tv.setText( String.format( "%.2f",usedStorageInGBs )+ " GBs" );
        deviceTotalSpace_tv.setText( String.format( "%.2f",totalStorageInGBs )  + "GB" );
        deviceAvailableSpace_Tv.setText(String.format( "%.2f",availableStorageInGBs )  + "GB" );
        deviceUsageDataPercent_tv.setText(String.format( "%.1f", GetSomething.getPercentage( totalStorageInGBs, usedStorageInGBs )  )+ "%" );
        deviceAvailableSpacePercent_tv.setText( String.format( "%.1f",GetSomething.getPercentage( totalStorageInGBs, availableStorageInGBs  ) ) + "%" );
     //progress animation
        deviceStorageProgress.setMax( totalStorageInGBs );
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, usedStorageInGBs);
        valueAnimator.setDuration(1500);
        valueAnimator.addUpdateListener( valueAnimator12 -> deviceStorageProgress.setProgress(Float.parseFloat( valueAnimator12.getAnimatedValue().toString())) );
        valueAnimator.start();
        //device storage progress bar
        deviceStorageProgress.setProgress( usedStorageInGBs );

        //if sd card inserted
        Boolean isSDPresent = android.os.Environment.getExternalStorageState().equals( android.os.Environment.MEDIA_MOUNTED );
        Boolean isSDSupportedDevice = Environment.isExternalStorageRemovable();
        if (isSDSupportedDevice && isSDPresent) {
            float sdTotalStorageInGBs = (GetSomething.getTotalStorage( true ) / 1024) + 1;
            float sdAvailableStorageInGBs = GetSomething.getAvailableStorage(true) / 1024;
            float sdUsage = sdTotalStorageInGBs - sdAvailableStorageInGBs;
            //SD Card storage progress bar

            sdStorageProgress.setMax( sdTotalStorageInGBs );
            ValueAnimator sdValueAnimator = ValueAnimator.ofFloat(0, sdUsage);
            valueAnimator.setDuration(1500);
            valueAnimator.addUpdateListener( valueAnimator1 -> deviceStorageProgress.setProgress(Float.parseFloat( valueAnimator1.getAnimatedValue().toString())) );
            valueAnimator.start();
            sdStorageProgress.setProgress( sdUsage );
            sdAvailableSpace_Tv.setText( String.format( "%.2f",sdAvailableStorageInGBs ) + " GB" );
            sdUsageDataPercent_tv.setText(String.format( "%.1f",GetSomething.getPercentage( sdTotalStorageInGBs, sdUsage  ) )    + "%" );
            sdAvailableSpacePercent_tv.setText(String.format( "%.1f",GetSomething.getPercentage( sdTotalStorageInGBs, sdAvailableStorageInGBs ) )  + "%" );
        } else {
            sdCard_LL.setVisibility( View.INVISIBLE );
        }
    }

}
