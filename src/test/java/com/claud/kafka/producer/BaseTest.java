package com.claud.kafka.producer;

import com.claud.kafka.JsonUtil;
import com.claud.kafka.producer.vo.BaseVo;
import com.google.gson.Gson;
import org.junit.Test;

import static org.junit.Assert.*;

public class BaseTest {
    Gson gson = JsonUtil.gson();

    @Test
    public void testStockPrice() {
        BaseVo base = new BaseVo();
        String json = gson.toJson(base);

        BaseVo fromJson = gson.fromJson(json, BaseVo.class);
        assertEquals(fromJson.getLogtype(), base.getLogtype());
        assertEquals(fromJson.getTime(), base.getTime());
    }

}
