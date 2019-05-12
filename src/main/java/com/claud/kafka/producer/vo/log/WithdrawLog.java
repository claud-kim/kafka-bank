package com.claud.kafka.producer.vo.log;

import com.claud.kafka.producer.vo.BaseVo;
import com.claud.kafka.producer.vo.send.LogKey;

public class WithdrawLog extends BaseVo {
    private int userNumber;
    private String accountNumber;
    private long outputMoney;

    public WithdrawLog(int userNumber, String accountNumber, int outputMoney) {
        super(LogType.DEPOSIT_LOG);
        this.userNumber = userNumber;
        this.accountNumber = accountNumber;
        this.outputMoney = outputMoney;
    }

    public int getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(int userNumber) {
        this.userNumber = userNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public long getOutputMoney() {
        return outputMoney;
    }

    public void setOutputMoney(long outputMoney) {
        this.outputMoney = outputMoney;
    }

    @Override
    public LogKey getLogKey() {
        return new LogKey(this.getLogtype(), this.userNumber);
    }

    @Override
    public String toString() {
        return "WithdrawLog{" +
                "userNumber=" + userNumber +
                ", accountNumber='" + accountNumber + '\'' +
                ", outputMoney=" + outputMoney +
                '}';
    }
}
