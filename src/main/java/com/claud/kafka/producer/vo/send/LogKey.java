package com.claud.kafka.producer.vo.send;

import com.claud.kafka.producer.vo.log.LogType;

public class LogKey {
    private LogType logType;
    private Integer customerNumber;

    public LogKey(LogType logType, Integer customerNumber) {
        this.logType = logType;
        this.customerNumber = customerNumber;
    }

    public LogType getLogType() {
        return logType;
    }

    public void setLogType(LogType logType) {
        this.logType = logType;
    }

    public Integer getCustomerNumber() {
        return customerNumber;
    }


    public void setCustomerNumber(Integer customerNumber) {
        this.customerNumber = customerNumber;
    }

    @Override
    public String toString() {
        return "LogKey{" +
                "logType=" + logType +
                ", customerNumber=" + customerNumber +
                '}';
    }
}
