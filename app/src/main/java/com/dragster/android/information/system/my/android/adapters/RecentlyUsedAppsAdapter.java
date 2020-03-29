package com.dragster.android.information.system.my.android.adapters;

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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.dragster.android.information.system.my.android.R;
import com.dragster.android.information.system.my.android.utilities.AppUtility;
import com.dragster.android.information.system.my.android.utilities.TimeSettingsClass;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import bot.box.appusage.model.AppData;
import bot.box.appusage.utils.UsageUtils;

public class RecentlyUsedAppsAdapter extends RecyclerView.Adapter<RecentlyUsedAppsAdapter.RecentlyUsedAppViewHolder> implements Filterable {
    private Context context;
    private List<AppData> appDataList;
    private List<AppData> fullList;
    private AppUtility appUtility;

    public RecentlyUsedAppsAdapter(Context context, List<AppData> appDataList) {
        this.context = context;
        this.appDataList = appDataList;
        fullList = new ArrayList<>(  );
        fullList.addAll( appDataList );
        appUtility = new AppUtility( context );

    }


    @NonNull
    @Override
    public RecentlyUsedAppViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.recently_used_apps_items_layout, parent, false );
        return new RecentlyUsedAppViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull RecentlyUsedAppViewHolder holder, int position) {
        String appName = appDataList.get( position ).mName;
        String appPackageName = appDataList.get( position ).mPackageName;
        String appLastTimeLaunched = TimeSettingsClass.getHoursFromMilliSecs( appDataList.get( position ).mUsageTime );

        // Glide used here for loading application icon 😍😍
        Glide.with( this.context )
                .load( UsageUtils.parsePackageIcon( appPackageName, R.mipmap.ic_launcher ) ).
                transition( new DrawableTransitionOptions().crossFade() )
                .into( holder.recentAppIcon_Iv );

        holder.recentAppName_Tv.setText( appName );
        holder.recentAppUsageTime_Tv.setText( appLastTimeLaunched );

        // intent to clicked app 😎😎
        holder.recentlyUsageAppsAdapterMain_LL.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PackageManager pm = context.getPackageManager();

                Intent intent = pm.getLaunchIntentForPackage( appPackageName );
                if (intent!=null)
                {
                    context.startActivity( intent );
                }

            }
        } );
        holder.recentAppDelete_Iv.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String packageName = appDataList.get( position ).mPackageName;
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
        } );

    }

    @Override
    public int getItemCount() {
        return appDataList.size();
    }



    class RecentlyUsedAppViewHolder extends RecyclerView.ViewHolder {
        TextView recentAppName_Tv, recentAppUsageTime_Tv;
        ImageView recentAppDelete_Iv, recentAppIcon_Iv;
        LinearLayout recentlyUsageAppsAdapterMain_LL;
        public RecentlyUsedAppViewHolder(@NonNull View itemView) {
            super( itemView );
            recentAppName_Tv = itemView.findViewById( R.id.recentAppName_Tv );
            recentAppUsageTime_Tv = itemView.findViewById( R.id.recentAppUsageTime_Tv );
            recentAppIcon_Iv = itemView.findViewById( R.id.recentAppIcon_Iv );
            recentAppDelete_Iv= itemView.findViewById( R.id.recentAppdeselect_Iv );
            recentlyUsageAppsAdapterMain_LL = itemView.findViewById( R.id.recentlyUsageAppsAdapterMain_LL );
        }
    }
    @Override
    public Filter getFilter() {
        return myFilter;
    }
    private Filter myFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<AppData>filteredList = new ArrayList<>(  );
            if (constraint == null || constraint.length() ==0)
            {
                filteredList.addAll( fullList );
            }
            else {
                String string = constraint.toString().toLowerCase();
                for (AppData data : fullList)
                {
                    if (data.mName.toLowerCase().startsWith( string ))
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
            appDataList.clear();
            appDataList.addAll( (Collection<? extends AppData>) results.values );
            notifyDataSetChanged();
        }
    };

}
