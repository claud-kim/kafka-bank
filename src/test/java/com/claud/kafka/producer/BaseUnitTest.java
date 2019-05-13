package com.claud.kafka.producer;

import com.claud.kafka.JsonUtil;
import com.claud.kafka.producer.vo.BaseVo;
import com.claud.kafka.producer.vo.log.SessionLog;
import com.google.gson.Gson;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BaseUnitTest {
    Gson gson = JsonUtil.gson();

    @Test
    public void testBaseVo() {
        BaseVo base = new SessionLog(0);
        String json = gson.toJson(base);

        SessionLog fromJson = gson.fromJson(json, SessionLog.class);
        assertEquals(fromJson.getLogtype(), base.getLogtype());
        assertEquals(fromJson.getTime().toString(), base.getTime().toString());

    }

}
