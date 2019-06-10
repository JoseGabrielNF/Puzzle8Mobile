package com.example.puzzle.UI;

import android.content.Context;
import android.graphics.Canvas;

import com.example.puzzle.Model.No;
import com.example.puzzle.Model.Num;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class GameBoard {

    private final int size = 3;
    private int width;
    private int height;
    private Context context;
    private Cell[] cells;
    private Canvas canvas;


    public int [][] initialState = new int[][] {{1,2,3},{4,5,6},{7,8,0}};
    public int [][] soluction = new int [][] {{1,2,3},{4,5,6},{7,8,0}};
    int stepsForSoluction =0;
    public ArrayList<String> wayToSolve = new ArrayList<>();
    No initialNode = new No();
    Num[] arrayNumbers = new Num [9];
    private Thread t1;

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
                initialState[j][i] = board_template[cellNum(i,j)];

            }
        }



    }

    // Modifico o initialState a cada movimento que o jogador faz para jogar na IA com o botao Resolva com a matriz atualizada
    public void changeInitialState(){
        int k = 0;
        for(int i = 0; i <size; i++){
            for (int j = 0; j<size;j++)
            {
                initialState[i][j] = cells[k].returnNumber();
                k++;
            }
        }
    }

    public int getNMoves(){
        return this.stepsForSoluction;
    }

    public void calcSolve(){
        wayToSolve.clear();
        stepsForSoluction = 0;
        for(int i =0; i < arrayNumbers.length; i++){
            arrayNumbers[i] = null;
        }
        initialNode.setState(initialState);
        makeCoordinates(soluction, arrayNumbers);
        findSoluction(initialNode, soluction, arrayNumbers);
    }

    public void solve(){
        if(wayToSolve.size()== 0) {
            return;
        }


        int zero [];



        zero = findZero(initialState);

            // preciso mudar no layout
            if(wayToSolve.get(0).equals("up")){
                int cellNumber = cellNum(zero[1], zero[0]-1);
                int temp = Math.abs(cellNum(zero[1] , zero[0]));
                if (cells[temp].checkEmpty()) {
                    cells[temp].changeState(cells[cellNumber].returnNumber());
                    cells[cellNumber].changeState(0);
                    changeInitialState();

                }
            }
            if(wayToSolve.get(0).equals("down")){
                int cellNumber = cellNum(zero[1], zero[0]+1);
                int temp = Math.abs(cellNum(zero[1] , zero[0]));
                if (cells[temp].checkEmpty()) {
                    cells[temp].changeState(cells[cellNumber].returnNumber());
                    cells[cellNumber].changeState(0);
                    changeInitialState();

                }

            }
            if(wayToSolve.get(0).equals("right")){
                int cellNumber = cellNum(zero[1]+1, zero[0]);
                int temp = Math.abs(cellNum(zero[1] , zero[0]));
                  if (cells[temp].checkEmpty()) {
                    cells[temp].changeState(cells[cellNumber].returnNumber());
                    cells[cellNumber].changeState(0);
                    changeInitialState();

                }

            }
            if(wayToSolve.get(0).equals("left")){
                int cellNumber = cellNum(zero[1]-1, zero[0]);
                int temp = Math.abs(cellNum(zero[1] , zero[0]));

                if (cells[temp].checkEmpty()) {
                    cells[temp].changeState(cells[cellNumber].returnNumber());
                    cells[cellNumber].changeState(0);
                    
                    changeInitialState();

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

        //Movimento para direita
        if(i!=2) {
            if (temp >= 0 && temp < 9) {
                if (cells[temp].checkEmpty()) {
                    cells[temp].changeState(cells[cellNumber].returnNumber());
                    cells[cellNumber].changeState(0);
                    changeInitialState();
                    return true;
                }
            }
        }
        //Movimento esquerda
        if(i!=0) {
            temp = Math.abs(cellNum(i - 1, j));
            if (temp >= 0 && temp < 9) {
                if (cells[temp].checkEmpty()) {
                    cells[temp].changeState(cells[cellNumber].returnNumber());
                    cells[cellNumber].changeState(0);
                    changeInitialState();
                    return true;
                }
            }
        }
        //Movimento para baixo
        if(j!=2) {
            temp = Math.abs(cellNum(i, j + 1));
            if (temp >= 0 && temp < 9) {
                if (cells[temp].checkEmpty()) {
                    cells[temp].changeState(cells[cellNumber].returnNumber());
                    cells[cellNumber].changeState(0);
                    changeInitialState();
                    return true;
                }
            }
        }
        //Movimento para cima
        if(j!=0) {
            temp = Math.abs(cellNum(i, j - 1));

            if (temp >= 0 && temp < 9) {
                if (cells[temp].checkEmpty()) {
                    cells[temp].changeState(cells[cellNumber].returnNumber());
                    cells[cellNumber].changeState(0);
                    changeInitialState();
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
                if(arr[j]!=0 && arr[i] > arr[j])
                    inversions++;
            }
        }

        return inversions % 2 == 0;
    }

    //IA

    public void findSoluction(No initialState, int [][] soluction, Num[] arrayNumbers){
        ArrayList<No> openStates = new ArrayList<>();
        openStates.add(initialState);
        ArrayList <String> checkedStates = new ArrayList();
        int counter =0;

        while(openStates.size()!=0){
            counter++;
            System.out.println("contador" + counter);
            Collections.sort(openStates);
            No no = openStates.remove(0);
            checkedStates.add(hashMatrix(no.getState()));

            if(compareMatriz(no.getState(), soluction)){
                //System.out.println("Você Venceu!");
                printSoluction(no);
                System.out.println("Movimentos: "+stepsForSoluction);
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
