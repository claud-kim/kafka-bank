package com.claud.kafka.consumer.rest;

import com.claud.kafka.consumer.vo.CustomerAccountInfo;
import com.claud.kafka.consumer.ManagerCustomer;

import java.util.ArrayList;
import java.util.List;

public class UserService {

    public List<CustomerAccountInfo> getAllUsers() {
        return new ArrayList<>(ManagerCustomer.getInstance().listAll());
    }

    public CustomerAccountInfo getUser(String id) {

        return ManagerCustomer.getInstance().getRestUser(Integer.parseInt(id));
    }


}
