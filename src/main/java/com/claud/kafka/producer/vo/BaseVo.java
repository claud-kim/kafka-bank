package com.claud.kafka.producer.vo;

import java.util.Date;

public class BaseVo {
    private LogType logtype;
    private Date time;

    public BaseVo() {
        this.logtype = LogType.NONE;
    }

    public BaseVo(LogType logtype) {
        this.logtype = logtype;
        this.time = new Date();
    }

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
}
