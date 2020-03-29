package com.dragster.android.information.system.my.android.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.dragster.android.information.system.my.android.utilities.DrawingClass;

public class DrawingActivty extends AppCompatActivity {
    DrawingClass drawingClass ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        drawingClass = new DrawingClass( this,null );
        setContentView( drawingClass );

    }
}
