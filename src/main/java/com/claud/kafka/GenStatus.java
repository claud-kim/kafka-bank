package com.claud.kafka;

import com.claud.kafka.producer.vo.ActionType;
import com.claud.kafka.producer.vo.BaseVo;
import com.claud.kafka.producer.vo.LogType;
import com.claud.kafka.producer.vo.SessionLog;

import javax.management.RuntimeOperationsException;
import javax.swing.*;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import static com.claud.kafka.producer.vo.LogType.JOIN_LOG;
import static com.claud.kafka.producer.vo.LogType.SESSION_LOG;

public class GenStatus {

    private Map<Integer, ActionType> customerActionStatus;
    private Random rand = new Random(System.currentTimeMillis());

    public GenStatus(int sizeCustomer, long seed){
        customerActionStatus = new ConcurrentHashMap<Integer, ActionType>(sizeCustomer);
    }



    public static ActionType tranferNextStatus(ActionType previousAction) throws RuntimeException {

        switch(previousAction) {
            case SESSION_NULL:
                return ActionType.JOIN;
            case JOIN:
                return ActionType.OPEN;
            case OPEN:
            case NORMAL:
                return ActionType.SESSION_ON;
            case SESSION_ON:
                return ActionType.NORMAL;
            default:
                throw new RuntimeException("Invalid entry");
        }
    }
}
