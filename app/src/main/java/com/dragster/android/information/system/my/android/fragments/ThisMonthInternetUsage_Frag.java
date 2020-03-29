package com.dragster.android.information.system.my.android.fragments;

import android.Manifest;
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


public class ThisMonthInternetUsage_Frag extends Fragment implements View.OnClickListener {
    private InterstitialAd interstitialAd;

    private static final String TAG = "Monthly Usage";
    private DataUsageManager manager;
    private String wSendingDataString,mSendingDataString;
    private SharedPreferences sharedPreferences;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        PageViewModel model = new ViewModelProvider( this ).get( PageViewModel.class );
        model.setIndex( TAG );
    }

    public static ThisMonthInternetUsage_Frag newInstance() {
        return new ThisMonthInternetUsage_Frag();
    }

    public ThisMonthInternetUsage_Frag() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_monthly_internet_usage_frag, container, false );
        sharedPreferences = getActivity().getSharedPreferences( "MY_PREFERENCES",Context.MODE_PRIVATE );
        Permissions permissions = new Permissions( getContext() );

        interstitialAd = new InterstitialAd( getActivity() );
        interstitialAd.setAdUnitId( getResources().getString( R.string.interstitial) );
        reqNewInterstitial();

        TextView thisMonthWifiNetUsage_Tv = view.findViewById( R.id.thisMonthWifiNetUsage_Tv );
        TextView thisMonthMobileDataNetUsage_Tv = view.findViewById( R.id.thisMonthMobileDataNetUsage_Tv );
        ImageView monthlyWifiNext_iv = view.findViewById( R.id.monthlyWifiNext_iv );
        ImageView monthlyMobileNext_iv = view.findViewById( R.id.monthlyMobileNext_iv );

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
        Usage mTodayUsage = manager.getUsage( Interval.INSTANCE.getMonth(), NetworkType.MOBILE );//data usage by mobile today

        float mDownloadUsage = mTodayUsage.getDownloads() / 1024 / 1024; // data in Mbs
        float mUploadUsage = mTodayUsage.getUploads() / 1024 / 1024;//
        float mTotalData = mDownloadUsage + mUploadUsage;
        if (mTotalData > 1024) {
            mTotalData = mTotalData / 1024;
            mSendingDataString = mTotalData+"Gb";
            thisMonthMobileDataNetUsage_Tv.setText( String.format( "%.2f", mTotalData ) + "Gb" );
        } else {
            mSendingDataString = mTotalData+"Mb";
            thisMonthMobileDataNetUsage_Tv.setText( String.format( "%.2f", mTotalData ) + "Mb" );
        }
        Usage wTodayUsage = manager.getUsage( Interval.INSTANCE.getMonth(), NetworkType.WIFI );//Wifi data usage for today
        float wDownloadUsage = wTodayUsage.getDownloads() / 1024 / 1024; // data in Mbs
        float wUploadUsage = wTodayUsage.getUploads() / 1024 / 1024;//
        float wTotalData = wDownloadUsage + wUploadUsage;
        if (wTotalData > 1024) {
            wTotalData = wTotalData / 1024;
            wSendingDataString = wTotalData+"Gb";
            thisMonthWifiNetUsage_Tv.setText( String.format( "%.2f", wTotalData ) + "Gb" );
        } else {
            wSendingDataString = wTotalData+"Mb";
            thisMonthWifiNetUsage_Tv.setText( String.format( "%.2f", wTotalData ) + "Mb" );
        }
        float totalUsage = wDownloadUsage + wUploadUsage + mDownloadUsage + mUploadUsage; // data in Mbs

        if (totalUsage > 1024) {
            totalUsage = totalUsage / 1024;
//            thisMonthTotalNetUsage_tv.setText( String.format( "%.2f", totalUsage ) + "Gb" );
        } else {
//            thisMonthTotalNetUsage_tv.setText( String.format( "%.2f", totalUsage ) + "Mb" );
        }
        monthlyWifiNext_iv.setOnClickListener( this );
        monthlyMobileNext_iv.setOnClickListener( this );
        return view;
    }

    @Override
    public void onClick(View v) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        switch (v.getId()) {
            case R.id.monthlyWifiNext_iv:
                Intent wIntent = new Intent( getContext(), DataUsageGraphsActivity.class );
                wIntent.putExtra( "THIS_MONTH_WIFI", true );
                wIntent.putExtra( "THIS_MONTH_WIFI_DATA", wSendingDataString );
                editor.putBoolean( "wifi",true ).commit();
                editor.putBoolean( "mobile",false ).commit();
                startActivity( wIntent );
                break;
            case R.id.monthlyMobileNext_iv:

            if (sharedPreferences.getBoolean( "is_purchase",false))
            {
                Intent mIntent = new Intent( getContext(), DataUsageGraphsActivity.class );
                mIntent.putExtra( "THIS_MONTH_MOBILE", true );
                mIntent.putExtra( "THIS_MONTH_MOBILE_DATA", mSendingDataString );
                editor.putBoolean( "mobile",true ).commit();
                editor.putBoolean( "wifi",false ).commit();
                startActivity( mIntent );
            }else
            {
                if (interstitialAd.isLoaded())
                {
                    interstitialAd.show();
                }else
                {
                    Intent mIntent = new Intent( getContext(), DataUsageGraphsActivity.class );
                    mIntent.putExtra( "THIS_MONTH_MOBILE", true );
                    mIntent.putExtra( "THIS_MONTH_MOBILE_DATA", mSendingDataString );
                    editor.putBoolean( "mobile",true ).commit();
                    editor.putBoolean( "wifi",false ).commit();
                    startActivity( mIntent );
                }
                interstitialAd.setAdListener( new AdListener() {
                    @Override
                    public void onAdClosed() {
                        Intent mIntent = new Intent( getContext(), DataUsageGraphsActivity.class );
                        mIntent.putExtra( "THIS_MONTH_MOBILE", true );
                        mIntent.putExtra( "THIS_MONTH_MOBILE_DATA", mSendingDataString );
                        editor.putBoolean( "mobile",true ).commit();
                        editor.putBoolean( "wifi",false ).commit();
                        startActivity( mIntent );
                        reqNewInterstitial();
                    }
                } );
            }

                break;
        }
    }
    private void reqNewInterstitial() {
        interstitialAd.loadAd(new AdRequest.Builder().build());

    }
}
