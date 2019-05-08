package com.claud.kafka.producer.vo;

public class OpeningAccountLog extends SessionLog {
    private int accountNumber;

    public OpeningAccountLog(){

    }

    public OpeningAccountLog(int accountNumber) {
        super();

        super.setLogtype(LogType.OPENING_ACCOUNT_LOG);
        this.accountNumber = accountNumber;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }
}
