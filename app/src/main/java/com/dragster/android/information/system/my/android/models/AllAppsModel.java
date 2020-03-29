package com.dragster.android.information.system.my.android.models;

import android.content.pm.ApplicationInfo;
import android.graphics.drawable.Drawable;

import java.util.List;

public class AllAppsModel {
    long getAppInstalledTime;
    String packageName;
    String appName;
    Drawable image;

    public AllAppsModel(String packageName, String appName, Drawable image, long getAppInstalledTime) {
        this.packageName = packageName;
        this.appName = appName;
        this.image = image;
        this.getAppInstalledTime = getAppInstalledTime;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getAppName() {
        return appName;
    }

    public Drawable getImage() {
        return image;
    }

    public long getGetAppInstalledTime() {
        return getAppInstalledTime;
    }
}
