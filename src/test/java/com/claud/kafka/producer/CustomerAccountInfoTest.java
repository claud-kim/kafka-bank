package com.claud.kafka.producer;

import com.claud.kafka.JsonUtil;
import com.claud.kafka.consumer.vo.CustomerAccountInfo;
import com.claud.kafka.producer.vo.log.LogType;
import com.claud.kafka.producer.vo.log.SessionLog;
import com.google.gson.Gson;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerAccountInfoTest {
    private CustomerAccountInfo customerAccountInfo = new CustomerAccountInfo(null);
    private Gson gson = JsonUtil.gson();

    @Test
    public void testLatestTrade() {

        SessionLog src = new SessionLog(LogType.SESSION_LOG, null);

        String trade = gson.toJson(src);
        assertEquals(0, customerAccountInfo.getAccount().getLatestTrade().size());

        customerAccountInfo.addTrade(trade);
        assertEquals(1, customerAccountInfo.getAccount().getLatestTrade().size());

        customerAccountInfo.addTrade(trade);
        assertEquals(2, customerAccountInfo.getAccount().getLatestTrade().size());

        customerAccountInfo.addTrade(trade);
        assertEquals(3, customerAccountInfo.getAccount().getLatestTrade().size());

        customerAccountInfo.addTrade(trade);
        assertEquals(3, customerAccountInfo.getAccount().getLatestTrade().size());
    }
}
