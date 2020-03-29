package com.dragster.android.information.system.my.android.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.dragster.android.information.system.my.android.asynctasksclasses.LessUsedAppsAsyncTask;
import com.dragster.android.information.system.my.android.models.AllAppsModel;
import com.dragster.android.information.system.my.android.utilities.ContextBaseGetFunctionsFromHere;
import com.dragster.android.information.system.my.android.R;
import com.dragster.android.information.system.my.android.activities.MyPhoneApps;
import com.dragster.android.information.system.my.android.adapters.LessUsedAppsAdapter;

import java.util.ArrayList;
import java.util.List;

import bot.box.appusage.contract.UsageContracts;
import bot.box.appusage.handler.Monitor;
import bot.box.appusage.model.AppData;
import bot.box.appusage.utils.Duration;

import static bot.box.appusage.datamanager.DataManager.init;


public class LessUsedApps extends Fragment implements UsageContracts.View {
    List<AppData> rUsedApps = new ArrayList<>(  );
    EditText etSearch;

    public static LessUsedApps newInstance()  {

        return new LessUsedApps();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view =  inflater.inflate( R.layout.fragment_less_used_apps, container, false );
        etSearch = ((MyPhoneApps)getActivity()).myPhoneAppsSearch_Et;

        List<AllAppsModel> allApps = new ArrayList<>(  );
        RecyclerView recyclerView = view.findViewById( R.id.lessUsedApps_rv );
        LinearLayoutManager layoutManager = new LinearLayoutManager( getActivity() );
        LessUsedAppsAdapter appsAdapter = new LessUsedAppsAdapter( getActivity() ) ;
        layoutManager.setOrientation( RecyclerView.VERTICAL );


        LessUsedAppsAsyncTask lessUsedAppsAsyncTask = new LessUsedAppsAsyncTask( getContext(),appsAdapter,rUsedApps,recyclerView,layoutManager );
        lessUsedAppsAsyncTask.execute(  );

        etSearch.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                appsAdapter.getFilter().filter( s );
            }
        } );
        recyclerView.setAdapter( appsAdapter );
        appsAdapter.notifyDataSetChanged();
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
        rUsedApps.addAll( usageData );
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }


}
