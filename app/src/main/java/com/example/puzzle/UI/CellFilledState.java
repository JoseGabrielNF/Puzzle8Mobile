package com.example.puzzle.UI;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class CellFilledState extends CellState {

    private int number;

    CellFilledState(int number, Context context) {
        super(context);
        this.number = number;
    }

    public void draw(Canvas canvas, int x, int y, int width, int height){
        super.draw(canvas, x, y, width, height);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(2);
        paint.setTextSize(150);
        paint.setColor(Color.WHITE);
        canvas.drawText(String.valueOf(number), x*width + width/2-50, y*height + height/2+40, paint);
    }

    int getNumber()
    {

        return this.number;
    }
}
