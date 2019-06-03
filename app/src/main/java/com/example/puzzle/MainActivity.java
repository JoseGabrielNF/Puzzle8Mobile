package com.example.puzzle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.puzzle.Model.No;
import com.example.puzzle.Model.Num;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    public int [][] solution = new int [][] {{1,2,3},{4,5,6},{7,8,0}};

    No initialNode = new No();
    int [][] initialState = new int[][] {{4,1,3},{2,6,8},{7,5,0}};
    int stepsForSoluction =0;
    Num[] arrayNumbers = new Num [9];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialNode.setState(initialState);
        printState(initialNode.getState());


        makeCoordinates(solution, arrayNumbers);
        findSoluction(initialNode, solution, arrayNumbers);
    }

    public void findSoluction(No initialState, int [][] soluction, Num[] arrayNumbers){
        ArrayList<No> openStates = new ArrayList<>();
        openStates.add(initialState);
        ArrayList<int [][]> checkedStates = new ArrayList<>();
        int counter =0;

        while(openStates.size()!=0){
            counter++;

            if(counter == 500)
                break;
            Collections.sort(openStates);
            for(int i =0; i < openStates.size(); i++){
                System.out.print(openStates.get(i).getDistanceOfManhattam()+" ");
            }
            System.out.println("\n===========================================");
            No no = openStates.remove(0);
            System.out.println("===========================================");
            printState(no.getState());
            System.out.println("counter: "+counter+"tamanho do checked: "+checkedStates.size());
            checkedStates.add(no.getState());

            if(no.getState() == soluction){
                System.out.println("ENCONTROU A SOLUCAO=========================================");
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
                if(checkedStates.contains(childNode.getState())==false){
                    //System.out.println("ENTROU CONTAINS ====================================");
                    childNode.setDistanceOfManhattam(distanceOfManhattam(arrayNumbers, childNode.getState()));
                    childNode.setPredecessor(no);
                    openStates.add(childNode);
                }
            }


            if(localizationOfZero[0] !=2) {
                No childNode = new No();
                childNode.setState(copyState(no.getState()));
                childNode.setState(down(childNode.getState()));

                if(checkedStates.contains(childNode.getState())==false){
                    //System.out.println("ENTROU CONTAINS ====================================");
                    childNode.setDistanceOfManhattam(distanceOfManhattam(arrayNumbers, childNode.getState()));
                    childNode.setPredecessor(no);
                    openStates.add(childNode);
                }
            }




            if(localizationOfZero[1] !=0) {
                No childNode = new No();
                childNode.setState(copyState(no.getState()));
                childNode.setState(left(childNode.getState()));

                if (checkedStates.contains(childNode.getState())==false) {
                    //System.out.println("ENTROU CONTAINS ====================================");
                    childNode.setDistanceOfManhattam(distanceOfManhattam(arrayNumbers, childNode.getState()));
                    childNode.setPredecessor(no);
                    openStates.add(childNode);
                }

            }


            if(localizationOfZero[1] !=2) {
                No childNode = new No();
                childNode.setState(copyState(no.getState()));
                childNode.setState(right(childNode.getState()));

                if (checkedStates.contains(childNode.getState())==false) {
                    //System.out.println("ENTROU CONTAINS ====================================");
                    childNode.setDistanceOfManhattam(distanceOfManhattam(arrayNumbers, childNode.getState()));
                    childNode.setPredecessor(no);
                    openStates.add(childNode);
                }

            }



        }
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
        printState(no.getState());
    }

    public int distanceOfManhattam(Num[] arrayNumbers, int [][] state){
        int total = 0;
        for(int i =0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                int number = state[i][j];
                total += Math.abs(i - arrayNumbers[number].getX()) + Math.abs(j - arrayNumbers[number].getY());
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

    /*Recebe uma Matriz e retorna as coordenadas do nó Zero*/
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
