package com.example.puzzle.UI;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;

import com.example.puzzle.R;

public class CellState {

    protected Paint paint;
    protected Context context;

    CellState(Context context) {
        this.context = context;
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
    }

    public void draw(Canvas canvas, int x, int y, int width, int height){


        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(50);
        paint.setColor(Color.WHITE);
        Rect rect = new Rect(x*width,y*height,((x+1)*width - 1),((y+1)*height - 1));
        canvas.drawRect(rect, paint);

        paint.setStyle(Paint.Style.FILL);

        int color = ContextCompat.getColor(context, R.color.colorPrimaryDark);
        paint.setColor(color);
        rect = new Rect(x*width,y*height,((x+1)*width - 1),((y+1)*height - 1));
        canvas.drawRect(rect, paint);

    }
}
