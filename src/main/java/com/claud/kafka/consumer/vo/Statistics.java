package com.claud.kafka.consumer.vo;

import com.claud.kafka.consumer.MinMax;
import com.google.gson.annotations.Expose;

public class Statistics implements MinMax {
    private int calMin;
    private int calMax;

    @Expose
    private Integer min;
    @Expose
    private Integer max;

    public Statistics() {
        this.calMin = Integer.MAX_VALUE;
        this.calMax = Integer.MIN_VALUE;

        min = null;
        max = null;
    }

    public void updateMinMax(int money) {
        if (this.calMin > money) {
            this.calMin = money;
            this.min = money;
        }

        if (this.calMax < money) {
            this.calMax = money;
            this.max = money;
        }
    }

    public int getCalMin() {
        return calMin;
    }

    public void setCalMin(int calMin) {
        this.calMin = calMin;
    }

    public int getCalMax() {
        return calMax;
    }

    public void setCalMax(int calMax) {
        this.calMax = calMax;
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    @Override
    public String toString() {
        return "Statistics{" +
                "min=" + min +
                ", max=" + max +
                '}';
    }
}

