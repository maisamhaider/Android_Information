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

public class WeeklyDataUsageGraphs_Frag extends Fragment {

    private static final String TAG = "Last Week";
    private Permissions permissions;
    private DataUsageManager manager;
    private boolean isMobile = false, isWifi = false;
    private SharedPreferences sharedPreferences;
    private Usage todayUsage, yesterday1, yesterday2, yesterday3, yesterday4, yesterday5, yesterday6, yesterday7;
    private BarChart barChart;

    public WeeklyDataUsageGraphs_Frag() {
        // Required empty public constructor
    }


    public static WeeklyDataUsageGraphs_Frag newInstance() {
        return new WeeklyDataUsageGraphs_Frag();
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
        View view = inflater.inflate( R.layout.fragment_weekly_data_usage_graphs, container, false );
//        String usageData = getArguments().get("data2").toString();
        sharedPreferences = getActivity().getSharedPreferences( "MY_PREFERENCES", Context.MODE_PRIVATE );
        permissions = new Permissions( getContext() );
        isMobile = sharedPreferences.getBoolean( "mobile", false );
        isWifi = sharedPreferences.getBoolean( "wifi", true );

        TextView weeklyBelowDataUsageDate_Tv, weeklyDataUsageMonth_Tv, weeklyDataUsageDate_Tv;
        weeklyDataUsageDate_Tv = view.findViewById( R.id.weeklyDataUsageDate_Tv );
        weeklyDataUsageMonth_Tv = view.findViewById( R.id.weeklyDataUsageMonth_Tv );
        weeklyBelowDataUsageDate_Tv = view.findViewById( R.id.weeklyBelowDataUsageDate_Tv );

        barChart = view.findViewById( R.id.weeklyBarChart_bc );

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
            todayUsage = manager.getUsage( Interval.INSTANCE.getWeek(), NetworkType.MOBILE );//data usage by mobile today
            yesterday1 = manager.getUsage( Interval.INSTANCE.customTime( -1, 1 ), NetworkType.MOBILE );
            yesterday2 = manager.getUsage( Interval.INSTANCE.customTime( -2, 1 ), NetworkType.MOBILE );
            yesterday3 = manager.getUsage( Interval.INSTANCE.customTime( -3, 1 ), NetworkType.MOBILE );
            yesterday4 = manager.getUsage( Interval.INSTANCE.customTime( -4, 1 ), NetworkType.MOBILE );
            yesterday5 = manager.getUsage( Interval.INSTANCE.customTime( -5, 1 ), NetworkType.MOBILE );
            yesterday6 = manager.getUsage( Interval.INSTANCE.customTime( -6, 1 ), NetworkType.MOBILE );
            yesterday7 = manager.getUsage( Interval.INSTANCE.customTime( -7, 1 ), NetworkType.MOBILE );

        } else {
            todayUsage = manager.getUsage( Interval.INSTANCE.getWeek(), NetworkType.WIFI );//data usage by mobile today
            yesterday1 = manager.getUsage( Interval.INSTANCE.customTime( -1, 1 ), NetworkType.WIFI );
            yesterday2 = manager.getUsage( Interval.INSTANCE.customTime( -2, 1 ), NetworkType.WIFI );
            yesterday3 = manager.getUsage( Interval.INSTANCE.customTime( -3, 1 ), NetworkType.WIFI );
            yesterday4 = manager.getUsage( Interval.INSTANCE.customTime( -4, 1 ), NetworkType.WIFI );
            yesterday5 = manager.getUsage( Interval.INSTANCE.customTime( -5, 1 ), NetworkType.WIFI );
            yesterday6 = manager.getUsage( Interval.INSTANCE.customTime( -6, 1 ), NetworkType.WIFI );
            yesterday7 = manager.getUsage( Interval.INSTANCE.customTime( -7, 1 ), NetworkType.WIFI );
        }


        float usageData = (todayUsage.getDownloads() + todayUsage.getUploads()) / 1024 / 1024;


        weeklyDataUsageDate_Tv.setText( TimeSettingsClass.getLastWeekFirstDay() + "-" + TimeSettingsClass.getTodayDate() );
        weeklyDataUsageMonth_Tv.setText( TimeSettingsClass.getCurrentMonth() );

        if (usageData > 1024) {
            usageData = usageData / 1024;
            weeklyBelowDataUsageDate_Tv.setText( "you have used " + String.format( "%.2f", usageData ) + " " + "Gb" + " till now" );
        } else {
            weeklyBelowDataUsageDate_Tv.setText( "you have used " + String.format( "%.2f", usageData ) + " " + "Mb" + " till now" );
        }

