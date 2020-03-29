package com.dragster.android.information.system.my.android.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.dragster.android.information.system.my.android.R;
import com.dragster.android.information.system.my.android.utilities.OnSwipeTouchListener;

public class Screenshot extends AppCompatActivity {

    private ImageView imageView, screenShotLeft_iv, screenShotRight_iv;
    int current_image_index;
    int C_Name  = 1;
    int[] image = {R.drawable.ic_screenshot1, R.drawable.ic_screenshot2, R.drawable.ic_screenshot3};

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_screenshot );
        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );
        imageView = findViewById( R.id.screenShotMain_iv );
        screenShotLeft_iv = findViewById( R.id.screenShotLeft_iv );
        screenShotRight_iv = findViewById( R.id.screenShotRight_iv );


        screenShotRight_iv.setOnClickListener( v -> {
            current_image_index++;
            current_image_index = current_image_index % image.length;
            imageView.setImageResource( image[current_image_index] );

        } );


        screenShotLeft_iv.setOnClickListener( v -> {

            if (current_image_index == 0) {
                imageView.setImageResource( image[2] );
                current_image_index = 2;
            }
            if (current_image_index == 1) {
                imageView.setImageResource( image[0] );
                current_image_index = 0;
            }
            if (current_image_index == 2) {
                imageView.setImageResource( image[1] );
                current_image_index = 1;
            }


        } );
        imageView.setOnTouchListener( new OnSwipeTouchListener( this ) {
            public void onSwipeTop() {
            }

            public void onSwipeRight() {
                current_image_index++;
                current_image_index = current_image_index % image.length;
                imageView.setImageResource( image[current_image_index] );
            }

            public void onSwipeLeft() {
                current_image_index--;
                current_image_index = current_image_index % image.length;
                imageView.setImageResource( image[current_image_index] );
            }

            public void onSwipeBottom() {
            }

        } );


    }

}
