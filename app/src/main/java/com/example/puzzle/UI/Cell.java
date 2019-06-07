package com.example.puzzle.UI;

import android.content.Context;
import android.graphics.Canvas;

public class Cell {

        private int x;
        private int y;
        private int width;
        private int height;
        private boolean empty;
        private CellState state;
        private Context context;

        Cell(int x, int y, Boolean isEmpty, Context context) {
            this.context = context;
            this.x = x;
            this.y = y;
            this.empty = isEmpty;

            state = new CellState(context);
            empty = true;
        }

        void draw(Canvas canvas) {
            state.draw(canvas, x, y, width, height);
        }

        void setDimensions(int width, int height) {

            this.height = height;
            this.width = width;

        }

        void changeState(int number) {

            if(number == 0)
            {
                state = new CellEmptyState(context);
                this.empty = true;
            }
            else {


                state = new CellFilledState(number, context);
                this.empty = false;
            }

        }

        int returnNumber()
        {
            if(!empty)
            {
                return ((CellFilledState) state).getNumber();
            }
            else
                return 0;
        }

        boolean checkEmpty()
        {

            return this.empty;
        }
}


