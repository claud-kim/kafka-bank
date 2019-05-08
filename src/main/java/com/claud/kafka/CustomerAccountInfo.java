package com.claud.kafka;

import com.claud.kafka.cosumer.Statis;
import com.claud.kafka.producer.vo.LogType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerAccountInfo {
    private Integer customerNumber;
    private int accountNumber;
    private String name;
    private String birthDay;
    private long money;

    private final static int LASTEST_NUM = 3;
    private List<String> latestTrade = new ArrayList<String>(LASTEST_NUM);

    private static Map<LogType, Statis> tradeStatis = new HashMap<LogType, Statis>(3);
    static {
        tradeStatis.put(LogType.DEPOSIT_LOG, new Statis());
        tradeStatis.put(LogType.WITHDRAW_LOG, new Statis());
        tradeStatis.put(LogType.TRANFER_LOG, new Statis());
    }

    public void addTrade(String trade){
        latestTrade.add(0,trade);
        if ( latestTrade.size() > LASTEST_NUM ) {
            latestTrade.remove(latestTrade.size()-1);
        }
    }

    public List<String> getLatestTrade() {
        return latestTrade;
    }
}
