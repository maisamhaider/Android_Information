package com.dragster.android.information.system.my.android.fragments;

import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
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
import com.dragster.android.information.system.my.android.adapters.AllAppsAdapter;
import com.dragster.android.information.system.my.android.asynctasksclasses.AllAppsProgressBarTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AllApplications#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllApplications extends Fragment {
    private static final String TAG = "All";
    private PageViewModel pageViewModel;
    private AllAppsProgressBarTask progressBarTaskAsync;
    private RecyclerView allApps_Rv;
    private AllAppsAdapter allAppsAdapter;
    private EditText etSearch;

    public static AllApplications newInstance() {
        return new AllApplications();
    }
    public AllApplications() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        pageViewModel = new ViewModelProvider( this ).get( PageViewModel.class );
        pageViewModel.setIndex(TAG);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_all_application, container, false );
        PackageManager pm = getPackageManager();
        etSearch = ((MyPhoneApps)getActivity()).myPhoneAppsSearch_Et;

        allApps_Rv = view.findViewById( R.id.allApps_Rv );
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager( getContext() );
        linearLayoutManager.setOrientation( RecyclerView.VERTICAL );
        allApps_Rv.setLayoutManager( linearLayoutManager );

        allAppsAdapter = new AllAppsAdapter( getActivity() );
        progressBarTaskAsync = new AllAppsProgressBarTask(getActivity(),allAppsAdapter,allApps_Rv,linearLayoutManager);
        progressBarTaskAsync.execute(  );

        etSearch.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                allAppsAdapter.getFilter().filter( s );
            }
        } );
        return view;
        }


    private PackageManager getPackageManager() {
        return getActivity().getPackageManager();
    }
}
