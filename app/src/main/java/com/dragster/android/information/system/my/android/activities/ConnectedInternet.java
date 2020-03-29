package com.dragster.android.information.system.my.android.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dragster.android.information.system.my.android.R;
import com.dragster.android.information.system.my.android.utilities.GetSomething;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import github.nisrulz.easydeviceinfo.base.EasyNetworkMod;
import github.nisrulz.easydeviceinfo.base.NetworkType;

public class ConnectedInternet extends AppCompatActivity {
    private TextView wifiName_Tv, wifiLast_Tv,internetType_tv;
    private ImageView wifiIsOnOrOff_Iv;
     @SuppressLint("SourceLockedOrientationActivity")
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_connected_internet );
         setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


         ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        WifiManager wifiManager = (WifiManager)getApplicationContext().getSystemService( Context.WIFI_SERVICE );
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        wifiName_Tv = findViewById( R.id.wifiName_Tv );
        wifiLast_Tv = findViewById( R.id.wifiLast_Tv ) ;
        wifiIsOnOrOff_Iv = findViewById( R.id.wifiIsOnOrOff_Iv );
        internetType_tv = findViewById( R.id.internetType_tv );



         MainActivity mainActivity = new MainActivity();
         GetSomething getSomething  = new GetSomething();
         AdView mAdView = findViewById( R.id.connectedInternet_adv );
         MobileAds.initialize( this,getResources().getString( R.string.banner ) );
         if (mainActivity.isPaid)
         {
             mAdView.setVisibility( View.GONE );
         }
         else
         {
             getSomething.bannerAD( mAdView );
         }

        EasyNetworkMod easyNetworkMod = new EasyNetworkMod(this);

        int networkType = easyNetworkMod.getNetworkType();
        if (networkInfo != null && networkInfo.isConnected()) {
            switch (networkType) {
                case NetworkType.CELLULAR_UNKNOWN:
                    wifiName_Tv.setText( "UNKNOWN" );
                    internetType_tv.setText( "UNKNOWN" );
                     break;
                case NetworkType.CELLULAR_UNIDENTIFIED_GEN:
                    wifiName_Tv.setText( "Network Type : Cellular Unidentified Generation" );
                    internetType_tv.setText( "Network Type : Cellular Unidentified Generation" );

                    break;
                case NetworkType.CELLULAR_2G:
                    wifiName_Tv.setText( "2G" );
                    internetType_tv.setText( "2G" );
                    wifiLast_Tv.setText( wifiLast_Tv.getText().toString()+" "+"2G" );


                    break;
                case NetworkType.CELLULAR_3G:
                    wifiName_Tv.setText( "3G" );
                    internetType_tv.setText( "3G" );
                    wifiLast_Tv.setText( wifiLast_Tv.getText().toString()+" "+"3G" );

                    break;
                case NetworkType.CELLULAR_4G:
                    wifiName_Tv.setText( "4G" );
                    internetType_tv.setText( "4G" );
                    wifiLast_Tv.setText(  wifiLast_Tv.getText().toString()+" "+"4G" );


                    break;
                case NetworkType.WIFI_WIFIMAX:
                    wifiName_Tv.setText( GetSomething.getCurrentSsID( this ) );
                    internetType_tv.setText( "Wi-Fi" );
                    wifiLast_Tv.setText( wifiLast_Tv.getText().toString()+" "+GetSomething.getCurrentSsID( this )  );

                    break;
                case NetworkType.UNKNOWN:
                default:
                    wifiName_Tv.setText( "Network Type : Unknown" );
                    internetType_tv.setText( "Unknown" );


                    break;
            }
        }
        else
        {
            wifiLast_Tv.setText( "No Internet connected" );
        }
    }
}
