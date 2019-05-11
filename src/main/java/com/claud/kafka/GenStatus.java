package com.claud.kafka;

import com.claud.kafka.producer.vo.gen.GenAccountInfo;
import com.claud.kafka.producer.vo.send.UserBankEvent;
import com.google.gson.Gson;

import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class GenStatus {

    private Map<Integer, GenAccountInfo> customerActionStatus;
    private int userTotalSize;


    private static class SingletonHolder {
        static final GenStatus INSTANCE = new GenStatus();
    }

    private GenStatus() {
    }

    public static GenStatus getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void init(final int userTotalSize) {
        this.userTotalSize = userTotalSize;
        this.customerActionStatus = new ConcurrentHashMap<>(userTotalSize);
    }
    
    public int getUserTotalSize() {
        return this.userTotalSize;
    }

    public GenAccountInfo getGenAccountInfo(Integer userNumber) {
        return this.customerActionStatus.get(userNumber);
    }

    public void putCustomActionStatus(Integer userNumber, GenAccountInfo genAccountInfo) {
        this.customerActionStatus.put(userNumber, genAccountInfo);
    }


    public String printSummary() {
        StringBuffer buffer = new StringBuffer();

        for (GenAccountInfo info : this.customerActionStatus.values()) {
            buffer.append(info.getUserNumber() + ":" + info.getMoney() + "\n");
        }

        return buffer.toString();
    }

    @Override
    public String toString() {
        return "GenStatus{" +
                "customerActionStatus=" + customerActionStatus +
                '}';
    }
}
