package com.claud.kafka.producer.vo;

import com.claud.kafka.producer.vo.log.LogType;
import com.claud.kafka.producer.vo.send.LogKey;

import java.util.Date;

public abstract class BaseVo {
    private LogType logtype;
    private Date time;

    public BaseVo() {
        this.logtype = LogType.NONE;
    }

    public BaseVo(LogType logtype) {
        this.logtype = logtype;
        this.time = new Date();
    }

    public abstract LogKey getLogKey();

    public LogType getLogtype() {
        return logtype;
    }

    public void setLogtype(LogType logtype) {
        this.logtype = logtype;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "BaseVo{" +
                "logtype=" + logtype +
                ", time=" + time +
                '}';
    }
}
