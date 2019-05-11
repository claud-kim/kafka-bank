package com.claud.kafka.producer.vo.log;

import com.claud.kafka.producer.vo.BaseVo;

public class OpeningAccountLog extends BaseVo {
    private Integer userNumber;
    private String accountNumber;

    public OpeningAccountLog(Integer userNumber, String accountNumber) {
        super(LogType.OPENING_ACCOUNT_LOG);
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
    public String toString() {
        return "OpeningAccountLog{" +
                "accountNumber='" + accountNumber + '\'' +
                '}';
    }
}
