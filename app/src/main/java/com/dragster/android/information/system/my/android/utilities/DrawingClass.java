package com.dragster.android.information.system.my.android.utilities;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class DrawingClass extends View {
    Paint paint;
    Path path;

    public DrawingClass(Context context, @Nullable AttributeSet attrs) {
        super( context, attrs );
        paint = new Paint();
        path = new Path();
        paint.setAntiAlias( true );
        paint.setColor( Color.parseColor( "#000000"  ));
        paint.setStrokeJoin( Paint.Join.ROUND );
        paint.setStyle( Paint.Style.STROKE );
        paint.setStrokeWidth( 5f );
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw( canvas );
        canvas.drawPath( path,paint );
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float xPos = event.getX();
        float yPos = event.getY();
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                path.moveTo( xPos,yPos );
                break;
            case MotionEvent.ACTION_UP:
                path.lineTo( xPos,yPos );
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            default:
                return false;

        }
        invalidate();
        return true;
    }
}
