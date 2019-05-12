package com.claud.kafka.producer.vo.log;

import com.claud.kafka.producer.vo.BaseVo;
import com.claud.kafka.producer.vo.send.LogKey;

public class WithdrawLog extends BaseVo {
    private int userNumber;
    private String outAccountNumber;
    private long outMoney;

    public WithdrawLog(int userNumber, String outAccountNumber, int outMoney) {
        super(LogType.DEPOSIT_LOG);
        this.userNumber = userNumber;
        this.outAccountNumber = outAccountNumber;
        this.outMoney = outMoney;
    }

    public int getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(int userNumber) {
        this.userNumber = userNumber;
    }

    public String getOutAccountNumber() {
        return outAccountNumber;
    }

    public void setOutAccountNumber(String outAccountNumber) {
        this.outAccountNumber = outAccountNumber;
    }

    public long getOutMoney() {
        return outMoney;
    }

    public void setOutMoney(long outMoney) {
        this.outMoney = outMoney;
    }

    @Override
    public LogKey getLogKey() {
        return new LogKey(this.getLogtype(), this.userNumber);
    }

    @Override
    public String toString() {
        return "WithdrawLog{" +
                "userNumber=" + userNumber +
                ", outAccountNumber='" + outAccountNumber + '\'' +
                ", outMoney=" + outMoney +
                '}';
    }
}
