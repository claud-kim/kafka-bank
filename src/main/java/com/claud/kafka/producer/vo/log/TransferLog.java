package com.claud.kafka.producer.vo.log;

import com.claud.kafka.producer.vo.BaseVo;
import com.claud.kafka.producer.vo.send.LogKey;

public class TransferLog extends BaseVo {
    private int userNumber;
    private String outAccountNumber;
    private long outMoney;
    private String destBank;
    private String destAccountNumber;
    private String destAccountName;

    public TransferLog(int userNumber, String outAccountNumber, String destBank, String destAccountNumber, String destCustomerNumber,
                       int transferMoney) {
        super(LogType.TRANSFER_LOG);
        this.userNumber = userNumber;
        this.outAccountNumber = outAccountNumber;
        this.outMoney = transferMoney;

        this.destBank = destBank;
        this.destAccountNumber = destAccountNumber;
        this.destAccountName = destCustomerNumber;

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

    public String getDestBank() {
        return destBank;
    }

    public void setDestBank(String destBank) {
        this.destBank = destBank;
    }

    public String getDestAccountNumber() {
        return destAccountNumber;
    }

    public void setDestAccountNumber(String destAccountNumber) {
        this.destAccountNumber = destAccountNumber;
    }

    public String getDestAccountName() {
        return destAccountName;
    }

    public void setDestAccountName(String destAccountName) {
        this.destAccountName = destAccountName;
    }

    @Override
    public LogKey getLogKey() {
        return new LogKey(this.getLogtype(), this.userNumber);
    }

    @Override
    public String toString() {
        return "TransferLog{" +
                "userNumber=" + userNumber +
                ", outAccountNumber='" + outAccountNumber + '\'' +
                ", outMoney=" + outMoney +
                ", destBank='" + destBank + '\'' +
                ", destAccountNumber='" + destAccountNumber + '\'' +
                ", destAccountName='" + destAccountName + '\'' +
                '}';
    }
}
