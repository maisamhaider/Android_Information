package com.dragster.android.information.system.my.android.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.dragster.android.information.system.my.android.R;
import com.dragster.android.information.system.my.android.adapters.MyPhoneAppsTabsAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class MyPhoneApps extends AppCompatActivity {
    private  boolean isAllApps,isRecentApps,isLessUsedApps;
    public EditText myPhoneAppsSearch_Et;

    @SuppressLint({"ClickableViewAccessibility", "SourceLockedOrientationActivity"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_my_phone_apps );
        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        MyPhoneAppsTabsAdapter tabsPagerAdapter = new MyPhoneAppsTabsAdapter(this, this.getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.apps_Vp);
        viewPager.setAdapter(tabsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        tabs.setTabTextColors( ColorStateList.valueOf( Color.parseColor( "#FFFFFF" ) ) );
        tabs.setSelectedTabIndicatorColor( Color.parseColor( "#093343" ) );
        ImageView myPhoneAppsCancel_Iv = findViewById( R.id.myPhoneAppsCancel_Iv );
        myPhoneAppsSearch_Et = findViewById( R.id.myPhoneAppsSearch_Et );
        myPhoneAppsSearch_Et.setOnTouchListener( (v, event) -> {

           if (event.getAction() == MotionEvent.ACTION_DOWN) {

               myPhoneAppsSearch_Et.post( () -> {
                   myPhoneAppsSearch_Et.requestFocus();
                   InputMethodManager inputMethodManager = (InputMethodManager) Objects.requireNonNull( getApplicationContext()).getSystemService( Context.INPUT_METHOD_SERVICE );
                   inputMethodManager.showSoftInput( myPhoneAppsSearch_Et, InputMethodManager.SHOW_FORCED );
                   myPhoneAppsSearch_Et.setImeOptions( EditorInfo.IME_ACTION_SEARCH );
                   myPhoneAppsSearch_Et.setSingleLine();

               } );
           }
           return true;
       } );

        myPhoneAppsCancel_Iv.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myPhoneAppsSearch_Et.setText( "" );
            }
        } );
    }

    @Override
    protected void onPause() {
        super.onPause();
        InputMethodManager inputMethodManager = (InputMethodManager) Objects.requireNonNull( getApplicationContext() ).getSystemService( Context.INPUT_METHOD_SERVICE );
        inputMethodManager.hideSoftInputFromWindow( myPhoneAppsSearch_Et.getWindowToken(), 0 );
    }
}
