package com.dragster.android.information.system.my.android.utilities;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class ApplicationUtils extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled( true );
    }
}
