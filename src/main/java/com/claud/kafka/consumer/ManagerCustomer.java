package com.claud.kafka.consumer;

import com.claud.kafka.JsonUtil;
import com.claud.kafka.consumer.vo.CustomerAccountInfo;
import com.google.gson.Gson;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ManagerCustomer {

    private Map<Integer, CustomerAccountInfo> allCustomer = new ConcurrentHashMap<>(50000);
    private Gson gson = JsonUtil.gson();

    private static class SingletonHolder {
        static final ManagerCustomer INSTANCE = new ManagerCustomer();
    }

    private ManagerCustomer() {
    }

    public static ManagerCustomer getInstance() {
        return ManagerCustomer.SingletonHolder.INSTANCE;
    }

    public Collection<CustomerAccountInfo> listAll() {
        return this.allCustomer.values();
    }

    public CustomerAccountInfo getRestUser(int userNumber) {
        return this.allCustomer.get(userNumber);
    }


    public CustomerAccountInfo getCustomerAccountInfo(Integer userNumber) {
        if (allCustomer.get(userNumber) == null) {
            allCustomer.put(userNumber, new CustomerAccountInfo(userNumber));
        }

        return this.allCustomer.get(userNumber);
    }

    public Gson getGson() {
        return this.gson;
    }

    public String printSummary() {
        StringBuffer buffer = new StringBuffer();
        for (CustomerAccountInfo info : allCustomer.values()) {
            buffer.append(info.getCustomer().getCustomerNumber() + ":" + info.getAccount().getBalance() + "\n");
        }

        return buffer.toString();
    }

}
