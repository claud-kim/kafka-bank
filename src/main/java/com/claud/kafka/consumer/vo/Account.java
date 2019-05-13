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

    private final static int LATEST_NUM = 3;

    @Expose
    private final List<String> latestTrade = new ArrayList<String>(LATEST_NUM);
    @Expose
    private Statistics depositLog;
    @Expose
    private Statistics withdrawLog;
    @Expose
    private Statistics transferLog;


    public Account() {
        this.balance = 0;
        this.depositLog = new Statistics();
        this.withdrawLog = new Statistics();
        this.transferLog = new Statistics();
    }

    public synchronized void addTrade(String trade) {
        latestTrade.add(0, trade);
        if (latestTrade.size() > LATEST_NUM) {
            latestTrade.remove(latestTrade.size() - 1);
        }
    }

    public List<String> getLatestTrade() {
        return latestTrade;
    }

    public synchronized void update(LogType logType, int money) {
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

    public Statistics getDepositLog() {
        return depositLog;
    }

    public void setDepositLog(Statistics depositLog) {
        this.depositLog = depositLog;
    }

    public Statistics getWithdrawLog() {
        return withdrawLog;
    }

    public void setWithdrawLog(Statistics withdrawLog) {
        this.withdrawLog = withdrawLog;
    }

    public Statistics getTransferLog() {
        return transferLog;
    }

    public void setTransferLog(Statistics transferLog) {
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