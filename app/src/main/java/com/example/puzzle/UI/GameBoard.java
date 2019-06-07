package com.example.puzzle.UI;

import android.content.Context;
import android.graphics.Canvas;

import java.util.Arrays;
import java.util.Random;

public class GameBoard {

    private final int size = 3;
    private int width;
    private int height;
    private Context context;
    private Cell[] cells;
    private Canvas canvas;

    GameBoard(Context context) {
        this.context = context;
        cells = new Cell[getCompleteSize()];
        this.init();

    }

    private int getCompleteSize() {

        return size*size;
    }

    void setDimensions(int w, int h) {
        width = w;
        height = h;

        for (int i = 0; i < getCompleteSize(); i++) {
            cells[i].setDimensions(getCellWidth(), getCellHeight());

        }
    }

    private void init() {
        int count;
        Boolean[] checkArray = new Boolean[10];
        Arrays.fill(checkArray, false);

        Random random = new Random();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells[cellNum(i, j)] = new Cell(i, j, true, context);

            }
        }

        int [] board_template = new int[10];
        boolean isValid = false;

        while(!isValid) {
            Arrays.fill(checkArray, false);
            Arrays.fill(board_template, 0);
            count = 0;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    int cellNumber = random.nextInt(9);
                    if (!checkArray[cellNumber]) {
                        board_template[cellNum(i, j)] = cellNumber;
                        count++;
                        checkArray[cellNumber] = true;
                    } else if (count < 10)
                        j--;
                }
            }

            if(check_validity(board_template))
                isValid = true;
        }

        for(int i = 0; i <size; i++){
            for (int j = 0; j<size;j++)
            {
                cells[cellNum(i,j)].changeState(board_template[cellNum(i, j)]);
                //System.out.println("aqui " + "linha"+ i + "colna"+  j + " " + board_template[cellNum(i,j)]);
            }
        }

        print(board_template);

    }

    public void print(int [] board_template){
        for(int i = 0; i <size; i++){
            for (int j = 0; j<size;j++)
            {
                System.out.println("aqui " + "coluna "+ i + " linha"+  j + " " + board_template[cellNum(i,j)]);
            }
        }
    }


    private int cellNum(int i, int j) {

        return (j * size)+ i;
    }

    private int getCellWidth() {

        return width / size;
    }

    private int getCellHeight() {

        return height / size;
    }

    private Cell getCell(int i, int j) {

        return cells[cellNum(i, j)];
    }


    void draw(Canvas canvas) {
        this.canvas = canvas;

        for (int i = 0; i < getCompleteSize(); i++) {
            cells[i].draw(canvas);
        }
    }

    //Trocas dos `botoes`
    boolean updateBoard(float x, float y) {
        int i = (int) x / getCellWidth();
        int j = (int) y / getCellHeight();


        int cellNumber = cellNum(i, j);
        int temp = Math.abs(cellNum(i + 1, j));
        if(i!=2) {

            if (temp >= 0 && temp < 9) {
                if (cells[temp].checkEmpty()) {
                    cells[temp].changeState(cells[cellNumber].returnNumber());
                    cells[cellNumber].changeState(0);
                    return true;
                }
            }
        }

        if(i!=0) {
            temp = Math.abs(cellNum(i - 1, j));

            if (temp >= 0 && temp < 9) {
                if (cells[temp].checkEmpty()) {
                    cells[temp].changeState(cells[cellNumber].returnNumber());
                    cells[cellNumber].changeState(0);
                    return true;
                }
            }
        }

        if(j!=2) {
            temp = Math.abs(cellNum(i, j + 1));

            if (temp >= 0 && temp < 9) {
                if (cells[temp].checkEmpty()) {
                    cells[temp].changeState(cells[cellNumber].returnNumber());
                    cells[cellNumber].changeState(0);
                    return true;
                }
            }
        }

        if(j!=0) {
            temp = Math.abs(cellNum(i, j - 1));

            if (temp >= 0 && temp < 9) {
                if (cells[temp].checkEmpty()) {
                    cells[temp].changeState(cells[cellNumber].returnNumber());
                    cells[cellNumber].changeState(0);
                    return true;
                }
            }
        }
        return false;
    }

    boolean check_game_state()
    {
        for (int i = 1; i<getCompleteSize();i++)
        {
            if(cells[i-1].returnNumber() != i)
            {
                return false;
            }
        }
        return true;
    }
    private boolean check_validity(int[] arr)
    {
        int inversions = 0;
        for(int i=0;i<getCompleteSize();i++)
        {
            for(int j=i+1;j<getCompleteSize();j++)
            {
                if(arr[i] > arr[j])
                    inversions++;
            }
        }

        return inversions % 2 == 0;
    }
}
