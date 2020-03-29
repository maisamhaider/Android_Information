package com.dragster.android.information.system.my.android.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dragster.android.information.system.my.android.R;
import com.dragster.android.information.system.my.android.utilities.GetSomething;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.skydoves.progressview.ProgressView;

import github.nisrulz.easydeviceinfo.base.EasyBatteryMod;

public class BatteryInfo extends AppCompatActivity {

    private TextView batteryStatus_Tv, batteryHealth_Tv, batteryTemperature_Tv, batteryVoltage_Tv, batteryType_Tv, powerSource_Tv;
    AdView mAdView;
    @SuppressLint({"WrongConstant", "SourceLockedOrientationActivity"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_battery_info );
        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mAdView = findViewById( R.id.batteryInfo_adv );
        batteryStatus_Tv = findViewById( R.id.batteryStatus_Tv );
        batteryHealth_Tv = findViewById( R.id.batteryHealth_Tv );
        batteryTemperature_Tv = findViewById( R.id.batteryTemperature_Tv );
        batteryVoltage_Tv = findViewById( R.id.batteryVoltage_Tv );
        batteryType_Tv = findViewById( R.id.batteryType_Tv );
        powerSource_Tv = findViewById( R.id.powerSource_Tv );
        IntentFilter intentFilter = new IntentFilter( Intent.ACTION_BATTERY_CHANGED );
        getApplicationContext().registerReceiver( broadcastReceiver, intentFilter );
        MainActivity mainActivity = new MainActivity();
        GetSomething getSomething = new GetSomething();
        mAdView = findViewById( R.id.allBasicInfo_adv );
        MobileAds.initialize( this,getResources().getString( R.string.banner ) );
        if (mainActivity.isPaid)
        {
            mAdView.setVisibility( View.GONE );
        }
        else
        {
            getSomething.bannerAD( mAdView );
        }


        EasyBatteryMod easyBatteryMod = new EasyBatteryMod( this );
        if (easyBatteryMod.isBatteryPresent()) {
            batteryHealth_Tv.setText( String.valueOf( easyBatteryMod.getBatteryHealth() ) );
            batteryType_Tv.setText( String.valueOf( easyBatteryMod.getBatteryTechnology() ) );
        }
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {


            int deviceBatteryStatus = intent.getIntExtra( BatteryManager.EXTRA_STATUS, -1 );
            int batteryHealth = intent.getIntExtra( BatteryManager.EXTRA_HEALTH, 0 );
            int batterySource = intent.getIntExtra( BatteryManager.EXTRA_PLUGGED, 0 );
            int batteryVoltage = intent.getIntExtra( BatteryManager.EXTRA_VOLTAGE, 0 );
            int level = intent.getIntExtra( BatteryManager.EXTRA_LEVEL, -1 );
            int scale = intent.getIntExtra( BatteryManager.EXTRA_SCALE, -1 );
            int batteryLevel = (int) (((float) level / (float) scale) * 100.0f);
            ProgressView batteryProgressView = findViewById( R.id.batteryProgressView1 );
            batteryProgressView.setAnimating( true );
            batteryProgressView.setMax( 100 );
            batteryProgressView.setProgress( batteryLevel );
            batteryProgressView.setMin( 5 );
            batteryProgressView.setLabelText( batteryLevel+"%" );
            if(batteryLevel==100)
            {
                batteryProgressView.setLabelText( "Full" );

            }
            else if (batteryLevel<15)
            {
                batteryProgressView.setLabelText( "Low battery" );
            }

            {
                if (deviceBatteryStatus == BatteryManager.BATTERY_STATUS_CHARGING) {
                    batteryStatus_Tv.setText( "Charging" );
                } else if (deviceBatteryStatus == BatteryManager.BATTERY_STATUS_NOT_CHARGING) {
                    batteryStatus_Tv.setText( "Not Charging" );
                } else if (deviceBatteryStatus == BatteryManager.BATTERY_STATUS_FULL) {
                    batteryStatus_Tv.setText( "Battery is full" );
                } else if (deviceBatteryStatus == BatteryManager.BATTERY_STATUS_UNKNOWN) {
                    batteryStatus_Tv.setText( "Unknown" );
                }
                else if (deviceBatteryStatus == BatteryManager.BATTERY_STATUS_DISCHARGING) {
                    batteryStatus_Tv.setText( "Discharging" );
                }
                {
                    if (batteryHealth == BatteryManager.BATTERY_HEALTH_COLD) {
                        batteryHealth_Tv.setText( "Cold" );
                    } else if (batteryHealth == BatteryManager.BATTERY_HEALTH_DEAD) {
                        batteryHealth_Tv.setText( "Dead" );
                    } else if (batteryHealth == BatteryManager.BATTERY_HEALTH_GOOD) {
                        batteryHealth_Tv.setText( "Good" );
                    } else if (batteryHealth == BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE) {
                        batteryHealth_Tv.setText( "Over Voltage" );
                    } else if (batteryHealth == BatteryManager.BATTERY_HEALTH_UNKNOWN) {
                        batteryHealth_Tv.setText( "Unknown" );
                    } else if (batteryHealth == BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE) {
                        batteryHealth_Tv.setText( "Unspecified Failure" );
                    }

                }
                {
                    int temp = 0;
                    if (intent != null) {
                        temp = (intent.getIntExtra( BatteryManager.EXTRA_TEMPERATURE, 0 ) / 10);
                    }
                    batteryTemperature_Tv.setText( temp + "°C" + "-" + GetSomething.getCelsiusToFahrenheit( temp ) + "°F" );
                }
                {
                    if (batterySource == BatteryManager.BATTERY_PLUGGED_AC) {
                        powerSource_Tv.setText( "AC" );
                    } else if (batterySource == BatteryManager.BATTERY_PLUGGED_USB) {
                        powerSource_Tv.setText( "USB" );
                    } else if (batterySource == BatteryManager.BATTERY_PLUGGED_WIRELESS) {
                        powerSource_Tv.setText( "WIRELESS" );
                    } else
                        powerSource_Tv.setText( "Unplugged" );
                }
                {
                    batteryVoltage_Tv.setText( batteryVoltage + "mV" );
                }
            }

        }

    };
    }

