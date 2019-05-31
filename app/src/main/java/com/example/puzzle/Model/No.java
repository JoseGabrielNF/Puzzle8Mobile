package com.example.puzzle.Model;

import java.util.Arrays;

public class No {
    private No predecessor = null;
    private int  [][] state;

    public int[][] getState() {
        return state;
    }

    public void setState(int[][] state) {
        this.state = state;
    }

    private int distanceOfManhattam;


    public No getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(No predecessor) {
        this.predecessor = predecessor;
    }


    public int getDistanceOfManhattam() {
        return distanceOfManhattam;
    }

    public void setDistanceOfManhattam(int distanceOfManhattam) {
        this.distanceOfManhattam = distanceOfManhattam;
    }

}
