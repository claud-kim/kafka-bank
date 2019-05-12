package com.claud.kafka.producer;

import com.claud.kafka.JsonUtil;
import com.claud.kafka.consumer.vo.UserProfile;
import com.claud.kafka.producer.vo.log.LogType;
import com.claud.kafka.producer.vo.log.SessionLog;
import com.google.gson.Gson;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserProfileUnitTest {
    private UserProfile userProfile = new UserProfile(null);
    private Gson gson = JsonUtil.gson();

    @Test
    public void testLatestTrade() {

        SessionLog src = new SessionLog(LogType.SESSION_LOG, null);

        String trade = gson.toJson(src);
        assertEquals(0, userProfile.getAccount().getLatestTrade().size());

        userProfile.addTrade(trade);
        assertEquals(1, userProfile.getAccount().getLatestTrade().size());

        userProfile.addTrade(trade);
        assertEquals(2, userProfile.getAccount().getLatestTrade().size());

        userProfile.addTrade(trade);
        assertEquals(3, userProfile.getAccount().getLatestTrade().size());

        userProfile.addTrade(trade);
        assertEquals(3, userProfile.getAccount().getLatestTrade().size());
    }
}
