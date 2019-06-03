package com.example.puzzle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.puzzle.Model.No;
import com.example.puzzle.Model.Num;

public class MainActivity extends AppCompatActivity {
    public int [][] solution = new int [][] {{1,2,3},{4,5,6},{7,8,0}};

    No initialNode = new No();
    int [][] initialState = new int[][] {{4,1,3},{2,6,8},{7,5,0}};

    Num[] arrayNumbers = new Num [9];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialNode.setState(initialState);
    printState(initialNode.getState());

    final ImageView img = (ImageView) findViewById(R.id.seis);
        img.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            ViewGroup.LayoutParams layParamsGet= img.getLayoutParams();
            int b=layParamsGet.height;
            int a=layParamsGet.width;
            img.setTranslationX(b);
            Toast.makeText(MainActivity.this, "seis"+a+" "+b, Toast.LENGTH_SHORT).show();
        }
    });


    int positionZero[] = findZero((initialNode.getState()));

        System.out.println("X = "+positionZero[0]+"Y = "+positionZero[1]);

    makeCoordinates(initialState, arrayNumbers);

}

    public int distanceOfManhattam(Num[] arrayNumbers, int [][] state){
        int total = 0;
        for(int i =0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                int number = state[i][j];
                total += Math.abs(i - arrayNumbers[number].getX()) + Math.abs(j - arrayNumbers[number].getY()) ;
            }
        }
        return total;
    }

    public void makeCoordinates(int [][] initialState, Num[] arrayNumbers){
        for(int i = 0; i < 3; i++){
            for(int j =0; j <3; j++){
                Num number = new Num(i, j, initialState[i][j]);
                arrayNumbers[number.getValue()] = number;
            }
        }
    }

    public void printState(int[][] state){
        for(int i =0; i < 3; i++){
            for(int j =0; j < 3; j++){
                System.out.print("["+state[i][j]+"] ");
            }
            System.out.println("");
        }
    }

    /*Recebe uma Matriz e retorna as coordenadas do valor Zero*/
    public int[] findZero(int [][] state){
        int positionZero[] = new int [2];
        for(int i =0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(state[i][j] == 0){
                    positionZero[0] = i;
                    positionZero[1] = j;
                }
            }
        }
        return positionZero;
    }
}
