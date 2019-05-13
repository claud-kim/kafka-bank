package com.claud.kafka.consumer.vo;

import com.google.gson.annotations.Expose;

import java.util.Date;

public class Customer {
    @Expose
    private Integer customerNumber;
    @Expose
    private String customerName;
    @Expose
    private String birthDay;
    @Expose
    private Date registerTime;
    @Expose
    private int totalSessionCnt;

    public Customer(Integer customerNumber) {
        this.customerNumber = customerNumber;
        this.totalSessionCnt = 0;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public int getTotalSessionCnt() {
        return totalSessionCnt;
    }

    public void setTotalSessionCnt(int totalSessionCnt) {
        this.totalSessionCnt = totalSessionCnt;
    }

    public Integer getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(Integer customerNumber) {
        this.customerNumber = customerNumber;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerName='" + customerName + '\'' +
                ", birthDay='" + birthDay + '\'' +
                ", registerTime=" + registerTime +
                ", totalSessionCnt=" + totalSessionCnt +
                '}';
    }


    public synchronized void updateSessionCnt() {
        this.totalSessionCnt += 1;
    }
}