        setData( 7, 7 );

//
//        List<Fill> data = new ArrayList<>();
//        data.add( new ValueDataEntry( , data7 ) );
//        data.add( new ValueDataEntry( , data6 ) );
//        data.add( new ValueDataEntry( TimeSettingsClass.getCustomDate( -5 ), data5 ) );
//        data.add( new ValueDataEntry( TimeSettingsClass.getCustomDate( -4 ), data4 ) );
//        data.add( new ValueDataEntry( TimeSettingsClass.getCustomDate( -3 ), data3 ) );
//        data.add( new ValueDataEntry( TimeSettingsClass.getCustomDate( -2 ), data2 ) );
//        data.add( new ValueDataEntry( TimeSettingsClass.getCustomDate( -1 ), data1 ) );

//
//        Column column = cartesian.column( data );
//
//        column.tooltip()
//                .titleFormat( "{%X}" )
//                .position( Position.CENTER_BOTTOM )
//                .anchor( Anchor.CENTER_BOTTOM )
//                .offsetX( 0d )
//                .offsetY( 6d )
//                .format( "{%Value}{groupsSeparator: } Mb" );
//
//        cartesian.animation( true );
//        cartesian.title( "Weekly data usage" );
//
//        cartesian.yScale().minimum( 0d );
//
//        cartesian.yAxis( 0 ).labels().format( "{%Value}{groupsSeparator: } Mb" );
//
//        cartesian.tooltip().positionMode( TooltipPositionMode.POINT );
//        cartesian.interactivity().hoverMode( HoverMode.BY_X );
//
//        cartesian.xAxis( 0 ).title( "Time" );
//        cartesian.yAxis( 0 ).title( "Data" );
//
//        anyChartView.setChart( cartesian );


        return view;
    }

    private void setData(int count, float range) {

        List<Float> myData = new ArrayList<>();
        float data1 = (yesterday1.getDownloads() + yesterday1.getUploads()) / 1024 / 1024;
        float data2 = (yesterday2.getDownloads() + yesterday2.getUploads()) / 1024 / 1024;
        float data3 = (yesterday3.getDownloads() + yesterday3.getUploads()) / 1024 / 1024;
        float data4 = (yesterday4.getDownloads() + yesterday4.getUploads()) / 1024 / 1024;
        float data5 = (yesterday5.getDownloads() + yesterday5.getUploads()) / 1024 / 1024;
        float data6 = (yesterday6.getDownloads() + yesterday6.getUploads()) / 1024 / 1024;
        float data7 = (yesterday7.getDownloads() + yesterday7.getUploads()) / 1024 / 1024;

        myData.add( data7 );
        myData.add( data6 );
        myData.add( data5 );
        myData.add( data4 );
        myData.add( data3 );
        myData.add( data2 );
        myData.add( data1 );

        List<BarEntry> values = new ArrayList<>();
        values.add( new BarEntry( 1, myData.get( 0 ) ) );
        values.add( new BarEntry( 2, myData.get( 1 ) ) );
        values.add( new BarEntry( 3, myData.get( 2 ) ) );
        values.add( new BarEntry( 4, myData.get( 3 ) ) );
        values.add( new BarEntry( 5, myData.get( 4 ) ) );
        values.add( new BarEntry( 6, myData.get( 5 ) ) );
        values.add( new BarEntry( 7, myData.get( 6 ) ) );

        final ArrayList<String> xAxisLabel = new ArrayList<>();
        xAxisLabel.add( TimeSettingsClass.getCustomDate1( -8 ) );
        xAxisLabel.add( TimeSettingsClass.getCustomDate1( -7 ) );
        xAxisLabel.add( TimeSettingsClass.getCustomDate1( -6 ) );
        xAxisLabel.add( TimeSettingsClass.getCustomDate1( -5 ) );
        xAxisLabel.add( TimeSettingsClass.getCustomDate1( -4 ) );
        xAxisLabel.add( TimeSettingsClass.getCustomDate1( -3 ) );
        xAxisLabel.add( TimeSettingsClass.getCustomDate1( -2 ) );
        xAxisLabel.add( TimeSettingsClass.getCustomDate1( -1 ) );

        BarDataSet set1;


        if (barChart.getData() != null && barChart.getData().getDataSetCount() >= 0) {
            set1 = (BarDataSet) barChart.getData().getDataSetByIndex( 0 );
            set1.setValues( values );
            barChart.getData().notifyDataChanged();
            barChart.notifyDataSetChanged();

        } else {
            set1 = new BarDataSet( values, "Per Day Data Usage (in Mega Bytes (Mbs))" );

            set1.setDrawIcons( false );
            set1.setColor( Color.parseColor( "#FFFFFF" ) );
            set1.setValueTextColor( Color.parseColor( "#FFFFFF" ) );
            set1.setBarBorderColor(  Color.parseColor( "#78BDC4" )  );


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

            barChart.animateY(2000);
            barChart.setData( data );
        }
    }

    public String returnWantedDataForm(float data) {
        String returnData = null;
        if (data > 1024) {
            returnData = data / 1024 + "Gb";
        } else {
            returnData = data + "Mb";
        }
        return returnData;
    }

}
