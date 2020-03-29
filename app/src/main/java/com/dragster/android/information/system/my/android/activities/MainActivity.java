package com.dragster.android.information.system.my.android.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.dragster.android.information.system.my.android.classes.Permissions;
import com.dragster.android.information.system.my.android.R;
import com.dragster.android.information.system.my.android.fragments.MainFragment;
import com.dragster.android.information.system.my.android.pushnotification.ExtraUtils;

public class MainActivity extends AppCompatActivity{

    private static final int REQUEST_PERMISSION = 1111;
    Fragment mFragment;
    public boolean isPaid = false;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        SharedPreferences sharedPreferences = getSharedPreferences( "MY_PREFERENCES", Context.MODE_PRIVATE );
        isPaid = sharedPreferences.getBoolean( "is_purchase",false );

        ExtraUtils.addToServer(this);
        ExtraUtils.checkIfNotificationRequired(this);

        Permissions permissions = new Permissions( this );
        permissions.permission();

        MainFragment mainFragment = new MainFragment();
        loadFragment( mainFragment );

    }

    public void loadFragment(Fragment fragment) {
        this.mFragment = fragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace( R.id.fragmentsContainer,mFragment );
        fragmentTransaction.commit();

    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            MainActivity.this.finish();
            super.onBackPressed();

        }
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            } else {
                Toast.makeText( this, "Permission is required for working application properly ",
                        Toast.LENGTH_SHORT ).show();
                Log.i( "Calendar Permission", "Not Granted" );
            }

        }
        super.onRequestPermissionsResult( requestCode, permissions, grantResults );
    }


}
