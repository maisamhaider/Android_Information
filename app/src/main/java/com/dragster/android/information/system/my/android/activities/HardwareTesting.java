package com.dragster.android.information.system.my.android.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dragster.android.information.system.my.android.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.Locale;


public class HardwareTesting extends AppCompatActivity implements View.OnClickListener {
    InterstitialAd interstitialAd;

    TextView simCardTestValue_tv, isHeadphone_tv, bluetoothSate_tv;
    TextToSpeech textSpeech;
    Handler handler;
    int clickCounter = 0;
    boolean isHeadphoneConnected;
    SharedPreferences sharedPreferences;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_hardware_testing );
        sharedPreferences = getSharedPreferences( "MY_PREFERENCES", Context.MODE_PRIVATE );
        interstitialAd = new InterstitialAd ( this );
        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );
        interstitialAd.setAdUnitId( getResources().getString( R.string.interstitial ) );
        reqNewInterstitial();
        handler = new Handler();

        LinearLayout vibrationTest_LL, simCard_LL, displayTest_LL, touchSensor_LL, speakerTest_LL, checkHeadPhone_LL, checkBluetooth_LL;
        simCardTestValue_tv = findViewById( R.id.simCardTestValue_tv );
        isHeadphone_tv = findViewById( R.id.isHeadphone_tv );
        bluetoothSate_tv = findViewById( R.id.bluetoothSate_tv );

        vibrationTest_LL = findViewById( R.id.vibrationTest_LL );
        simCard_LL = findViewById( R.id.simCard_LL );
        displayTest_LL = findViewById( R.id.displayTest_LL );
        touchSensor_LL = findViewById( R.id.touchSensor_LL );
        speakerTest_LL = findViewById( R.id.speakerTest_LL );
        checkHeadPhone_LL = findViewById( R.id.checkHeadPhone_LL );
        checkBluetooth_LL = findViewById( R.id.checkBluetooth_LL );

        vibrationTest_LL.setOnClickListener( this );
        simCard_LL.setOnClickListener( this );
        displayTest_LL.setOnClickListener( this );
        speakerTest_LL.setOnClickListener( this );
        touchSensor_LL.setOnClickListener( this );
        checkHeadPhone_LL.setOnClickListener( this );
        checkBluetooth_LL.setOnClickListener( this );

        textSpeech = new TextToSpeech( getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textSpeech.setLanguage( Locale.US );
                }
            }
        } );


    }

    public void reqNewInterstitial() {
        interstitialAd.loadAd( new AdRequest.Builder().build() );

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.vibrationTest_LL:

                if (sharedPreferences.getBoolean( "is_purchase", false )) {
                    Vibrator vibrator = (Vibrator) getSystemService( Context.VIBRATOR_SERVICE );
                    vibrator.vibrate( 2000 );
                 } else {
                    if (interstitialAd.isLoaded()) {
                        interstitialAd.show();
                    } else {
                        Vibrator vibrator = (Vibrator) getSystemService( Context.VIBRATOR_SERVICE );
                        vibrator.vibrate( 2000 );
                    }
                    interstitialAd.setAdListener( new AdListener() {
                        @Override
                        public void onAdClosed() {
                            Vibrator vibrator = (Vibrator) getSystemService( Context.VIBRATOR_SERVICE );
                            vibrator.vibrate( 2000 );
                            reqNewInterstitial();
                        }
                    } );
                }


                break;
            case R.id.simCard_LL:
                TelephonyManager telMgr = (TelephonyManager) getSystemService( Context.TELEPHONY_SERVICE );
                int simState = telMgr.getSimState();
                switch (simState) {
                    case TelephonyManager.SIM_STATE_ABSENT:
                        simCardTestValue_tv.setVisibility( View.VISIBLE );
                        simCardTestValue_tv.setText( "Sim card is absent" );
                        handler.postDelayed( () -> simCardTestValue_tv.setVisibility( View.GONE ), 2000 );
                        break;
                    case TelephonyManager.SIM_STATE_READY:
                        simCardTestValue_tv.setVisibility( View.VISIBLE );
                        simCardTestValue_tv.setText( "sim card is inserted" );
                        handler.postDelayed( () -> simCardTestValue_tv.setVisibility( View.GONE ), 2000 );
                        break;
                    case TelephonyManager.SIM_STATE_UNKNOWN:
                        simCardTestValue_tv.setVisibility( View.VISIBLE );
                        simCardTestValue_tv.setText( "sim card unknown" );
                        handler.postDelayed( () -> simCardTestValue_tv.setVisibility( View.GONE ), 2000 );
                        break;
                }

                break;
            case R.id.displayTest_LL:
                Dialog dDialog = new Dialog( this, android.R.style.Theme_Light );
                dDialog.requestWindowFeature( Window.FEATURE_NO_TITLE );
                View view = LayoutInflater.from( this ).inflate( R.layout.my_custom_display_test_dialog_layout, null );
                dDialog.setContentView( view );
                ConstraintLayout cL = view.findViewById( R.id.displayTestDialog_CL );
                cL.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (clickCounter == 0) {
                            cL.setBackgroundColor( Color.parseColor( "#7CFC00" ) );
                        } else if (clickCounter == 1) {
                            cL.setBackgroundColor( Color.parseColor( "#ff0000" ) );

                        } else if (clickCounter == 2) {
                            cL.setBackgroundColor( Color.parseColor( "#3346FF" ) );

                        }
                        if (clickCounter >= 3) {
                            clickCounter = 0;
                            dDialog.dismiss();
                        }
                        clickCounter++;

                    }
                } );
                dDialog.show();
                break;

            case R.id.touchSensor_LL:
                if (sharedPreferences.getBoolean( "is_purchase",false ))
                {
                    Intent intent = new Intent( HardwareTesting.this, DrawingActivty.class );
                    startActivity( intent );
                }else
                {
                    if (interstitialAd.isLoaded()) {
                        interstitialAd.show();
                    } else {
                        Intent intent = new Intent( HardwareTesting.this, DrawingActivty.class );
                        startActivity( intent );
                    }
                    interstitialAd.setAdListener( new AdListener() {
                        @Override
                        public void onAdClosed() {
                            Intent intent = new Intent( HardwareTesting.this, DrawingActivty.class );
                            startActivity( intent );
                            reqNewInterstitial();
                        }
                    } );
                }


                break;
            case R.id.checkHeadPhone_LL:

                IntentFilter iFilter = new IntentFilter( Intent.ACTION_HEADSET_PLUG );
                getApplicationContext().registerReceiver( receiver, iFilter );
                if (isHeadphoneConnected) {
                    isHeadphone_tv.setVisibility( View.VISIBLE );
                    isHeadphone_tv.setText( "Headphone is unplugged" );
                    handler.postDelayed( () -> isHeadphone_tv.setVisibility( View.GONE ), 2000 );
                } else {
                    isHeadphone_tv.setVisibility( View.VISIBLE );
                    isHeadphone_tv.setText( "Headphone is unplugged" );
                    handler.postDelayed( () -> isHeadphone_tv.setVisibility( View.GONE ), 2000 );
                }

                break;
            case R.id.speakerTest_LL:
                textSpeech.speak( "speaker is working.", TextToSpeech.QUEUE_FLUSH, null );
                break;
            case R.id.checkBluetooth_LL:
                BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (bluetoothAdapter == null) {
                    bluetoothSate_tv.setVisibility( View.VISIBLE );
                    bluetoothSate_tv.setText( "device does not have bluetooth" );
                    handler.postDelayed( new Runnable() {
                        @Override
                        public void run() {
                            bluetoothSate_tv.setVisibility( View.GONE );
                        }
                    }, 2000 );
                } else if (bluetoothAdapter.isEnabled()) {
                    bluetoothSate_tv.setVisibility( View.VISIBLE );
                    bluetoothSate_tv.setText( "bluetooth is On" );
                    handler.postDelayed( new Runnable() {
                        @Override
                        public void run() {
                            bluetoothSate_tv.setVisibility( View.GONE );
                        }
                    }, 2000 );
                } else {
                    bluetoothSate_tv.setVisibility( View.VISIBLE );
                    bluetoothSate_tv.setText( "bluetooth is Off" );
                    handler.postDelayed( new Runnable() {
                        @Override
                        public void run() {
                            bluetoothSate_tv.setVisibility( View.GONE );
                        }
                    }, 2000 );
                }

                break;


        }

    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int isConnected = intent.getIntExtra( "state", -1 );
            if (isConnected != -1) {
                isHeadphoneConnected = true;
            } else {
                isHeadphoneConnected = false;
            }
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        textSpeech.stop();
    }
}
