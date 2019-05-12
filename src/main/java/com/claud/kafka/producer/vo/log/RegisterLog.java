package com.claud.kafka.producer.vo.log;

import com.claud.kafka.producer.vo.BaseVo;
import com.claud.kafka.producer.vo.send.LogKey;

public class RegisterLog extends BaseVo {
    private int userNumber;
    private String birthDay;
    private String userName;

    public RegisterLog(Integer userNumber, String userName, String birthDay) {
        super(LogType.REGISTER_LOG);
        this.userNumber = userNumber;
        this.userName = userName;
        this.birthDay = birthDay;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getUserName() {
        return userName;
    }

    public int getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(int userNumber) {
        this.userNumber = userNumber;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public LogKey getLogKey() {
        return new LogKey(this.getLogtype(), this.userNumber);
    }

    @Override
    public String toString() {
        return "RegisterLog{" +
                "userNumber=" + userNumber +
                ", birthDay='" + birthDay + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
