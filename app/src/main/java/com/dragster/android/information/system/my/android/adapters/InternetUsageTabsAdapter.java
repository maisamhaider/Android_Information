package com.dragster.android.information.system.my.android.adapters;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.dragster.android.information.system.my.android.R;
import com.dragster.android.information.system.my.android.fragments.ThisMonthInternetUsage_Frag;
import com.dragster.android.information.system.my.android.fragments.TodayInternetUsage_Frag;
import com.dragster.android.information.system.my.android.fragments.LastWeeklyInternetUsage_Frag;

public class InternetUsageTabsAdapter extends FragmentPagerAdapter {
    @StringRes
    private static final int[] TAB_TITLES = new int[] { R.string.tab_text_4, R.string.tab_text_5, R.string.tab_text_6 };
    private final Context mContext;

    public InternetUsageTabsAdapter(Context context, FragmentManager fm ) {
        super(fm);
        mContext = context;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:

                return TodayInternetUsage_Frag.newInstance();
            case 1:
                return LastWeeklyInternetUsage_Frag.newInstance();
            case 2:
            return ThisMonthInternetUsage_Frag.newInstance();
            default:
                return null;
        }
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }
    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }

}
