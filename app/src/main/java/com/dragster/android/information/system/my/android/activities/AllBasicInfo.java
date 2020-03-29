package com.dragster.android.information.system.my.android.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dragster.android.information.system.my.android.R;
import com.dragster.android.information.system.my.android.utilities.GetSomething;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.lang.reflect.Field;

public class AllBasicInfo extends AppCompatActivity {
    MainActivity mainActivity;
    TextView androidVersion_Tv, manufactureName_Tv, modelName_Tv, serialNo_Tv, idNo_Tv, brandName_Tv, typeName_Tv,
            userName_Tv, INCREMENTALNo_Tv, SDKName_Tv, BOARDNo_Tv, HOSTName_Tv, FINGERPRINTName_Tv;
    AdView mAdView;
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_all_basic_info );
        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        MainActivity mainActivity = new MainActivity();
        GetSomething getSomething  = new GetSomething();
        mAdView = findViewById( R.id.allBasicInfo_adv );
        MobileAds.initialize( this,getResources().getString( R.string.banner ) );
        if (mainActivity.isPaid)
        {
            mAdView.setVisibility( View.GONE );
        }
        else
            {
                getSomething.bannerAD( mAdView );
            }
        androidVersion_Tv = findViewById( R.id.androidVersion_Tv );
        manufactureName_Tv =  findViewById( R.id.manufactureName_Tv );
        modelName_Tv =  findViewById( R.id.modelName_Tv );
        serialNo_Tv = findViewById( R.id.serialNo_Tv );
        idNo_Tv =  findViewById( R.id.idNo_Tv );
        brandName_Tv = findViewById( R.id.brandName_Tv );
        typeName_Tv = findViewById( R.id.typeName_Tv );
        userName_Tv =  findViewById( R.id.userName_Tv );
        INCREMENTALNo_Tv =  findViewById( R.id.INCREMENTALNo_Tv );
        SDKName_Tv =  findViewById( R.id.SDKName_Tv );
        BOARDNo_Tv =  findViewById( R.id.BOARDNo_Tv );
        HOSTName_Tv =  findViewById( R.id.HOSTName_Tv );
        FINGERPRINTName_Tv =  findViewById( R.id.FINGERPRINTName_Tv );
        //info
        StringBuilder builder = new StringBuilder();
        builder.append( Build.VERSION.RELEASE );

        Field[] fields = Build.VERSION_CODES.class.getFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            int fieldValue = -1;

            try {
                fieldValue = field.getInt( new Object() );
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            if (fieldValue == Build.VERSION.SDK_INT) {
                builder.append(fieldName);
            }
        }
        manufactureName_Tv.setText( Build.MANUFACTURER );
        modelName_Tv.setText( Build.MODEL );
        androidVersion_Tv.setText( builder.toString());
        serialNo_Tv.setText( Build.SERIAL );
        idNo_Tv.setText( Build.ID );
        brandName_Tv.setText( Build.BRAND );
        typeName_Tv.setText( Build.TYPE );
        userName_Tv.setText( Build.USER );
        INCREMENTALNo_Tv.setText( Build.VERSION.INCREMENTAL );
        SDKName_Tv.setText( Build.VERSION.SDK );
        BOARDNo_Tv.setText( Build.BOARD );
        HOSTName_Tv.setText( Build.HOST );
        FINGERPRINTName_Tv.setText( Build.FINGERPRINT );


//        Log.i( "TAG", "SERIAL: " + Build.SERIAL );
//        Log.i( "TAG", "MODEL: " + Build.MODEL );
//        Log.i( "TAG", "ID: " + Build.ID );
//        Log.i( "TAG", "Manufacture: " + Build.MANUFACTURER );
//        Log.i( "TAG", "brand: " + Build.BRAND );
//        Log.i( "TAG", "type: " + Build.TYPE );
//        Log.i( "TAG", "user: " + Build.USER );
//        Log.i( "TAG", "BASE: " + Build.VERSION_CODES.BASE );
//        Log.i( "TAG", "INCREMENTAL " + Build.VERSION.INCREMENTAL );
//        Log.i( "TAG", "SDK  " + Build.VERSION.SDK );
//        Log.i( "TAG", "BOARD: " + Build.BOARD );
//        Log.i( "TAG", "BRAND " + Build.BRAND );
//        Log.i( "TAG", "HOST " + Build.HOST );
//        Log.i( "TAG", "FINGERPRINT: " + Build.FINGERPRINT );
//        Log.i( "TAG", "Version Code: " + Build.VERSION.RELEASE );
    }
}
