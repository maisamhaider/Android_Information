package com.dragster.android.information.system.my.android.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.usage.NetworkStatsManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.TextView;

import com.dragster.android.information.system.my.android.classes.Permissions;
import com.dragster.android.information.system.my.android.R;
import com.dragster.android.information.system.my.android.adapters.InternetUsageTabsAdapter;
import com.dragster.android.information.system.my.android.fragments.TodayInternetUsage_Frag;
import com.dragster.android.information.system.my.android.interfaces.DataUsageInterface;
import com.dragster.android.information.system.my.android.utilities.GetSomething;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.tabs.TabLayout;

import me.ibrahimsn.library.DataUsageManager;
import me.ibrahimsn.library.Interval;
import me.ibrahimsn.library.NetworkType;
import me.ibrahimsn.library.Usage;

public class InternetUsage extends AppCompatActivity   {
    private float data = 0;
    private Permissions permissions;
    private DataUsageManager manager;
    private TextView mainInternetDataTotalUsage_tv, mainInternetDataPrefix_tv,internetDataUsage_tv2;
    private AdView mAdView;
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_internet_usage );
        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );

        InternetUsageTabsAdapter internetUsageTabsAdapter = new InternetUsageTabsAdapter( this, this.getSupportFragmentManager() );
        permissions = new Permissions( this );
        mAdView = findViewById( R.id.internetDataUsage_adv );
        ViewPager viewPager = findViewById( R.id.internetFrag_Vp );
        TabLayout tabLayout = findViewById( R.id.intervalTabs_TL );
        viewPager.setAdapter( internetUsageTabsAdapter );
        tabLayout.setupWithViewPager( viewPager );
        tabLayout.setTabTextColors( ColorStateList.valueOf( Color.parseColor( "#FFFFFF" ) ) );
        tabLayout.setSelectedTabIndicatorColor( Color.parseColor( "#093343" ) );
        MainActivity mainActivity = new MainActivity();
        //banner
        MobileAds.initialize( this, getResources().getString( R.string.banner ) );
        GetSomething getSomething = new GetSomething();
        MobileAds.initialize( this, getResources().getString( R.string.banner ) );
        if (mainActivity.isPaid) {
            mAdView.setVisibility( View.GONE );
        } else {
            getSomething.bannerAD( mAdView );
        }


        internetDataUsage_tv2 = findViewById( R.id.internetDataUsage_tv2 );

        mainInternetDataTotalUsage_tv = findViewById( R.id.mainInternetDataTotalUsage_tv );
        mainInternetDataPrefix_tv = findViewById( R.id.mainInternetDataPrefix_tv );


        NetworkStatsManager networkStatsManager = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            networkStatsManager = (NetworkStatsManager) getSystemService( Context.NETWORK_STATS_SERVICE );
        }
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService( Context.TELEPHONY_SERVICE );
        permissions.permission();
        if (permissions.permission()) {
            if (ActivityCompat.checkSelfPermission( this, Manifest.permission.READ_PHONE_STATE ) != PackageManager.PERMISSION_GRANTED) {
                permissions.permission();
                return;
            }
            manager = new DataUsageManager( networkStatsManager, telephonyManager.getSubscriberId() );
        }
        getThisMonthInternetUsage();

        // Monitor single interval





    }
    public void getTodayInternetUsage()
    {
        Usage wTodayUsage = manager.getUsage( Interval.INSTANCE.getToday(), NetworkType.WIFI );//Wifi data usage for today
        Usage mTodayUsage = manager.getUsage( Interval.INSTANCE.getToday(), NetworkType.MOBILE );//data usage by mobile today
        float mDownloadUsage = mTodayUsage.getDownloads() / 1024 / 1024; // data in Mbs
        float mUploadUsage = mTodayUsage.getUploads() / 1024 / 1024;//
        float wDownloadUsage = wTodayUsage.getDownloads() / 1024 / 1024; // data in Mbs
        float wUploadUsage = wTodayUsage.getUploads() / 1024 / 1024;//
        float todayTotalUsage = wDownloadUsage + wUploadUsage + mDownloadUsage + mUploadUsage;
        internetDataUsage_tv2.setText( "Today usage" );
        if ( todayTotalUsage > 1024) {
            todayTotalUsage   =  todayTotalUsage / 1024; //data in Gbs
            mainInternetDataTotalUsage_tv.setText( todayTotalUsage+"" );
            mainInternetDataPrefix_tv.setText( "Gb" );
        } else {
            mainInternetDataTotalUsage_tv.setText( todayTotalUsage + "" );
            mainInternetDataPrefix_tv.setText( "Mb" );
        }
    }
    public void getWeeklyInternetUsage()
    {
        internetDataUsage_tv2.setText( "Weekly usage" );
        Usage mWeeklyUsage =  manager.getUsage( Interval.INSTANCE.getWeek(), NetworkType.MOBILE );
        Usage wMonthlyUsage = manager.getUsage( Interval.INSTANCE.getWeek(), NetworkType.WIFI );

        float mDownloadUsage = mWeeklyUsage.getDownloads() / 1024 / 1024; // data in Mbs
        float mUploadUsage = mWeeklyUsage.getUploads() / 1024 / 1024;//
        float wDownloadUsage = wMonthlyUsage.getDownloads() / 1024 / 1024; // data in Mbs
        float wUploadUsage = wMonthlyUsage.getUploads() / 1024 / 1024;//
        float weeklyTotalUsage = wDownloadUsage + wUploadUsage + mDownloadUsage + mUploadUsage;
        if (weeklyTotalUsage > 1024) {
            weeklyTotalUsage = weeklyTotalUsage / 1024; //data in Gbs
            mainInternetDataTotalUsage_tv.setText(String.format( "%.1f", weeklyTotalUsage));
            mainInternetDataPrefix_tv.setText( "Gb" );
        } else {
            mainInternetDataTotalUsage_tv.setText(String.format( "%.1f", weeklyTotalUsage) );
            mainInternetDataPrefix_tv.setText( "Mb" );
        }
    }
    public void getThisMonthInternetUsage()
    {
        internetDataUsage_tv2.setText( "Monthly usage" );
        Usage mMonthlyUsage =  manager.getUsage( Interval.INSTANCE.getMonth(), NetworkType.MOBILE );
        Usage wMonthlyUsage = manager.getUsage( Interval.INSTANCE.getMonth(), NetworkType.WIFI );//data usage by mobile today
        float mDownloadUsage = mMonthlyUsage.getDownloads() / 1024 / 1024; // data in Mbs
        float mUploadUsage = mMonthlyUsage.getUploads() / 1024 / 1024;//
        float wDownloadUsage = wMonthlyUsage.getDownloads() / 1024 / 1024; // data in Mbs
        float wUploadUsage = wMonthlyUsage.getUploads() / 1024 / 1024;//
        float monthlyTotalUsage = wDownloadUsage + wUploadUsage + mDownloadUsage + mUploadUsage;
        if (monthlyTotalUsage > 1024) {
            monthlyTotalUsage = monthlyTotalUsage / 1024; //data in Gbs
            mainInternetDataTotalUsage_tv.setText( String.format( "%.1f", monthlyTotalUsage) );
            mainInternetDataPrefix_tv.setText( "Gb" );
        } else {
            mainInternetDataTotalUsage_tv.setText(String.format( "%.1f",monthlyTotalUsage)  );
            mainInternetDataPrefix_tv.setText( "Mb" );
        }

    }

}
