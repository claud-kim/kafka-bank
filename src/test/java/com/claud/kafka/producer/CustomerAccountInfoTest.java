package com.claud.kafka.producer;

import com.claud.kafka.CustomerAccountInfo;
import com.claud.kafka.producer.vo.SessionLog;
import com.google.gson.Gson;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerAccountInfoTest {
    private CustomerAccountInfo customerAccountInfo = new CustomerAccountInfo();
    private Gson gson = new Gson();

    @Test
    public void testLatestTrade() {

        SessionLog src = new SessionLog();

        String trade = gson.toJson(src);
        assertEquals(0, customerAccountInfo.getLatestTrade().size());

        customerAccountInfo.addTrade(trade);
        assertEquals(1, customerAccountInfo.getLatestTrade().size());

        customerAccountInfo.addTrade(trade);
        assertEquals(2, customerAccountInfo.getLatestTrade().size());

        customerAccountInfo.addTrade(trade);
        assertEquals(3, customerAccountInfo.getLatestTrade().size());

        customerAccountInfo.addTrade(trade);
        assertEquals(3, customerAccountInfo.getLatestTrade().size());
    }
}
