package com.dragster.android.information.system.my.android.utilities;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class TimeSettingsClass {

    public static String getFormattedDateFromMilliseconds(long milliSeconds)
    {
        // Create a DateFormatter object for displaying date in specified format.
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yy");

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());

    }
    @SuppressLint("DefaultLocale")
    public static String getHoursFromMilliSecs(long milliSecs)
    {
        return  String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(milliSecs),
                TimeUnit.MILLISECONDS.toMinutes(milliSecs) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliSecs)), // The change is in this line
                TimeUnit.MILLISECONDS.toSeconds(milliSecs) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliSecs)));
    }

    public static String getLastWeekFirstDay()
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "dd" );
        calendar.add( Calendar.DATE,-7 );
        String data = simpleDateFormat.format( calendar.getTime() );
        return data;
    }
    public static String getCustomDate1(int day )
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "dd MMM" );
        calendar.add( Calendar.DATE,day );
        String data = simpleDateFormat.format( calendar.getTime() );
        return data;
    }
    public static String getCustomDate2(int start,int end )
    {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat( "dd" );
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat( "dd" );


        calendar1.add( Calendar.DATE,start );
        calendar2.add( Calendar.DATE,end );

        String data = simpleDateFormat1.format( calendar1.getTime() )
                +"-"+simpleDateFormat2.format( calendar2.getTime() );
        return data;
    }
    public static String getTodayDate()
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "dd" );
        String data = simpleDateFormat.format( calendar.getTime() );
        return data;

    }
    public static String getCurrentMonth()
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "MMMM" );
        String data = simpleDateFormat.format( calendar.getTime() );
        return data;

    }
}
