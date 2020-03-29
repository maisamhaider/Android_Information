package com.dragster.android.information.system.my.android.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.dragster.android.information.system.my.android.R;
import com.dragster.android.information.system.my.android.adapters.DataUsageGraphsTabsAdapter;
import com.google.android.material.tabs.TabLayout;

public class DataUsageGraphsActivity extends AppCompatActivity {

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_data_usage_graphs );
        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        DataUsageGraphsTabsAdapter adapter = new DataUsageGraphsTabsAdapter( this.getSupportFragmentManager(), this );
        ViewPager viewPager = findViewById( R.id.DUGF_Vp );
        TabLayout tabLayout = findViewById( R.id.DUGFTabs_TL );
        LinearLayout dataUsageGraphsBack_LL = findViewById( R.id.dataUsageGraphsBack_LL );
        viewPager.setAdapter( adapter );
        tabLayout.setupWithViewPager( viewPager );
        tabLayout.setTabTextColors( ColorStateList.valueOf( Color.parseColor( "#FFFFFF" ) ) );
        tabLayout.setSelectedTabIndicatorColor( Color.parseColor( "#BEBEBE" ) );

        Intent intent = getIntent();
        boolean isTodayMobile = intent.getBooleanExtra( "TODAY_MOBILE", false );
        boolean isLastWeekMobile = intent.getBooleanExtra( "LAST_WEEK_MOBILE", false );
        boolean isThisMonthMobile = intent.getBooleanExtra( "THIS_MONTH_MOBILE", false );

        boolean isTodayWifi = intent.getBooleanExtra( "TODAY_WIFI", false );
        boolean isLastWeekWifi = intent.getBooleanExtra( "LAST_WEEK_WIFI", false );
        boolean isThisMonthWifi = intent.getBooleanExtra( "THIS_MONTH_WIFI", false );

        String  todayMobileData  = intent.getStringExtra( "TODAY_MOBILE_DATA");
        String  lastWeekMobileData  = intent.getStringExtra( "LAST_WEEK_MOBILE_DATA");
        String  thisMonthMobileData  = intent.getStringExtra( "THIS_MONTH_MOBILE" );

        String  todayWifiData  = intent.getStringExtra( "TODAY_WIFI_DATA");
        String  lastWeekWifiData  = intent.getStringExtra( "LAST_WEEK_DATA" );
        String  thisMonthWifiData  = intent.getStringExtra( "THIS_MONTH_WIFI_DATA" );

        dataUsageGraphsBack_LL.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        } );
        if (isTodayMobile)
        {
            viewPager.setCurrentItem( 0 );
            adapter.setData( todayMobileData );
         }
        else
            if (isLastWeekMobile)
            {
                viewPager.setCurrentItem( 1 );
                adapter.setData( lastWeekMobileData );

            }
            else
                if (isThisMonthMobile)
            {
                viewPager.setCurrentItem( 2 );
                adapter.setData( thisMonthMobileData );

            }
                else
                if (isTodayWifi)
                {
                    viewPager.setCurrentItem( 0);
                    adapter.setData( todayWifiData );

                }

                else
                if (isLastWeekWifi)
                {
                    viewPager.setCurrentItem( 1 );
                    adapter.setData( lastWeekWifiData );
                    adapter.setWhichViewIsClicked("wifi");
                }

                else
                if (isThisMonthWifi)
                {
                    viewPager.setCurrentItem( 2 );
                    adapter.setData( thisMonthWifiData );

                }



    }
}
