package com.dragster.android.information.system.my.android.classes;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Permissions {


    private static final int REQ = 1111;
    private  Context context;
    public Permissions( Context context) {
        this.context = context;
    }

    public  boolean permission()
    {
        int phoneStatePer = ContextCompat.checkSelfPermission( context, Manifest.permission.READ_PHONE_STATE );
        int storageReadPermission = ContextCompat.checkSelfPermission( context,Manifest.permission.READ_EXTERNAL_STORAGE );
        int storageWritePermission = ContextCompat.checkSelfPermission( context,Manifest.permission.WRITE_EXTERNAL_STORAGE );
        int cameraPermission = ContextCompat.checkSelfPermission( context,Manifest.permission.CAMERA );
        int networkState = ContextCompat.checkSelfPermission( context,Manifest.permission.ACCESS_NETWORK_STATE );
        int accessWifiState = ContextCompat.checkSelfPermission( context,Manifest.permission.ACCESS_WIFI_STATE );
        int killBackgroundPressPer= ContextCompat.checkSelfPermission( context,Manifest.permission.KILL_BACKGROUND_PROCESSES );

        if (phoneStatePer == PackageManager.PERMISSION_GRANTED
                && storageReadPermission==PackageManager.PERMISSION_GRANTED
                && storageWritePermission== PackageManager.PERMISSION_GRANTED
                && cameraPermission == PackageManager.PERMISSION_GRANTED
                && networkState == PackageManager.PERMISSION_GRANTED
                && accessWifiState == PackageManager.PERMISSION_GRANTED
                && killBackgroundPressPer == PackageManager.PERMISSION_GRANTED)
        {
            return true;
        }
        else
            ActivityCompat.requestPermissions( (Activity) context,new String[]{Manifest.permission.READ_PHONE_STATE
                    ,Manifest.permission.READ_EXTERNAL_STORAGE
                    ,Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ,Manifest.permission.CAMERA
                    ,Manifest.permission.ACCESS_NETWORK_STATE
                    ,Manifest.permission.ACCESS_WIFI_STATE
                    ,Manifest.permission.KILL_BACKGROUND_PROCESSES},REQ );

        return false;
    }
}
