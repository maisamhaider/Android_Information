package com.dragster.android.information.system.my.android.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.dragster.android.information.system.my.android.R;
import com.dragster.android.information.system.my.android.activities.InternetSpeed;
import com.dragster.android.information.system.my.android.activities.MainActivity;
import com.dragster.android.information.system.my.android.models.AllAppsModel;
import com.dragster.android.information.system.my.android.utilities.AppUtility;
import com.dragster.android.information.system.my.android.utilities.TimeSettingsClass;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import bot.box.appusage.handler.Monitor;
import bot.box.appusage.utils.UsageUtils;

public class AllAppsAdapter extends RecyclerView.Adapter<AllAppsAdapter.AllAppsHolder> implements Filterable {
    private InterstitialAd interstitialAd;
    private List<AllAppsModel> apps;
    private Context context;
    private AppUtility appUtility;
    private List<AllAppsModel> fullList;
    private MainActivity mainActivity;


    @SuppressLint("NewApi")
    public AllAppsAdapter( Context context) {

        this.context = context;
        appUtility = new AppUtility( context );
        apps = new ArrayList<>(  );
        fullList = new ArrayList<>(  );


     }

     public void setList(List<AllAppsModel> apps){
        this.apps.clear();
         this.fullList.clear();
        this.apps = apps;
         this.fullList.addAll( apps );
     }

    @NonNull
    @Override
    public AllAppsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.allappsrecyclerviewitem_lo, parent, false );
        return new AllAppsHolder( view );
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(@NonNull AllAppsHolder holder, final int position) {
        interstitialAd = new InterstitialAd( context );
        interstitialAd.setAdUnitId( context.getResources().getString( R.string.interstitial) );
        reqNewInterstitial();
        mainActivity = new MainActivity();

        String appName = apps.get( position ).getAppName();
        String appPackage = apps.get( position ).getPackageName();
        String appInstalledTime = TimeSettingsClass.getFormattedDateFromMilliseconds( apps.get( position ).getGetAppInstalledTime());

        holder.appName_Tv.setText( appName );

        Glide.with(context).load( UsageUtils.parsePackageIcon(apps.get( position ).getPackageName(),R.mipmap.ic_launcher))
                .transition(new DrawableTransitionOptions().crossFade()).into(holder.appImage_Iv);

        holder.appInstalledTime_Tv.setText(appInstalledTime );
        if (Monitor.hasUsagePermission())


        holder.allAppsMain_Rl.setOnClickListener( v -> {
            PackageManager pm = context.getPackageManager();
            Intent intent = pm.getLaunchIntentForPackage( appPackage );
            if (intent != null) {
                context.startActivity( intent );
            }
        } );
        holder.deleteAppImage_Iv.setOnClickListener( v -> {
            String packageName = apps.get( position ).getPackageName();

            if (mainActivity.isPaid)
            {
                if (appUtility.isSystemApp( packageName )) {
                    Toast.makeText( context, "Can not Uninstall system's application", Toast.LENGTH_SHORT ).show();
                } else if (appUtility.isAppPreLoaded( packageName )) {

                } else {
                    Intent intent = new Intent( Intent.ACTION_DELETE );
                    intent.setData( Uri.parse( "package:" + packageName ) );
                    intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
                    context.startActivity( intent );
                }
            }
            else
            {
                if (interstitialAd.isLoaded())
                {
                    interstitialAd.show();
                }else
                {
                    if (appUtility.isSystemApp( packageName )) {
                        Toast.makeText( context, "Can not Uninstall system's application", Toast.LENGTH_SHORT ).show();
                    } else if (appUtility.isAppPreLoaded( packageName )) {

                    } else {
                        Intent intent = new Intent( Intent.ACTION_DELETE );
                        intent.setData( Uri.parse( "package:" + packageName ) );
                        intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
                        context.startActivity( intent );
                    }
                }
                interstitialAd.setAdListener( new AdListener() {
                    @Override
                    public void onAdClosed() {
                        if (appUtility.isSystemApp( packageName )) {
                            Toast.makeText( context, "Can not Uninstall system's application", Toast.LENGTH_SHORT ).show();
                        } else if (appUtility.isAppPreLoaded( packageName )) {

                        } else {
                            Intent intent = new Intent( Intent.ACTION_DELETE );
                            intent.setData( Uri.parse( "package:" + packageName ) );
                            intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
                            context.startActivity( intent );
                        }
                        reqNewInterstitial();
                    }
                } );

            }

        } );

    }
    private void reqNewInterstitial() {
        interstitialAd.loadAd(new AdRequest.Builder().build());
    }

    @Override
    public int getItemCount() {
        return apps.size();
    }



    class AllAppsHolder extends RecyclerView.ViewHolder {
        TextView appName_Tv, appInstalledTime_Tv;
        ImageView appImage_Iv, deleteAppImage_Iv;
        RelativeLayout allAppsMain_Rl;

        public AllAppsHolder(@NonNull View itemView) {
            super( itemView );
            appName_Tv = itemView.findViewById( R.id.allAppName_Tv );
            appInstalledTime_Tv = itemView.findViewById( R.id.allAppInstalledTime_Tv );
            appImage_Iv = itemView.findViewById( R.id.allAppImage_Iv );
            allAppsMain_Rl = itemView.findViewById( R.id.allAppsMain_Rl );
            deleteAppImage_Iv = itemView.findViewById( R.id.allAppSelected_deselect_Iv );

        }
    }
    @Override
    public Filter getFilter() {
        return myFilter;
    }
    private Filter myFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<AllAppsModel>filteredList = new ArrayList<>(  );
            if (constraint == null || constraint.length() ==0)
            {
                filteredList.addAll( fullList );
            }
            else {
                String string = constraint.toString().toLowerCase();
                for (AllAppsModel data : fullList)
                {
                    if (data.getAppName().toLowerCase().startsWith( string ))
                    {
                        filteredList.add( data );
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            apps.clear();
            apps.addAll( (Collection<? extends AllAppsModel>) results.values );
            notifyDataSetChanged();
        }
    };

}
