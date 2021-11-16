package com.example.myapplication.Model;

import java.io.Serializable;

public class Temp implements Serializable {
    private double min;
    private double max;

    public Temp() {}

    public Temp(double min, double max) {
        this.min = min;
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public void setMax(double max) {
        this.max = max;
    }
}
