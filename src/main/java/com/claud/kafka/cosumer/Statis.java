package com.claud.kafka.cosumer;

public class Statis implements MinMax {
    private long min;
    private long max;

    public Statis(){
        this.min = Long.MAX_VALUE;
        this.max = Long.MIN_VALUE;
    }

    public void updateMinMax(long money){
        if ( this.min > money ){
            this.min = money;
        }

        if ( this.max < money ) {
            this.max = money;
        }
    }

    public long getMin() {
        return min;
    }

    public void setMin(long min) {
        this.min = min;
    }

    public long getMax() {
        return max;
    }

    public void setMax(long max) {
        this.max = max;
    }
}
