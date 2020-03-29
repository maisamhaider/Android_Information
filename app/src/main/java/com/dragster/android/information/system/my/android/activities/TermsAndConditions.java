package com.dragster.android.information.system.my.android.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

import com.dragster.android.information.system.my.android.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class TermsAndConditions extends AppCompatActivity {
    InterstitialAd interstitialAd;
    private Button declineBtn,acceptBtn ;
    private SharedPreferences myPreferences;
    private CheckBox checkBox ;
    public boolean isPaid = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_terms_and_conditions );
        declineBtn = findViewById( R.id.declineBtn );
        acceptBtn = findViewById( R.id.acceptBtn );
        checkBox = findViewById( R.id.tAndCB );

        myPreferences = getApplicationContext().getSharedPreferences( "MY_PREFERENCES", Context.MODE_PRIVATE );
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getResources().getString(R.string.interstitial));
        isPaid = myPreferences.getBoolean( "is_purchase",false );
        reqNewInterstitial();
        boolean previouslyStarted = myPreferences.getBoolean("is_Accept", false);
        if(!previouslyStarted) {
            if(!isPaid) {
                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                } else {
                    acceptBtn.setOnClickListener( new View.OnClickListener() {
                        @SuppressLint("ApplySharedPref")
                        @Override
                        public void onClick(View v) {
                            if (checkBox.isChecked())
                            {
                                SharedPreferences.Editor edit = myPreferences.edit();
                                Intent intent = new Intent( getApplicationContext(),MainActivity.class );
                                intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                                edit.putBoolean("is_Accept", true);
                                edit.commit();
                                startActivity( intent );
                                finish();
                            }


                        }
                    } );
                    declineBtn.setOnClickListener( new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    } );

                }
                interstitialAd.setAdListener( new AdListener() {
                    @Override
                    public void onAdClosed() {
                        acceptBtn.setOnClickListener( new View.OnClickListener() {
                            @SuppressLint("ApplySharedPref")
                            @Override
                            public void onClick(View v) {
                                if (checkBox.isChecked())
                                {
                                    SharedPreferences.Editor edit = myPreferences.edit();
                                    Intent intent = new Intent( getApplicationContext(),MainActivity.class );
                                    intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                                    edit.putBoolean("is_Accept", true);
                                    edit.commit();
                                    startActivity( intent );
                                    finish();
                                }
                            }
                        } );
                        declineBtn.setOnClickListener( new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        } );                        reqNewInterstitial();
                    }
                } );
            }else
            {
                acceptBtn.setOnClickListener( new View.OnClickListener() {
                    @SuppressLint("ApplySharedPref")
                    @Override
                    public void onClick(View v) {
                        if (checkBox.isChecked())
                        {
                            SharedPreferences.Editor edit = myPreferences.edit();
                            Intent intent = new Intent( getApplicationContext(),MainActivity.class );
                            intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                            edit.putBoolean("is_Accept", true);
                            edit.commit();
                            startActivity( intent );
                            finish();
                        }
                    }
                } );
                declineBtn.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                } );
            }

        }
        else
            {
                Intent intent = new Intent( getApplicationContext(),MainActivity.class );
                intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity( intent );
                finish();
            }
            }

    public void reqNewInterstitial() {
        interstitialAd.loadAd(new AdRequest.Builder().build());

    }

}
