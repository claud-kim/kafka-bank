package com.claud.kafka.producer.vo.log;

import com.claud.kafka.producer.vo.BaseVo;
import com.claud.kafka.producer.vo.send.LogKey;

public class SessionLog extends BaseVo {
    private Integer userNumber; //null able

    public SessionLog(Integer userNumber) {
        super(LogType.SESSION_LOG);
        this.userNumber = userNumber;
    }

    public SessionLog(LogType type, Integer userNumber) {
        super.setLogtype(type);
        this.userNumber = userNumber;
    }

    public Integer getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(Integer userNumber) {
        this.userNumber = userNumber;
    }

    @Override
    public LogKey getLogKey() {
        return new LogKey(this.getLogtype(), this.userNumber);
    }

    @Override
    public String toString() {
        return "SessionLog{" +
                "userNumber=" + userNumber +
                '}';
    }
}
