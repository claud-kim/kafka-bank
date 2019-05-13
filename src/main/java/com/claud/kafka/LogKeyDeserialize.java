package com.claud.kafka;

import com.claud.kafka.producer.vo.send.LogKey;
import com.google.gson.Gson;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class LogKeyDeserialize implements Deserializer<LogKey> {
    private final Gson gson = JsonUtil.gson();

    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public LogKey deserialize(String s, byte[] bytes) {
        LogKey logKey = gson.fromJson(new String(bytes, StandardCharsets.UTF_8), LogKey.class);

        return logKey;
    }

    @Override
    public void close() {

    }
}
