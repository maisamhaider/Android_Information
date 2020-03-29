package com.dragster.android.information.system.my.android.adapters;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.dragster.android.information.system.my.android.R;
import com.dragster.android.information.system.my.android.fragments.MonthlyDataUsageGraphs_Frag;
import com.dragster.android.information.system.my.android.fragments.TodayDataUsageGraphs_Frag;
import com.dragster.android.information.system.my.android.fragments.WeeklyDataUsageGraphs_Frag;
import com.google.android.gms.ads.InterstitialAd;

public class DataUsageGraphsTabsAdapter extends FragmentPagerAdapter {
    private static final int[] Tab_Table = new int[]{R.string.tab_text_4,R.string.tab_text_5,R.string.tab_text_6};
    private Context context;
    private String data,whichViewIsClicked;

    public DataUsageGraphsTabsAdapter(@NonNull FragmentManager fm, Context context) {
        super( fm );
        this.context = context;

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                TodayDataUsageGraphs_Frag frag1 = new TodayDataUsageGraphs_Frag();
                Bundle bundle1= new Bundle(  );
                bundle1.putString( "data1", data);
                 frag1.setArguments( bundle1 );
                return frag1;
            case 1:
                WeeklyDataUsageGraphs_Frag frag2 = new WeeklyDataUsageGraphs_Frag();
                Bundle bundle2= new Bundle(  );
                bundle2.putString( "data2", data);
                 frag2.setArguments( bundle2 );
                return frag2;
            case 2:
                MonthlyDataUsageGraphs_Frag frag3 = new MonthlyDataUsageGraphs_Frag();
                Bundle bundle3= new Bundle(  );
                bundle3.putString( "data3", data);
                 frag3.setArguments( bundle3 );
                return frag3;
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return context.getResources().getString(Tab_Table[position] );
    }

    @Override
    public int getCount() {
        return 3;
    }


    public void setData(String data) {
        this.data = data;
    }

    public void setWhichViewIsClicked(String whichViewIsClicked) {
        this.whichViewIsClicked = whichViewIsClicked;
    }
}
