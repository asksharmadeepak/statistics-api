package com.example.statistics.api.model;

import java.io.Serializable;

public class Calculation implements Serializable {

    private int total;
    private double sumX;
    private double avgX;
    private double sumY;
    private double avgY;

    public Calculation() {
    }

    public Calculation(int total, double sumX, double avgX, double sumY, double avgY) {
        this.total = total;
        this.sumX = sumX;
        this.avgX = avgX;
        this.sumY = sumY;
        this.avgY = avgY;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public double getSumX() {
        return sumX;
    }

    public void setSumX(double sumX) {
        this.sumX = sumX;
    }

    public double getAvgX() {
        return avgX;
    }

    public void setAvgX(double avgX) {
        this.avgX = avgX;
    }

    public double getSumY() {
        return sumY;
    }

    public void setSumY(double sumY) {
        this.sumY = sumY;
    }

    public double getAvgY() {
        return avgY;
    }

    public void setAvgY(double avgY) {
        this.avgY = avgY;
    }

    @Override
    public String toString() {
        return total+","+sumX+","+avgX+","+sumY+","+avgY;
    }
}