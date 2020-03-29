package com.dragster.android.information.system.my.android.fragments;

import android.Manifest;
import android.app.usage.NetworkStatsManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.dragster.android.information.system.my.android.models.PageViewModel;
import com.dragster.android.information.system.my.android.utilities.TimeSettingsClass;
import com.dragster.android.information.system.my.android.R;
import com.dragster.android.information.system.my.android.classes.Permissions;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.List;

import me.ibrahimsn.library.DataUsageManager;
import me.ibrahimsn.library.Interval;
import me.ibrahimsn.library.NetworkType;
import me.ibrahimsn.library.Usage;

public class MonthlyDataUsageGraphs_Frag extends Fragment {

    private static final String TAG = "This Month";
    private SharedPreferences sharedPreferences;
    private boolean isMobile = false, isWifi = false;
    private Permissions permissions;
    private DataUsageManager manager;
    private Usage todayUsage;
    private Usage firstWeek1, firstWeek2, firstWeek3, firstWeek4;
    private BarChart barChart;
    ;

    public MonthlyDataUsageGraphs_Frag() {
        // Required empty public constructor
    }


    public static MonthlyDataUsageGraphs_Frag newInstance() {
        return new MonthlyDataUsageGraphs_Frag();
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
        View view = inflater.inflate( R.layout.fragment_monthly_data_usage_graphs, container, false );
        sharedPreferences = getActivity().getSharedPreferences( "MY_PREFERENCES", Context.MODE_PRIVATE );
        permissions = new Permissions( getContext() );
        isMobile = sharedPreferences.getBoolean( "mobile", false );
        isWifi = sharedPreferences.getBoolean( "wifi", true );

        TextView monthlyBelowDataUsageDate_Tv, monthlyDataUsageMonth_Tv, monthlyTotalDataUsage_Tv;
         monthlyDataUsageMonth_Tv = view.findViewById( R.id.monthlyDataUsageMonth_Tv );
        monthlyBelowDataUsageDate_Tv = view.findViewById( R.id.monthlyBelowDataUsageDate_Tv );
        barChart = view.findViewById( R.id.monthlyBarChart_bc );

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
            todayUsage = manager.getUsage( Interval.INSTANCE.getMonth(), NetworkType.MOBILE );//data usage by mobile today
            firstWeek1 = manager.getUsage( Interval.INSTANCE.customTime( 1, 7 ), NetworkType.MOBILE );
            firstWeek2 = manager.getUsage( Interval.INSTANCE.customTime( 8, 14 ), NetworkType.MOBILE );
            firstWeek3 = manager.getUsage( Interval.INSTANCE.customTime( 15, 21 ), NetworkType.MOBILE );
            firstWeek4 = manager.getUsage( Interval.INSTANCE.customTime( 22, 28 ), NetworkType.MOBILE );

        } else {
            todayUsage = manager.getUsage( Interval.INSTANCE.getMonth(), NetworkType.WIFI );//data usage by mobile today
            firstWeek1 = manager.getUsage( Interval.INSTANCE.customTime( 1, 7 ), NetworkType.WIFI );
            firstWeek2 = manager.getUsage( Interval.INSTANCE.customTime( 8, 14 ), NetworkType.WIFI );
            firstWeek3 = manager.getUsage( Interval.INSTANCE.customTime( 15, 21 ), NetworkType.WIFI );
            firstWeek4 = manager.getUsage( Interval.INSTANCE.customTime( 22, 28 ), NetworkType.WIFI );

        }


        float usageData = (todayUsage.getDownloads() + todayUsage.getUploads()) / 1024 / 1024;


//        monthlyTotalDataUsage_Tv.setText( TimeSettingsClass.getLastWeekFirstDay() + "-" + TimeSettingsClass.getTodayDate() );
        monthlyDataUsageMonth_Tv.setText( TimeSettingsClass.getCurrentMonth() );

        if (usageData > 1024) {
            usageData = usageData / 1024;
            monthlyBelowDataUsageDate_Tv.setText( "you have used " + String.format( "%.2f", usageData ) + " " + "Gb" + " till now" );
        } else {
            monthlyBelowDataUsageDate_Tv.setText( "you have used " + String.format( "%.2f", usageData ) + " " + "Mb" + " till now" );
        }
        setData( 6, 6 );

        return view;
    }

    private void setData(int count, float range) {

        List<Float> myData = new ArrayList<>();
        float data1 = (firstWeek1.getDownloads() + firstWeek1.getUploads()) / 1024 / 1024;
        float data2 = (firstWeek2.getDownloads() + firstWeek2.getUploads()) / 1024 / 1024;
        float data3 = (firstWeek3.getDownloads() + firstWeek3.getUploads()) / 1024 / 1024;
        float data4 = (firstWeek4.getDownloads() + firstWeek4.getUploads()) / 1024 / 1024;


        myData.add( data4 );
        myData.add( data3 );
        myData.add( data2 );
        myData.add( data1 );


        List<BarEntry> values = new ArrayList<>();
        values.add( new BarEntry( 1, myData.get( 0 ) ) );
        values.add( new BarEntry( 2, myData.get( 1 ) ) );
        values.add( new BarEntry( 3, myData.get( 2 ) ) );
        values.add( new BarEntry( 4, myData.get( 3 ) ) );


        final ArrayList<String> xAxisLabel = new ArrayList<>();
        xAxisLabel.add( TimeSettingsClass.getCustomDate2( 0, 7 ) );
        xAxisLabel.add( TimeSettingsClass.getCustomDate2( 8, 15 ) );
        xAxisLabel.add( TimeSettingsClass.getCustomDate2( 16, 21 ) );
        xAxisLabel.add( TimeSettingsClass.getCustomDate2( 22, 28 ) );

        BarDataSet set1;
        if (barChart.getData() != null && barChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) barChart.getData().getDataSetByIndex( 0 );
            set1.setValues( values );
            barChart.getData().notifyDataChanged();
            barChart.notifyDataSetChanged();

        } else {
            set1 = new BarDataSet( values, "monthly usage in Mega Bytes(Mbs)" );

            set1.setDrawIcons( false );
            set1.setColor( Color.parseColor( "#FFFFFF" ) );
            set1.setValueTextColor( Color.parseColor( "#FFFFFF" ) );
            set1.setBarBorderColor( Color.parseColor( "#78BDC4" ) );

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add( set1 );

            BarData data = new BarData( dataSets );
            data.setValueTextSize( 10f );
            data.setValueTypeface( Typeface.defaultFromStyle( Typeface.BOLD ) );
            data.setBarWidth( 0.5f );

            barChart.getAxisRight().setEnabled( false );
            YAxis yAxis = barChart.getAxisLeft();
            yAxis.setTextColor( Color.parseColor( "#FFFFFF" ) );

            barChart.getXAxis().setPosition( XAxis.XAxisPosition.BOTTOM );
            XAxis xAxis = barChart.getXAxis();
            xAxis.setTextColor( Color.parseColor( "#FFFFFF" ) );
            xAxis.setValueFormatter( new IndexAxisValueFormatter( xAxisLabel ) );
            barChart.getDescription().setEnabled( false );
            barChart.getLegend().setTextColor( Color.parseColor( "#FFFFFF" ) );

            barChart.animateY( 2000 );
            barChart.setData( data );
        }

    }
}
