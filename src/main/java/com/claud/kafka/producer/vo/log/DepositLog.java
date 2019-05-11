package com.claud.kafka.producer.vo.log;

import com.claud.kafka.producer.vo.BaseVo;

public class DepositLog extends BaseVo {
    private Integer userNumber;
    private String accountNumber;
    private long insertMoney;

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

    public long getInsertMoney() {
        return insertMoney;
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
