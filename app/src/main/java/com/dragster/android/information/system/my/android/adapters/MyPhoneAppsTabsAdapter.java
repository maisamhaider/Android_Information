package com.dragster.android.information.system.my.android.adapters;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.dragster.android.information.system.my.android.R;
import com.dragster.android.information.system.my.android.fragments.AllApplications;
import com.dragster.android.information.system.my.android.fragments.LessUsedApps;
import com.dragster.android.information.system.my.android.fragments.RecentlyUsedApps_Frag;

public class MyPhoneAppsTabsAdapter extends FragmentPagerAdapter {
    @StringRes
    private static final int[] TAB_TITLES = new int[] { R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3 };
    private final Context mContext;
    public MyPhoneAppsTabsAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return AllApplications.newInstance();
            case 1:
                return RecentlyUsedApps_Frag.newInstance();
            case 2:
            LessUsedApps lessUsedApps = new LessUsedApps();
            return lessUsedApps;
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
