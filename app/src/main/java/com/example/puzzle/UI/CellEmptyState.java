package com.example.puzzle.UI;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class CellEmptyState extends CellState {

    CellEmptyState(Context context)
    {
        super(context);
    }
    public void draw(Canvas canvas, int x, int y, int width, int height){

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        Rect rect = new Rect(x * width-25, y * height-25, ((x + 1) * width - 1), ((y + 1) * height - 1));
        canvas.drawRect(rect, paint);
    }
}
