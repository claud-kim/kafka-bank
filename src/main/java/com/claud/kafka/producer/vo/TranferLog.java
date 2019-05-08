package com.claud.kafka.producer.vo;

public class TranferLog extends WithdrawLog {
    private String destBank;
    private int destAccountNumber;
    private int destCustomerNumber;

    public TranferLog(){
    }

    public TranferLog(WithdrawLog log, String destBank, int destAccountNumber, int destCustomerNumber) {
        super(log,log.getMoney());
        this.destBank = destBank;
        this.destAccountNumber = destAccountNumber;
        this.destCustomerNumber = destCustomerNumber;

        this.setLogtype(LogType.TRANFER_LOG);
    }

    public String getDestBank() {
        return destBank;
    }

    public void setDestBank(String destBank) {
        this.destBank = destBank;
    }

    public int getDestAccountNumber() {
        return destAccountNumber;
    }

    public void setDestAccountNumber(int destAccountNumber) {
        this.destAccountNumber = destAccountNumber;
    }

    public int getDestCustomerNumber() {
        return destCustomerNumber;
    }

    public void setDestCustomerNumber(int destCustomerNumber) {
        this.destCustomerNumber = destCustomerNumber;
    }
}
