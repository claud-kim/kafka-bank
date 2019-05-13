package com.claud.kafka.producer.vo.send;

import com.claud.kafka.producer.vo.gen.GenAccountInfo;
import com.claud.kafka.producer.vo.log.*;

public class UserBankEvent {
    private LogKey logkey;
    private SessionLog sessionLog;
    private RegisterLog registerLog;
    private AccountOpenLog openingAccountLog;
    private DepositLog depositLog;
    private WithdrawLog withdrawLog;
    private TransferLog transferLog;

    public UserBankEvent(LogKey logkey, SessionLog sessionLog) {
        this.logkey = logkey;
        this.sessionLog = sessionLog;
    }

    public UserBankEvent(LogKey logkey, RegisterLog registerLog) {
        this.logkey = logkey;
        this.registerLog = registerLog;
    }

    public UserBankEvent(LogKey logkey, AccountOpenLog OpeningAccountLog) {
        this.logkey = logkey;
        this.openingAccountLog = OpeningAccountLog;
    }

    public UserBankEvent(LogKey logkey, DepositLog depositLog) {
        this.logkey = logkey;
        this.depositLog = depositLog;
    }

    public UserBankEvent(LogKey logkey, WithdrawLog withdrawLog) {
        this.logkey = logkey;
        this.withdrawLog = withdrawLog;
    }

    public UserBankEvent(LogKey logkey, TransferLog transferLog) {
        this.logkey = logkey;
        this.transferLog = transferLog;
    }

    public LogKey getLogkey() {
        return logkey;
    }

    public void setLogkey(LogKey logkey) {
        this.logkey = logkey;
    }

    public SessionLog getSessionLog() {
        return sessionLog;
    }

    public void setSessionLog(SessionLog sessionLog) {
        this.sessionLog = sessionLog;
    }

    public RegisterLog getRegisterLog() {
        return registerLog;
    }

    public void setRegisterLog(RegisterLog registerLog) {
        this.registerLog = registerLog;
    }

    public AccountOpenLog getOpeningAccountLog() {
        return openingAccountLog;
    }

    public void setOpeningAccountLog(AccountOpenLog openingAccountLog) {
        this.openingAccountLog = openingAccountLog;
    }

    public DepositLog getDepositLog() {
        return depositLog;
    }

    public void setDepositLog(DepositLog depositLog) {
        this.depositLog = depositLog;
    }

    public WithdrawLog getWithdrawLog() {
        return withdrawLog;
    }

    public void setWithdrawLog(WithdrawLog withdrawLog) {
        this.withdrawLog = withdrawLog;
    }

    public TransferLog getTransferLog() {
        return transferLog;
    }

    public void setTransferLog(TransferLog transferLog) {
        this.transferLog = transferLog;
    }

    @Override
    public String toString() {
        return "UserBankEvent{" +
                "logkey=" + logkey +
                ", sessionLog=" + sessionLog +
                ", registerLog=" + registerLog +
                ", openingAccountLog=" + openingAccountLog +
                ", depositLog=" + depositLog +
                ", withdrawLog=" + withdrawLog +
                ", transferLog=" + transferLog +
                '}';
    }

    public String toJson() {
        return GenAccountInfo.getGson().toJson(this);
    }
}
