package com.dragster.android.information.system.my.android.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dragster.android.information.system.my.android.R;

public class ScreenDensity extends AppCompatActivity {
    private LinearLayout densityFirst_LL,densitySecond_LL,densityThird_LL,densityFourth_LL,densityFifth_LL,densitySixth_LL;
    private TextView lowPixelDetail_Tv,mediumPixelDetail_Tv,highPixelDetail_Tv,ExtraPixelDetail_Tv,extraHighPixelDetail_Tv,tooExtraPixelDetail_Tv;
    private float densityValue = 0;
    private int dpiValue = 0;
    private DisplayMetrics displayMetrics;
    private StringBuilder stringBuilder;
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_screen_density );
        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        displayMetrics = new DisplayMetrics();
        stringBuilder = new StringBuilder(  );

        this.getWindowManager().getDefaultDisplay().getMetrics( displayMetrics );
        densityValue = getResources().getDisplayMetrics().density;
        dpiValue = getResources().getDisplayMetrics().densityDpi;
        stringBuilder.append( dpiValue ).append( " Pixel Per Inch " );



        densityFirst_LL = findViewById( R.id. densityFirst_LL);
        densitySecond_LL = findViewById( R.id.densitySecond_LL );
        densityThird_LL  = findViewById( R.id.densityThird_LL );
        densityFourth_LL  = findViewById( R.id.densityFourth_LL );
        densityFifth_LL  = findViewById( R.id.densityFifth_LL );
        densitySixth_LL  = findViewById( R.id.densitySixth_LL );

        lowPixelDetail_Tv  = findViewById( R.id.lowPixelDetail_Tv) ;
        mediumPixelDetail_Tv  = findViewById( R.id.mediumPixelDetail_Tv );
        highPixelDetail_Tv  = findViewById( R.id.highPixelDetail_Tv );
        ExtraPixelDetail_Tv  = findViewById( R.id.ExtraPixelDetail_Tv );
        extraHighPixelDetail_Tv  = findViewById( R.id. extraHighPixelDetail_Tv);
        tooExtraPixelDetail_Tv  = findViewById( R.id.tooExtraPixelDetail_Tv );


        if (densityValue == 0.75)
        {
             lowPixelDetail_Tv.setText( stringBuilder.toString() );
        }
        else if (densityValue == 1.0)
        {
             mediumPixelDetail_Tv.setText( stringBuilder.toString() );
        }
        else if (densityValue == 1.5)
        {
             highPixelDetail_Tv.setText( stringBuilder.toString() );
        }
        else if (densityValue == 2.0)
        {
             ExtraPixelDetail_Tv.setText( stringBuilder.toString() );
        }
        else if ( densityValue == 3.0)
        {
             extraHighPixelDetail_Tv.setText( stringBuilder.toString() );
        }
        else if (densityValue == 4.0)
        {
             tooExtraPixelDetail_Tv.setText( stringBuilder.toString() );
        }

    }
}
