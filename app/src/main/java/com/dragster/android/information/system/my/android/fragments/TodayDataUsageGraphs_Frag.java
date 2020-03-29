package com.dragster.android.information.system.my.android.fragments;

import android.Manifest;
import android.app.usage.NetworkStatsManager;
import android.content.Context;
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
import android.widget.TextView;

import com.dragster.android.information.system.my.android.models.PageViewModel;
import com.dragster.android.information.system.my.android.utilities.TimeSettingsClass;
import com.dragster.android.information.system.my.android.R;
import com.dragster.android.information.system.my.android.classes.Permissions;

import me.ibrahimsn.library.DataUsageManager;
import me.ibrahimsn.library.Interval;
import me.ibrahimsn.library.NetworkType;
import me.ibrahimsn.library.Usage;

public class TodayDataUsageGraphs_Frag extends Fragment {

    private static final String TAG = "Today";
    private TodayInternetUsage_Frag todayInternetUsage_frag;
    private TextView firstTagTotalDataUsagePrefix_LL, firstTagTotalDataUsage_Tv;
    private String prefix;
    private boolean isMobile = false, isWifi = false;
    private DataUsageManager manager;
    private SharedPreferences sharedPreferences;
    private Usage todayUsage;

    public TodayDataUsageGraphs_Frag() {
        // Required empty public constructor
    }


    public static TodayDataUsageGraphs_Frag newInstance() {
        return new TodayDataUsageGraphs_Frag();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        PageViewModel pageViewModel = new ViewModelProvider( this ).get( PageViewModel.class );
        pageViewModel.setIndex( TAG );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_today_data_usage_graphs_, container, false );
        Permissions permissions = new Permissions( getContext() );
        sharedPreferences = getActivity().getSharedPreferences( "MY_PREFERENCES", Context.MODE_PRIVATE );

        isMobile = sharedPreferences.getBoolean( "mobile", false );
        isWifi = sharedPreferences.getBoolean( "wifi", true );

        todayInternetUsage_frag = new TodayInternetUsage_Frag();
        TextView BelowDataUsageDate_Tv, dataUsageMonth_Tv, dataUsageDate_Tv;


        BelowDataUsageDate_Tv = view.findViewById( R.id.BelowDataUsageDate_Tv );
        dataUsageMonth_Tv = view.findViewById( R.id.dataUsageMonth_Tv );
        dataUsageDate_Tv = view.findViewById( R.id.dataUsageDate_Tv );
        firstTagTotalDataUsage_Tv = view.findViewById( R.id.firstTagTotalDataUsage_Tv );
        firstTagTotalDataUsagePrefix_LL = view.findViewById( R.id.firstTagTotalDataUsagePrefix_LL );

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
        if (isMobile) {
            todayUsage = manager.getUsage( Interval.INSTANCE.getToday(), NetworkType.MOBILE );//data usage by mobile today

        } else {
            todayUsage = manager.getUsage( Interval.INSTANCE.getToday(), NetworkType.WIFI );//data usage by mobile today
        }



        float usageData = (todayUsage.getDownloads() + todayUsage.getUploads())/1024/ 1024;



        dataUsageDate_Tv.setText( TimeSettingsClass.getTodayDate() );
        dataUsageMonth_Tv.setText( TimeSettingsClass.getCurrentMonth() );

        if (usageData > 1024) {
            usageData = usageData / 1024;
            firstTagTotalDataUsage_Tv.setText(  String.format( "%.2f", usageData  ) );
            firstTagTotalDataUsagePrefix_LL.setText( "Gb" );
            BelowDataUsageDate_Tv.setText( "you have used " + String.format( "%.2f", usageData  ) + " " + "Gb" + " till now" );
        } else {
            firstTagTotalDataUsage_Tv.setText(  String.format( "%.2f", usageData  ));
            firstTagTotalDataUsagePrefix_LL.setText( "Mb" );
            BelowDataUsageDate_Tv.setText( "you have used " +String.format( "%.2f", usageData  )  + " " + "Mb" + " till now" );
        }

        return view;
    }

}
