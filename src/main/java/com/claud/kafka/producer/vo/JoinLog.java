package com.claud.kafka.producer.vo;

import java.util.Date;

public class JoinLog extends SessionLog {
    private String birthDay;
    private String name;

    public JoinLog() {
    }

    public JoinLog(SessionLog log, String birthDay, String name){
        this.setCustomerNumber(log.getCustomerNumber());
        this.setTime(new Date());

        this.birthDay = birthDay;
        this.name = name;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
