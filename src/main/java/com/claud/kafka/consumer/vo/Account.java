package com.claud.kafka.consumer.vo;

import com.claud.kafka.producer.vo.log.LogType;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class Account {
    @Expose
    private String AccountNumber;
    @Expose
    private int balance;

    private final static int LASTEST_NUM = 3;

    @Expose
    private final List<String> latestTrade = new ArrayList<String>(LASTEST_NUM);
    @Expose
    private Statis depositLog;
    @Expose
    private Statis withdrawLog;
    @Expose
    private Statis transferLog;


    public Account() {
        this.balance = 0;
        this.depositLog = new Statis();
        this.withdrawLog = new Statis();
        this.transferLog = new Statis();
    }

    public void addTrade(String trade) {
        latestTrade.add(0, trade);
        if (latestTrade.size() > LASTEST_NUM) {
            latestTrade.remove(latestTrade.size() - 1);
        }
    }

    public List<String> getLatestTrade() {
        return latestTrade;
    }

    public void update(LogType logType, int money) {
        if (LogType.DEPOSIT_LOG.equals(logType)) {
            this.balance += money;
            this.depositLog.updateMinMax(money);
        } else if (LogType.WITHDRAW_LOG.equals(logType)) {
            this.balance -= money;
            this.withdrawLog.updateMinMax(money);
        } else if (LogType.TRANSFER_LOG.equals(logType)) {
            this.balance -= money;
            this.transferLog.updateMinMax(money);
        }
    }

    public Statis getDepositLog() {
        return depositLog;
    }

    public void setDepositLog(Statis depositLog) {
        this.depositLog = depositLog;
    }

    public Statis getWithdrawLog() {
        return withdrawLog;
    }

    public void setWithdrawLog(Statis withdrawLog) {
        this.withdrawLog = withdrawLog;
    }

    public Statis getTransferLog() {
        return transferLog;
    }

    public void setTransferLog(Statis transferLog) {
        this.transferLog = transferLog;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getAccountNumber() {
        return AccountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        AccountNumber = accountNumber;
    }

    @Override
    public String toString() {
        return "Account{" +
                "AccountNumber='" + AccountNumber + '\'' +
                ", balance=" + balance +
                ", latestTrade=" + latestTrade +
                ", depositLog=" + depositLog +
                ", withdrawLog=" + withdrawLog +
                ", transferLog=" + transferLog +
                '}';
    }
}