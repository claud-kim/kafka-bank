package com.claud.kafka.producer.vo.gen;

public class DestInfo {
    private String deskBank;
    private String deskAccountNumber;
    private String destAccountName;

    public DestInfo(String deskBank, String deskAccountNumber, String destAccountName) {
        this.deskBank = deskBank;
        this.deskAccountNumber = deskAccountNumber;
        this.destAccountName = destAccountName;
    }

    public String getDeskBank() {
        return deskBank;
    }

    public void setDeskBank(String deskBank) {
        this.deskBank = deskBank;
    }

    public String getDeskAccountNumber() {
        return deskAccountNumber;
    }

    public void setDeskAccountNumber(String deskAccountNumber) {
        this.deskAccountNumber = deskAccountNumber;
    }

    public String getDestAccountName() {
        return destAccountName;
    }

    public void setDestAccountName(String destAccountName) {
        this.destAccountName = destAccountName;
    }
}
