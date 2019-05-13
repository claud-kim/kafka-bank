package com.claud.kafka;

import org.javasimon.Split;

public abstract class GenStopwatch {

    public abstract String getStopWatchName(String methodName);

    public abstract String getServicePrefix();

    public long getRunningForMillis(Split split) {
        return Math.round(split.runningFor() / 1_000_000);
    }

}