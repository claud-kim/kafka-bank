package com.claud.kafka.producer.vo;

public class WithdrawLog extends  OpeningAccountLog{
    private long money;

    public WithdrawLog(){
    }

    public WithdrawLog(OpeningAccountLog log, long money) {
        super(log.getCustomerNumber());
        this.money = money;
        this.setLogtype(LogType.WITHDRAW_LOG);
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }
}
