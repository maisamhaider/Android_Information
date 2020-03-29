package com.dragster.android.information.system.my.android.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.usage.NetworkStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dragster.android.information.system.my.android.activities.InternetSpeed;
import com.dragster.android.information.system.my.android.interfaces.DataUsageInterface;
import com.dragster.android.information.system.my.android.models.PageViewModel;
import com.dragster.android.information.system.my.android.R;
import com.dragster.android.information.system.my.android.activities.DataUsageGraphsActivity;
import com.dragster.android.information.system.my.android.classes.Permissions;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.Objects;

import me.ibrahimsn.library.DataUsageManager;
import me.ibrahimsn.library.Interval;
import me.ibrahimsn.library.NetworkType;
import me.ibrahimsn.library.Usage;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TodayInternetUsage_Frag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TodayInternetUsage_Frag extends Fragment implements View.OnClickListener {
    private InterstitialAd interstitialAd;

    private static final String TAG = "Today Usage";

    private DataUsageInterface dataUsageInterface;
    private DataUsageManager manager;
    private String mSendingDataString, wSendingDataString;
    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        PageViewModel model = new ViewModelProvider( this ).get( PageViewModel.class );
        model.setIndex( TAG );
    }

    public TodayInternetUsage_Frag() {
    }

    public static TodayInternetUsage_Frag newInstance() {

        return new TodayInternetUsage_Frag();
    }

    @SuppressLint("HardwareIds")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_today_internet_usage_frag, container, false );
        sharedPreferences = getActivity().getSharedPreferences( "MY_PREFERENCES", Context.MODE_PRIVATE );

        Permissions permissions = new Permissions( getContext() );

        interstitialAd = new InterstitialAd( getActivity() );
        interstitialAd.setAdUnitId( getResources().getString( R.string.interstitial) );
        reqNewInterstitial();


        TextView wifiNetUsage_Tv = view.findViewById( R.id.todayWifiNetUsage_Tv );
        TextView mobileDataNetUsage_Tv = view.findViewById( R.id.todayMobileDataNetUsage_Tv );
        ImageView todayWifiNext_tv = view.findViewById( R.id.todayWifiNext_iv );
        ImageView todayMobileNext_tv = view.findViewById( R.id.todayMobileNext_iv );

        NetworkStatsManager networkStatsManager = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            networkStatsManager = (NetworkStatsManager) getContext().getSystemService( Context.NETWORK_STATS_SERVICE );
        }
        TelephonyManager telephonyManager = (TelephonyManager) getContext().getSystemService( Context.TELEPHONY_SERVICE );
        permissions.permission();
        if (permissions.permission()) {
            if (ActivityCompat.checkSelfPermission( getContext(), Manifest.permission.READ_PHONE_STATE ) != PackageManager.PERMISSION_GRANTED) {
                permissions.permission();
                return view;
            }
            else {
                Log.d( TAG, "onCreateView: " + telephonyManager.getSubscriberId() );
                manager = new DataUsageManager( Objects.requireNonNull( networkStatsManager ), telephonyManager.getSubscriberId() );
            }

        }

        // Monitor single interval


        Usage mTodayUsage = manager.getUsage( Interval.INSTANCE.getToday(), NetworkType.MOBILE );//data usage by mobile today

        float mDownloadUsage = mTodayUsage.getDownloads() / 1024 / 1024; // data in Mbs
        float mUploadUsage = mTodayUsage.getUploads() / 1024 / 1024;//
        float mTotalData = mDownloadUsage + mUploadUsage;
        if (mTotalData > 1024) {
            mTotalData = mTotalData / 1024;
            mSendingDataString = mTotalData + "Gb";
            mobileDataNetUsage_Tv.setText( mTotalData + "Gb" );
        } else {
            mSendingDataString = mTotalData + "Mb";
            mobileDataNetUsage_Tv.setText( mTotalData + "Mb" );
        }
        Usage wTodayUsage = manager.getUsage( Interval.INSTANCE.getToday(), NetworkType.WIFI );//Wifi data usage for today
        float wDownloadUsage = wTodayUsage.getDownloads() / 1024 / 1024; // data in Mbs
        float wUploadUsage = wTodayUsage.getUploads() / 1024 / 1024;//
        float wTotalData = wDownloadUsage + wUploadUsage;
        if (wTotalData > 1024) {
            wTotalData = wTotalData / 1024;
            wSendingDataString = wTotalData + "Gb";
            wifiNetUsage_Tv.setText( wTotalData + "Gb" );
        } else {
            wSendingDataString = wTotalData + "Mb";
            wifiNetUsage_Tv.setText( wTotalData + "Mb" );
        }

        float totalUsage = wDownloadUsage + wUploadUsage + mDownloadUsage + mUploadUsage; // data in Mbs

        todayWifiNext_tv.setOnClickListener( this );
        todayMobileNext_tv.setOnClickListener( this );

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @SuppressLint("ApplySharedPref")
    @Override
    public void onClick(View v) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        switch (v.getId()) {
            case R.id.todayWifiNext_iv:
            if (sharedPreferences.getBoolean( "is_purchase",false ))
            {
                Intent wIntent = new Intent( getContext(), DataUsageGraphsActivity.class );
                wIntent.putExtra( "TODAY_WIFI", true );
                wIntent.putExtra( "TODAY_WIFI_DATA", wSendingDataString );
                editor.putBoolean( "wifi", true ).commit();
                editor.putBoolean( "mobile", false ).commit();
                startActivity( wIntent );
            }
            else
                {
                    if (interstitialAd.isLoaded())
                    {
                        interstitialAd.show();
                    }else
                    {
                        Intent wIntent = new Intent( getContext(), DataUsageGraphsActivity.class );
                        wIntent.putExtra( "TODAY_WIFI", true );
                        wIntent.putExtra( "TODAY_WIFI_DATA", wSendingDataString );
                        editor.putBoolean( "wifi", true ).commit();
                        editor.putBoolean( "mobile", false ).commit();
                        startActivity( wIntent );
                    }
                    interstitialAd.setAdListener( new AdListener() {
                        @Override
                        public void onAdClosed() {
                            Intent wIntent = new Intent( getContext(), DataUsageGraphsActivity.class );
                            wIntent.putExtra( "TODAY_WIFI", true );
                            wIntent.putExtra( "TODAY_WIFI_DATA", wSendingDataString );
                            editor.putBoolean( "wifi", true ).commit();
                            editor.putBoolean( "mobile", false ).commit();
                            startActivity( wIntent );
                            reqNewInterstitial();
                        }
                    } );
                }


                break;
            case R.id.todayMobileNext_iv:
                Intent mIntent = new Intent( getContext(), DataUsageGraphsActivity.class );
                mIntent.putExtra( "TODAY_MOBILE", true );
                mIntent.putExtra( "TODAY_MOBILE_DATA", mSendingDataString );
                editor.putBoolean( "mobile", true ).commit();
                editor.putBoolean( "wifi", false ).commit();
                startActivity( mIntent );
                break;
        }
    }



    private void reqNewInterstitial() {
        interstitialAd.loadAd(new AdRequest.Builder().build());

    }
}
