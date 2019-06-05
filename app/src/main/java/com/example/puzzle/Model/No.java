package com.example.puzzle.Model;

import java.util.Arrays;
import java.util.Comparator;

public class No implements Comparable<No>{

    private No predecessor = null;
    private int  [][] state;
    private int distanceOfManhattam;
    private String orientation;




    public int[][] getState() {
        return state;
    }

    public void setState(int[][] state) {
        this.state = state;
    }


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


    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    @Override
    public int compareTo(No no) {
        if (this.distanceOfManhattam < no.getDistanceOfManhattam()) {
            return -1;
        }
        if (this.distanceOfManhattam > no.getDistanceOfManhattam()) {
            return 1;
        }
        return 0;
    }
}
