package com.example.puzzle;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.puzzle.Model.No;
import com.example.puzzle.Model.Num;
import com.example.puzzle.UI.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;



public class MainActivity extends AppCompatActivity {
    public int [][] solution = new int [][] {{1,2,3},{4,5,6},{7,8,0}};

    //stores a sequence of strings for the solution. Ex: "up", "down", "left", "right"
    //By default the first position is null.
    public ArrayList<String> wayToSolve = new ArrayList<>();
    No initialNode = new No();
    int [][] initialState = new int[][] {{2,3,0},{1,8,6},{5,7,4}};

    Num[] arrayNumbers = new Num [9];
    int stepsForSoluction =0;

    GameView.GameViewActionListener listener;
    //int matriz [] = GameBoard.print();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // initialNode.setState(initialState);
     //printState(initialNode.getState());

        createListener();
        drawBoard();

        //pegar a matriz iniciar e substituir no initialState
        for(int i = 0; i < initialState.length; i++){
            for (int j = 0; j< initialState.length;j++){

            }
        }



   // int positionZero[] = findZero((initialNode.getState()));

        //System.out.println("X = "+positionZero[0]+"Y = "+positionZero[1]);

   // makeCoordinates(initialState, arrayNumbers);
   // findSoluction(initialNode, solution, arrayNumbers);

    }

    // Instanciar a tela
    private void createListener() {
        listener = new GameView.GameViewActionListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void showMessage(String message) {
                setMessage(message);
            }
        };
    }

    private void drawBoard() {
        ViewGroup view = (ViewGroup) findViewById(R.id.game);


        View gameView = new GameView(this, null, listener);
        view.addView(gameView);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void setMessage(String message) {
        if (Objects.equals(message, "create_new_game")) {
            newGame(null);
            return;
        }
        TextView view = (TextView) findViewById(R.id.moves);
        view.setText(message);
    }

    public void newGame(View view) {

        if (view != null) {

            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            //Yes button clicked
                            ViewGroup n_view = (ViewGroup) findViewById(R.id.game);


                            View gameView = new GameView(MainActivity.this, null, listener);
                            n_view.addView(gameView);
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure that you want to start a new game? You will lose your current progress").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();

        } else {
            ViewGroup n_view = (ViewGroup) findViewById(R.id.game);


            View gameView = new GameView(this, null, listener);
            n_view.addView(gameView);
        }
    }
    //Fim instancia tela

    public void findSoluction(No initialState, int [][] soluction, Num[] arrayNumbers){
        ArrayList<No> openStates = new ArrayList<>();
        openStates.add(initialState);
        ArrayList <String> checkedStates = new ArrayList();
        int counter =0;

        while(openStates.size()!=0){
            counter++;

            Collections.sort(openStates);
            No no = openStates.remove(0);
            checkedStates.add(hashMatrix(no.getState()));

            if(compareMatriz(no.getState(), soluction)){
                System.out.println("Soluction Found!");
                printSoluction(no);
                System.out.println("Steps: "+stepsForSoluction);
                break;
            }


            int [] localizationOfZero = findZero(no.getState());

            //Generating states and adding to the open states if it has not been opened yet.

            if(localizationOfZero[0] !=0){

                No childNode = new No();
                childNode.setState(copyState(no.getState()));
                childNode.setState(up(childNode.getState()));


                if(checkedStates.contains(hashMatrix(childNode.getState())) != true){
                    childNode.setDistanceOfManhattam(distanceOfManhattam(arrayNumbers, childNode.getState()));
                    childNode.setPredecessor(no);
                    childNode.setOrientation("up");
                    openStates.add(childNode);
                }
            }


            if(localizationOfZero[0] !=2) {
                No childNode = new No();
                childNode.setState(copyState(no.getState()));
                childNode.setState(down(childNode.getState()));


                if(checkedStates.contains(hashMatrix(childNode.getState()))!= true){
                    childNode.setDistanceOfManhattam(distanceOfManhattam(arrayNumbers, childNode.getState()));
                    childNode.setPredecessor(no);
                    childNode.setOrientation("down");
                    openStates.add(childNode);
                }
            }




            if(localizationOfZero[1] !=0) {
                No childNode = new No();
                childNode.setState(copyState(no.getState()));
                childNode.setState(left(childNode.getState()));


                if (checkedStates.contains(hashMatrix(childNode.getState()))!= true) {
                    childNode.setDistanceOfManhattam(distanceOfManhattam(arrayNumbers, childNode.getState()));
                    childNode.setPredecessor(no);
                    childNode.setOrientation("left");
                    openStates.add(childNode);
                }

            }


            if(localizationOfZero[1] !=2) {

                No childNode = new No();
                childNode.setState(copyState(no.getState()));
                childNode.setState(right(childNode.getState()));
                if (checkedStates.contains(hashMatrix(childNode.getState()))!= true) {
                    childNode.setDistanceOfManhattam(distanceOfManhattam(arrayNumbers, childNode.getState()));
                    childNode.setPredecessor(no);
                    childNode.setOrientation("right");
                    openStates.add(childNode);
                }

            }



        }
    }

    public boolean compareMatriz(int[][]matriz, int[][]solution){
        for(int i =0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(matriz[i][j]!=solution[i][j])
                    return false;
            }
        }
        return true;
    }

    public String hashMatrix(int [][] matriz){
        String hash = "";
        for(int i =0; i < 3; i++){
            for(int j =0; j <3; j++){
                hash += matriz[i][j];
            }
        }
        return hash;
    }

    public int[][] copyState(int[][] matriz){
        int[][] temp = new int [3][3];
        for(int i =0; i < 3; i++){
            for(int j =0; j <3; j++){
                temp[i][j] = matriz[i][j];
            }
        }
        return temp;
    }

    public int[][] right(int[][] matriz){
        int [] positionZero = findZero(matriz);
        matriz[positionZero[0]][positionZero[1]] = matriz[positionZero[0]][positionZero[1]+1];
        matriz[positionZero[0]][positionZero[1]+1] = 0;
        return matriz;
    }

    public int[][] left(int[][] matriz){
        int [] positionZero = findZero(matriz);
        matriz[positionZero[0]][positionZero[1]] = matriz[positionZero[0]][positionZero[1]-1];
        matriz[positionZero[0]][positionZero[1]-1] = 0;
        return matriz;
    }


    public int[][] up(int[][] matriz) {
        int[] positionZero = findZero(matriz);
        matriz[positionZero[0]][positionZero[1]] = matriz[positionZero[0]-1][positionZero[1]];
        matriz[positionZero[0]-1][positionZero[1]] = 0;
        return matriz;
    }

    public int[][] down(int[][] matriz) {
        int[] positionZero = findZero(matriz);
        matriz[positionZero[0]][positionZero[1]] = matriz[positionZero[0]+1][positionZero[1]];
        matriz[positionZero[0]+1][positionZero[1]] = 0;
        return matriz;
    }

    public void printSoluction(No no){
        stepsForSoluction+=1;
        if(no.getPredecessor()!= null)
            printSoluction(no.getPredecessor());
        wayToSolve.add(no.getOrientation());
        System.out.println(no.getOrientation());
        printState(no.getState());
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
        System.out.println("\n=====================================");
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
