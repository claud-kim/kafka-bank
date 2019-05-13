package com.claud.kafka.consumer.rest;

import com.claud.kafka.GenStopwatch;
import com.claud.kafka.consumer.ManagerCustomer;
import com.claud.kafka.consumer.vo.UserProfile;

import java.util.ArrayList;
import java.util.List;

public class UserService extends GenStopwatch {

    public List<UserProfile> getAllUsers() {
        return new ArrayList<>(ManagerCustomer.getInstance().listAll());
    }

    public UserProfile getUser(String id) {
        return ManagerCustomer.getInstance().getRestUser(Integer.parseInt(id));
    }


    @Override
    public String getStopWatchName(String methodName) {
        return String.format("%s%s", getServicePrefix(), methodName.substring(methodName.lastIndexOf('.') + 1));
    }

    @Override
    public String getServicePrefix() {
        return "svc.";
    }

}
