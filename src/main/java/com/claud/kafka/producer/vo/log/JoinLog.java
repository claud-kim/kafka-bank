package com.claud.kafka.producer.vo.log;

import com.claud.kafka.producer.vo.BaseVo;
import com.claud.kafka.producer.vo.send.LogKey;

public class JoinLog extends BaseVo {
    private int userNumber;
    private String birthDay;
    private String name;

    public JoinLog(Integer userNumber, String name, String birthDay) {
        super(LogType.JOIN_LOG);
        this.userNumber = userNumber;
        this.name = name;
        this.birthDay = birthDay;
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

    public int getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(int userNumber) {
        this.userNumber = userNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public LogKey getLogKey() {
        return new LogKey(this.getLogtype(), this.userNumber);
    }

    @Override
    public String toString() {
        return "JoinLog{" +
                "userNumber=" + userNumber +
                ", birthDay='" + birthDay + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
