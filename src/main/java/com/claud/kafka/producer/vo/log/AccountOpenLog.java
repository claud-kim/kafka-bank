package com.claud.kafka.producer.vo.log;

import com.claud.kafka.producer.vo.BaseVo;
import com.claud.kafka.producer.vo.send.LogKey;

public class AccountOpenLog extends BaseVo {
    private Integer userNumber;
    private String accountNumber;

    public AccountOpenLog(Integer userNumber, String accountNumber) {
        super(LogType.ACCOUNT_OPEN_LOG);
        this.userNumber = userNumber;
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public LogKey getLogKey() {
        return new LogKey(this.getLogtype(), this.userNumber);
    }

    @Override
    public String toString() {
        return "AccountOpenLog{" +
                "userNumber=" + userNumber +
                ", accountNumber='" + accountNumber + '\'' +
                '}';
    }
}
