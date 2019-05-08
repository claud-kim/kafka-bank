package com.claud.kafka.producer.vo;

public class DepositLog extends OpeningAccountLog {
    private long money;

    public DepositLog() {

    }

    public DepositLog(OpeningAccountLog log, long money) {
        super(log.getCustomerNumber());
        this.money = money;
        this.setLogtype(LogType.DEPOSIT_LOG);
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }
}
