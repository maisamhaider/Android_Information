package com.dragster.android.information.system.my.android.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dragster.android.information.system.my.android.R;
import com.dragster.android.information.system.my.android.classes.Permissions;
import com.dragster.android.information.system.my.android.utilities.GetSomething;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.Objects;

public class CacheAndEmptyFolders extends AppCompatActivity {
    long cacheSize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_cache_and_empty_folders );
        Permissions permissions = new Permissions( getApplicationContext() );


//        TextView cacheSize_tv,emptyFoldersAmount_Tv;
        LinearLayout clareCache_LL,deleteEmptyFolders_LL;

//        cacheSize_tv = findViewById( R.id.cacheSize_tv );
//        emptyFoldersAmount_Tv = findViewById( R.id.emptyFoldersAmount_Tv );

        clareCache_LL = findViewById( R.id.clareCache_LL );
        deleteEmptyFolders_LL = findViewById( R.id.deleteEmptyFolders_LL );

        permissions.permission();

        MainActivity mainActivity = new MainActivity();
        GetSomething getSomething  = new GetSomething();
       AdView mAdView = findViewById( R.id.cacheAndEmptyFolders_adv );
        MobileAds.initialize( this,getResources().getString( R.string.banner ) );
        if (mainActivity.isPaid)
        {
            mAdView.setVisibility( View.GONE );
        }
        else
        {
            getSomething.bannerAD( mAdView );
        }
         clareCache_LL.setOnClickListener( v -> {
            if (permissions.permission())
            {
               GetSomething.deleteCache( this);
                GetSomething.killBackgroundProcesses(getApplicationContext());
                 Toast.makeText( this, "device cache is cleared", Toast.LENGTH_SHORT ).show();
            }
        } );


        deleteEmptyFolders_LL.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String path = Environment.getExternalStorageDirectory().getAbsolutePath();
                AlertDialog.Builder builder= new AlertDialog.Builder(CacheAndEmptyFolders.this );
                View view = LayoutInflater.from( getApplicationContext() ).inflate( R.layout.empty_folder_delete_alert_dialog_layout,null );
                builder.setView( view );
                builder.setCancelable( true );
                AlertDialog dialog = builder.create();
                dialog.show();
                Objects.requireNonNull( dialog.getWindow() ).setBackgroundDrawable(new ColorDrawable( Color.TRANSPARENT ) );
                Button noEmptyFolder_Btn = view.findViewById( R.id.noEmptyFolder_Btn );
                Button yesEmptyFolder_Btn = view.findViewById( R.id.yesEmptyFolder_Btn );
                yesEmptyFolder_Btn.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GetSomething.deleteFolder(path);
                        Toast.makeText( CacheAndEmptyFolders.this, "All empty folders are deleted", Toast.LENGTH_SHORT ).show();
                        dialog.dismiss();
                    }
                } );
                noEmptyFolder_Btn.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                } );

            }
        } );

    }

}
