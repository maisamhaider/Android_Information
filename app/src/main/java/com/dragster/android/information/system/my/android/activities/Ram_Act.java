package com.dragster.android.information.system.my.android.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.dragster.android.information.system.my.android.R;
import com.dragster.android.information.system.my.android.utilities.GetSomething;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.Objects;

public class Ram_Act extends AppCompatActivity {
    private TextView ramAmount_Tv;
    private float totalRam, freeRam, usedRam;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_ram_ );
        ramAmount_Tv = findViewById( R.id.ramAmount_Tv );
        RoundCornerProgressBar progressBar = findViewById( R.id.ramRoundCorner_pb );
        TextView ramUsagePercent_tv = findViewById( R.id.ramUsagePercent_tv );
        TextView ramUsed_Tv = findViewById( R.id.ramUsed_Tv );
        TextView ramAvailablePercent_tv = findViewById( R.id.ramAvailablePercent_tv );
        TextView ramAvailable_Tv = findViewById( R.id.ramAvailable_Tv );
        MainActivity mainActivity = new MainActivity();
        GetSomething getSomething  = new GetSomething();
        AdView mAdView = findViewById( R.id.ramAct_adv );
        MobileAds.initialize( this,getResources().getString( R.string.banner ) );
        if (mainActivity.isPaid)
        {
            mAdView.setVisibility( View.GONE );
        }
        else
        {
            getSomething.bannerAD( mAdView );
        }
        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );

        ActivityManager activityManager = (ActivityManager) Objects.requireNonNull( this ).getSystemService( Context.ACTIVITY_SERVICE );
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo( memoryInfo );
        float totalRam1 = memoryInfo.totalMem / 1024 / 1024;
        float freeRam1 = memoryInfo.availMem / 1024/ 1024;
        float usedRam =  totalRam1 - freeRam1;
        ramUsagePercent_tv.setText( String.format( "%.1f",GetSomething.getPercentage( totalRam1,usedRam ) )+"%" );
        ramAvailablePercent_tv.setText(  String.format( "%.1f",GetSomething.getPercentage( totalRam1,freeRam1 ) )+"%" );

        progressBar.setMax( totalRam1 );
        ValueAnimator animator = ValueAnimator.ofFloat(0, usedRam );
        animator.setDuration( 1500 );
        animator.addUpdateListener( new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                progressBar.setProgress(  Float.parseFloat(animation.getAnimatedValue().toString()  ) );
            }
        } );
        animator.start();
        progressBar.setProgress(  usedRam );


        totalRam = memoryInfo.totalMem / 1024 ;
        freeRam = memoryInfo.availMem / 1024;
        usedRam = totalRam - freeRam;
        ramUsed_Tv.setText( String.format( "%.1f", usedRam ) + "KB" );
        ramUsed_Tv.setText( String.format( "%.1f", usedRam ) + "KB" );
        // free ram
        if (freeRam > 1024) {
            freeRam = freeRam / 1024;
            ramAvailable_Tv.setText( String.format( "%.1f", freeRam ) + "MB" );
            if (freeRam > 1024) {
                freeRam = freeRam / 1024;
                ramAvailable_Tv.setText( String.format( "%.1f", freeRam ) + "GB" );
            }
        }
        // used ram
        if (usedRam > 1024) {
            usedRam = usedRam / 1024;
            ramUsed_Tv.setText( String.format( "%.1f", usedRam ) + "MB" );
            if (usedRam > 1024) {
                usedRam = usedRam / 1024;
                ramUsed_Tv.setText( String.format( "%.1f", usedRam ) + "GB" );
            }
        }

        // total ram
        ramAmount_Tv.setText( String.format( "%.1f", totalRam/1024/1024 ) + " GB" );

    }
}
