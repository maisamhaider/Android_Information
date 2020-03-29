package com.dragster.android.information.system.my.android.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.dragster.android.information.system.my.android.models.PageViewModel;
import com.dragster.android.information.system.my.android.R;
import com.dragster.android.information.system.my.android.activities.MyPhoneApps;
import com.dragster.android.information.system.my.android.adapters.RecentlyUsedAppsAdapter;

import java.util.List;

import bot.box.appusage.contract.UsageContracts;
import bot.box.appusage.handler.Monitor;
import bot.box.appusage.model.AppData;
import bot.box.appusage.utils.Duration;

import static bot.box.appusage.datamanager.DataManager.init;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecentlyUsedApps_Frag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecentlyUsedApps_Frag extends Fragment implements UsageContracts.View {
    private static final String TAG = "All Apps";
    private PageViewModel pageViewModel;
    private RecyclerView recentlyUsedApps_Rv;
    private EditText etSearch;

    //    private List<AppData> appDataList;
    private RecentlyUsedAppsAdapter recentlyUsedAppsAdapter;

    public static RecentlyUsedApps_Frag newInstance() {
        return new RecentlyUsedApps_Frag();
    }
    public RecentlyUsedApps_Frag() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        pageViewModel = ViewModelProviders.of(this).get( PageViewModel.class);
        pageViewModel.setIndex(TAG);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view =  inflater.inflate( R.layout.fragment_recently_used_apps, container, false );
        etSearch = ((MyPhoneApps)getActivity()).myPhoneAppsSearch_Et;

        recentlyUsedApps_Rv = view.findViewById( R.id.recentlyUsedApps_Rv );

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

        if (Monitor.hasUsagePermission()) {
            Monitor.scan().getAppLists(this).fetchFor( Duration.TODAY);
            init();
        } else {
            Monitor.requestUsagePermission();
        }
    }

    @Override
    public void getUsageData(List<AppData> usageData, long mTotalUsage, int duration) {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager( getContext() );
        linearLayoutManager.setOrientation( RecyclerView.VERTICAL );
        recentlyUsedApps_Rv.setLayoutManager( linearLayoutManager );

            recentlyUsedAppsAdapter = new RecentlyUsedAppsAdapter( getContext(),usageData );
        etSearch.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                recentlyUsedAppsAdapter.getFilter().filter( s );
            }
        } );
        recentlyUsedApps_Rv.setAdapter( recentlyUsedAppsAdapter );
        recentlyUsedAppsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }
}
