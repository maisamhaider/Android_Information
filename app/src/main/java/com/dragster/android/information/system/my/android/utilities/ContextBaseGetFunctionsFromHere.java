package com.dragster.android.information.system.my.android.utilities;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.telephony.TelephonyManager;

import androidx.core.content.ContextCompat;

import com.dragster.android.information.system.my.android.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ContextBaseGetFunctionsFromHere extends ContextWrapper {

    private int UID;
    private long total;
    private PackageManager mPackageManager;


    public ContextBaseGetFunctionsFromHere(Context base) {
        super( base );
    }

    public List<String> GetAllInstalledApkInfo() {

        List<String> ApkPackageName = new ArrayList<>();

        Intent intent = new Intent( Intent.ACTION_MAIN, null );

        intent.addCategory( Intent.CATEGORY_LAUNCHER );

        intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED );

        List<ResolveInfo> resolveInfoList = this.getPackageManager().queryIntentActivities( intent, 0 );

        for (ResolveInfo resolveInfo : resolveInfoList) {

            ActivityInfo activityInfo = resolveInfo.activityInfo;

            if (isSystemPackage( resolveInfo ) || !isSystemPackage( resolveInfo )) {
                if (!ApkPackageName.contains( activityInfo.applicationInfo.packageName )) {
                    ApkPackageName.add( activityInfo.applicationInfo.packageName );
                }
            }
        }

        return ApkPackageName;

    }
    public long getAppInstallTime(String apkPackageName)
    {
        long timestamp = 0 ;
        PackageManager pm = this.getPackageManager();

        try {
            PackageInfo info = pm.getPackageInfo(apkPackageName, 0);
            Field field = PackageInfo.class.getField("firstInstallTime");
          timestamp = field.getLong(info);
        } catch (PackageManager.NameNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (SecurityException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IllegalArgumentException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (NoSuchFieldException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IllegalAccessException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        return timestamp;

    }



    public boolean isSystemPackage(ResolveInfo resolveInfo) {

        return ((resolveInfo.activityInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
    }

    public Drawable getAppIconByPackageName(String ApkTempPackageName) {

        Drawable drawable;

        try {
            drawable = this.getPackageManager().getApplicationIcon( ApkTempPackageName );

        } catch (PackageManager.NameNotFoundException e) {

            e.printStackTrace();

            drawable = ContextCompat.getDrawable( this, R.mipmap.ic_launcher );
        }
        return drawable;
    }

    public String GetAppName(String ApkPackageName) {

        String Name = "";

        ApplicationInfo applicationInfo;

        PackageManager packageManager = this.getPackageManager();

        try {

            applicationInfo = packageManager.getApplicationInfo( ApkPackageName, 0 );

            if (applicationInfo != null) {

                Name = (String) packageManager.getApplicationLabel( applicationInfo );
            }

        } catch (PackageManager.NameNotFoundException e) {

            e.printStackTrace();
        }
        return Name;
    }

    public boolean isSomeIssueWithSimCard()
    {
        boolean isIssue = false;
        TelephonyManager telMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        int simState = telMgr.getSimState();
        switch (simState) {
            case TelephonyManager.SIM_STATE_ABSENT:
                // do something
                isIssue = true;
                break;

            case TelephonyManager.SIM_STATE_READY:
                // do something
                isIssue = false;
                break;
            case TelephonyManager.SIM_STATE_UNKNOWN:
                // do something
                isIssue = true;
                break;

        }
        return isIssue;
    }






}
