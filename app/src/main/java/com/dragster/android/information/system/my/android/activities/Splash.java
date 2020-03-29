package com.dragster.android.information.system.my.android.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.dragster.android.information.system.my.android.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class Splash extends AppCompatActivity {
    InterstitialAd interstitialAd;
    private MainActivity mainActivity;

    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_splash );


        mainActivity= new MainActivity();
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getResources().getString(R.string.interstitial));
        reqNewInterstitial();
        SharedPreferences myPreferences = getSharedPreferences( "MY_PREFERENCES", Context.MODE_PRIVATE );
        boolean isPaid = myPreferences.getBoolean( "is_purchase", false );

        if(!isPaid) {
            if (interstitialAd.isLoaded()) {
                interstitialAd.show();
            } else {
                handler = new Handler( );
                handler.postDelayed( new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent( Splash.this,TermsAndConditions.class );
                        startActivity( intent );
                        finish();
                    }
                },2000);

            }
            interstitialAd.setAdListener( new AdListener() {
                @Override
                public void onAdClosed() {
                    handler = new Handler( );
                    handler.postDelayed( new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent( Splash.this,TermsAndConditions.class );
                            startActivity( intent );
                            finish();
                        }
                    },2000);
                    reqNewInterstitial();
                }
            } );
        }else
        {
            handler = new Handler( );
            handler.postDelayed( new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent( Splash.this,TermsAndConditions.class );
                    startActivity( intent );
                    finish();
                }
            },2000);
        }



    }
    public void reqNewInterstitial() {
        interstitialAd.loadAd(new AdRequest.Builder().build());

    }
}
