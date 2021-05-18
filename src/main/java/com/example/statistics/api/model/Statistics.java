package com.example.statistics.api.model;

import java.io.Serializable;

public class Statistics implements Serializable {

    private Long timestamp;
    private Double inputX;
    private Integer inputY;

    public Statistics() {
    }

    public Statistics(Long timestamp, Double inputX, Integer inputY) {
        this.timestamp = timestamp;
        this.inputX = inputX;
        this.inputY = inputY;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Double getInputX() {
        return inputX;
    }

    public void setInputX(Double inputX) {
        this.inputX = inputX;
    }

    public Integer getInputY() {
        return inputY;
    }

    public void setInputY(Integer inputY) {
        this.inputY = inputY;
    }
}
