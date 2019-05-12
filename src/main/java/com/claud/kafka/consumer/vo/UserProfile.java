package com.claud.kafka.consumer.vo;

import com.claud.kafka.consumer.vo.Account;
import com.claud.kafka.consumer.vo.Customer;
import com.claud.kafka.producer.vo.log.LogType;
import com.google.gson.annotations.Expose;

public class UserProfile {
    @Expose
    private Customer customer;
    @Expose
    private Account account;

    public UserProfile(Integer customerNumber) {
        this.customer = new Customer(customerNumber);
        this.account = new Account();
    }

    public void addTrade(String trade) {
        account.addTrade(trade);
    }

    public void update(LogType logType, int money) {

        this.account.update(logType, money);
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "customer=" + customer +
                ", account=" + account +
                '}';
    }
}
