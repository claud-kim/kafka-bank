package com.claud.kafka.producer.vo.gen;

import com.claud.kafka.AppConstants;
import com.claud.kafka.JsonUtil;
import com.claud.kafka.producer.vo.log.*;
import com.claud.kafka.producer.vo.send.LogKey;
import com.claud.kafka.producer.vo.send.UserBankEvent;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenAccountInfo extends AccountInfo {
    private ActionType currentActionType;
    private LogType currentLogType;
    private static Gson gson = JsonUtil.gson();

    private static final Logger logger =
            LoggerFactory.getLogger(GenAccountInfo.class);

    private static List<DestInfo> destInfos;

    static {
        destInfos = new ArrayList<>();
        destInfos.add(new DestInfo("hana bank", "hana-1", "name1"));
        destInfos.add(new DestInfo("woori bank", "woori-1", "name2"));

    }

    public GenAccountInfo(int customerNumber, String accountNumber, String name, String birthDay, int money,
                          ActionType currentActionType, LogType currentLogType) {
        super(customerNumber, accountNumber, name, birthDay, money);
        this.currentActionType = currentActionType;
        this.currentLogType = currentLogType;
    }

    public static Gson getGson() {
        return gson;
    }

    public ActionType getCurrentActionType() {
        return currentActionType;
    }

    public void setCurrentActionType(ActionType currentActionType) {
        this.currentActionType = currentActionType;
    }


    public LogKey getLogKey() {
        LogKey logkey = new LogKey(this.currentLogType, this.getUserNumber());

        return logkey;
    }

    public ActionType transferNextStatus(final ActionType previousAction) throws RuntimeException {

        switch (previousAction) {
            case SESSION_NULL:
                return ActionType.REGISTER;
            case REGISTER:
                return ActionType.ACCOUNT_OPEN;
            case ACCOUNT_OPEN:
            case NORMAL:
                return ActionType.SESSION_ON;
            case SESSION_ON:
                return ActionType.NORMAL;
            default:
                return ActionType.SESSION_NULL;
        }
    }

    public UserBankEvent genUserBankEvent(final Random random) {

        UserBankEvent event = null;

        ActionType now = this.currentActionType;
        this.currentActionType = transferNextStatus(now);
        //logger.debug("now {} next {}", now, this.currentActionType);

        if (ActionType.SESSION_NULL.equals(now)) {
            currentLogType = LogType.SESSION_LOG;
            event = new UserBankEvent(this.getLogKey(), new SessionLog(null));
            return event;
        } else if (ActionType.SESSION_ON.equals(now)) {
            currentLogType = LogType.SESSION_LOG;
            event = new UserBankEvent(this.getLogKey(), new SessionLog(this.getUserNumber()));
            return event;
        } else if (ActionType.REGISTER.equals(now)) {
            currentLogType = LogType.REGISTER_LOG;
            event = new UserBankEvent(this.getLogKey(), new RegisterLog(this.getUserNumber(), this.getName(), this.getBirthDay()));
            return event;
        } else if (ActionType.ACCOUNT_OPEN.equals(now)) {
            currentLogType = LogType.ACCOUNT_OPEN_LOG;
            event = new UserBankEvent(this.getLogKey(), new AccountOpenLog(this.getUserNumber(), this.getAccountNumber()));
            return event;
        }

        // rand % 3
        if (this.getMoney() == 0) {
            // depositLog
            int insertMoney = AppConstants.insertRandomRange(random);
            this.updatePlusMoney(insertMoney);
            currentLogType = LogType.DEPOSIT_LOG;
            event = new UserBankEvent(this.getLogKey(), new DepositLog(this.getUserNumber(), this.getAccountNumber(), insertMoney));
            return event;
        } else {
            // rand % 3
            int num = AppConstants.randomIntBetween(random, 0, 3);
            if (0 == num) {
                // depositLog
                int insertMoney = AppConstants.insertRandomRange(random);
                updatePlusMoney(insertMoney);
                currentLogType = LogType.DEPOSIT_LOG;
                event = new UserBankEvent(this.getLogKey(), new DepositLog(this.getUserNumber(), this.getAccountNumber(), insertMoney));
                return event;
            } else if (1 == num) {
                // withdrawLog
                currentLogType = LogType.WITHDRAW_LOG;
                int outMoney = AppConstants.randomIntBetween(random, 1, this.getMoney() + 1);
                updateMinusMoney(outMoney);
                event = new UserBankEvent(this.getLogKey(), new WithdrawLog(this.getUserNumber(), this.getAccountNumber(), outMoney));
                return event;
            } else {
                // transfer
                int outMoney = AppConstants.randomIntBetween(random, 1, this.getMoney() + 1);
                updateMinusMoney(outMoney);
                int select = AppConstants.randomIntBetween(random, 0, destInfos.size());
                DestInfo selectDest = destInfos.get(select);

                currentLogType = LogType.TRANSFER_LOG;
                event = new UserBankEvent(this.getLogKey(), new TransferLog(this.getUserNumber(), this.getAccountNumber(), selectDest.getDeskBank(),
                        selectDest.getDeskAccountNumber(), selectDest.getDestAccountName(), outMoney));
                return event;
            }

        }

    }

    @Override
    public String toString() {
        return "GenAccountInfo{" +
                "userNumber=" + super.getUserNumber() +
                ", accountNumber=" + super.getAccountNumber() +
                ", name=" + super.getName() +
                ", birthDay=" + super.getBirthDay() +
                ", money=" + super.getMoney() +
                ", currentActionType=" + currentActionType +
                ", currentLogType=" + currentLogType +
                '}';
    }
}
