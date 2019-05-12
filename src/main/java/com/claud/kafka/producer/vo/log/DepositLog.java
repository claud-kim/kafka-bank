package com.claud.kafka.producer.vo.log;

import com.claud.kafka.producer.vo.BaseVo;
import com.claud.kafka.producer.vo.send.LogKey;

public class DepositLog extends BaseVo {
    private Integer userNumber;
    private String accountNumber;
    private int insertMoney;

    public DepositLog(Integer userNumber, String insertAccountNumber, int insertMoney) {
        super(LogType.DEPOSIT_LOG);
        this.userNumber = userNumber;
        this.accountNumber = insertAccountNumber;
        this.insertMoney = insertMoney;
    }

    public Integer getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(Integer userNumber) {
        this.userNumber = userNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setInsertMoney(int insertMoney) {
        this.insertMoney = insertMoney;
    }

    public int getInsertMoney() {
        return insertMoney;
    }

    @Override
    public LogKey getLogKey() {
        return new LogKey(this.getLogtype(), this.userNumber);
    }

    @Override
    public String toString() {
        return "DepositLog{" +
                "insertMoney=" + insertMoney +
                ", userNumber=" + userNumber +
                ", accountNumber='" + accountNumber + '\'' +
                ", insertMoney=" + insertMoney +
                '}';
    }
}
