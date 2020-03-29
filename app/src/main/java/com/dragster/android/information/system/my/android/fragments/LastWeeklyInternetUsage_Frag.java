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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dragster.android.information.system.my.android.activities.InternetSpeed;
import com.dragster.android.information.system.my.android.models.PageViewModel;
import com.dragster.android.information.system.my.android.R;
import com.dragster.android.information.system.my.android.activities.DataUsageGraphsActivity;
import com.dragster.android.information.system.my.android.classes.Permissions;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import me.ibrahimsn.library.DataUsageManager;
import me.ibrahimsn.library.Interval;
import me.ibrahimsn.library.NetworkType;
import me.ibrahimsn.library.Usage;

public class LastWeeklyInternetUsage_Frag extends Fragment implements View.OnClickListener {
    private static final String TAG = " Weekly Usage ";
    private DataUsageManager manager;
    private String mSendingDataString,wSendingDataString;
    private  SharedPreferences sharedPreference;
    private InterstitialAd interstitialAd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        PageViewModel model = new ViewModelProvider( this ).get( PageViewModel.class );
        model.setIndex( TAG );
    }

    public LastWeeklyInternetUsage_Frag() {
    }

    public static LastWeeklyInternetUsage_Frag newInstance() {
        return new LastWeeklyInternetUsage_Frag();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_weekly_internet_usage_frag, container, false );
        sharedPreference = getActivity().getSharedPreferences( "MY_PREFERENCES", Context.MODE_PRIVATE );

        Permissions permissions = new Permissions( getContext() );
        interstitialAd = new InterstitialAd( getActivity() );
        interstitialAd.setAdUnitId( getResources().getString( R.string.interstitial) );
        reqNewInterstitial();

        TextView lastWeekMobileDataNetUsage_Tv = view.findViewById( R.id.lastWeekMobileDataNetUsage_Tv );
        TextView lastWeekWifiNetUsage_Tv = view.findViewById( R.id.lastWeekWifiNetUsage_Tv );
        ImageView weeklyWifiNext_iv = view.findViewById( R.id.weeklyWifiNext_iv );
        ImageView weeklyMobileNext_iv = view.findViewById( R.id.weeklyMobileNext_iv );
//        TextView lastWeekTotalNetUsage_tv = view.findViewById( R.id.lastWeekTotalNetUsage_tv );

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
            manager = new DataUsageManager( networkStatsManager, telephonyManager.getSubscriberId() );
        }


        // Monitor single interval
        Usage mTodayUsage = manager.getUsage( Interval.INSTANCE.getWeek(), NetworkType.MOBILE );//data usage by mobile today

        float mDownloadUsage = mTodayUsage.getDownloads() / 1024 / 1024; // data in Mbs
        float mUploadUsage = mTodayUsage.getUploads() / 1024 / 1024;//
        float mTotalData = mDownloadUsage + mUploadUsage;
        if (mTotalData > 1024) {
            mTotalData = mTotalData / 1024;
            mSendingDataString = mTotalData +"Gb";
            lastWeekMobileDataNetUsage_Tv.setText(String.format( "%.1f",mTotalData )  + "Gb" );
        } else {
            mSendingDataString = mTotalData+"Gb";
            lastWeekMobileDataNetUsage_Tv.setText(String.format( "%.1f",mTotalData )   + "Mb" );
        }
        Usage wTodayUsage = manager.getUsage( Interval.INSTANCE.getWeek(), NetworkType.WIFI );//Wifi data usage for today
        float wDownloadUsage = wTodayUsage.getDownloads() / 1024 / 1024; // data in Mbs
        float wUploadUsage = wTodayUsage.getUploads() / 1024 / 1024;//
        float wTotalData = wDownloadUsage + wUploadUsage;
        if (wTotalData > 1024) {
            wTotalData = wTotalData / 1024;
            wSendingDataString = wTotalData +"Gb";
            lastWeekWifiNetUsage_Tv.setText(String.format( "%.1f",wTotalData )  + "Gb" );
        } else {
            wSendingDataString  = wTotalData +"Mb";
            lastWeekWifiNetUsage_Tv.setText(String.format( "%.1f",wTotalData )  + "Mb" );
        }

        weeklyWifiNext_iv.setOnClickListener( this );
        weeklyMobileNext_iv.setOnClickListener( this );
        return view;
    }

    @SuppressLint("ApplySharedPref")
    @Override
    public void onClick(View v) {
        SharedPreferences.Editor editor = sharedPreference.edit();
        switch (v.getId()) {
            case R.id.weeklyWifiNext_iv:
                if (sharedPreference.getBoolean( "is_purchase",false ))
                {
                    Intent wIntent = new Intent( getContext(), DataUsageGraphsActivity.class );
                    wIntent.putExtra( "LAST_WEEK_WIFI", true );
                    wIntent.putExtra( "LAST_WEEK_DATA", wSendingDataString );
                    editor.putBoolean( "wifi",true ).commit();
                    editor.putBoolean( "mobile",false ).commit();
                    startActivity( wIntent );
                }else
                {
                    if (interstitialAd.isLoaded())
                    {
                        interstitialAd.show();
                    }else
                    {

                    }
                    interstitialAd.setAdListener( new AdListener() {
                        @Override
                        public void onAdClosed() {
                            Intent wIntent = new Intent( getContext(), DataUsageGraphsActivity.class );
                            wIntent.putExtra( "LAST_WEEK_WIFI", true );
                            wIntent.putExtra( "LAST_WEEK_DATA", wSendingDataString );
                            editor.putBoolean( "wifi",true ).commit();
                            editor.putBoolean( "mobile",false ).commit();
                            startActivity( wIntent );
                            reqNewInterstitial();
                        }
                    } );
                }


                break;
            case R.id.weeklyMobileNext_iv:
                Intent mIntent = new Intent( getContext(), DataUsageGraphsActivity.class );
                mIntent.putExtra( "LAST_WEEK_MOBILE", true );
                mIntent.putExtra( "LAST_WEEK_MOBILE_DATA", mSendingDataString );
                editor.putBoolean( "mobile",true ).commit();
                editor.putBoolean( "wifi",false ).commit();

                startActivity( mIntent );
                break;
        }
    }
    private void reqNewInterstitial() {
        interstitialAd.loadAd(new AdRequest.Builder().build());

    }
}
