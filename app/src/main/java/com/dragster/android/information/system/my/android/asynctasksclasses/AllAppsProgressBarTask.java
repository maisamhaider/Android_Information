package com.dragster.android.information.system.my.android.asynctasksclasses;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dragster.android.information.system.my.android.R;
import com.dragster.android.information.system.my.android.adapters.AllAppsAdapter;
import com.dragster.android.information.system.my.android.models.AllAppsModel;
import com.dragster.android.information.system.my.android.utilities.ContextBaseGetFunctionsFromHere;


import java.util.ArrayList;
import java.util.List;

import pl.tajchert.waitingdots.DotsTextView;


public class AllAppsProgressBarTask extends AsyncTask<Void, Integer, String> {

    Context context;
    private AlertDialog.Builder progressAlertDialog;
    private AllAppsAdapter allAppsAdapter;
    private AlertDialog dialog;
    private View view;
    private DotsTextView dotsTextView;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private List<AllAppsModel> list;
    ContextBaseGetFunctionsFromHere contextBaseGetFunctionsFromHere;

    public AllAppsProgressBarTask(Context context, AllAppsAdapter allAppsAdapter,
                                  RecyclerView recyclerView, LinearLayoutManager linearLayoutManager) {
        this.context = context;
        this.allAppsAdapter = allAppsAdapter;
        this.recyclerView = recyclerView;
        this.linearLayoutManager = linearLayoutManager;
        this.list = new ArrayList<>();
        contextBaseGetFunctionsFromHere = new ContextBaseGetFunctionsFromHere( context );


    }

    @Override
    protected void onPreExecute() {
        try {
            progressAlertDialog = new AlertDialog.Builder( context );
            View view = LayoutInflater.from( context ).inflate( R.layout.progress_dialog, null );
            progressAlertDialog.setView( view );
            dotsTextView = view.findViewById( R.id.dots );
            dialog = progressAlertDialog.create();
            dialog.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
            dialog.show();
            dotsTextView.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected String doInBackground(Void... voids) {
        for (int i = 0; i < contextBaseGetFunctionsFromHere.GetAllInstalledApkInfo().size(); i++) {
            String pName = contextBaseGetFunctionsFromHere.GetAllInstalledApkInfo().get( i );
            String appName = contextBaseGetFunctionsFromHere.GetAppName( pName );
            Drawable appIcon = contextBaseGetFunctionsFromHere.getAppIconByPackageName( pName );
            long appInstalledTime = contextBaseGetFunctionsFromHere.getAppInstallTime( pName );

            list.add( new AllAppsModel( pName, appName, appIcon, appInstalledTime ) );
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        recyclerView.setLayoutManager( linearLayoutManager );
        allAppsAdapter.setList( list );
        recyclerView.setAdapter( allAppsAdapter );
        allAppsAdapter.notifyDataSetChanged();
        if (dialog.isShowing()) {
            dotsTextView.stop();
            dialog.dismiss();
        }

    }


}
