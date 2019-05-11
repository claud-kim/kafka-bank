package com.claud.kafka;

import com.claud.kafka.producer.vo.send.LogKey;
import com.google.gson.Gson;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class LogKeySerialize implements Serializer<LogKey> {
    private final Gson gson = JsonUtil.gson();

    @Override
    public void configure(Map<String, ?> map, boolean isKey) {

    }

    @Override
    public byte[] serialize(String s, LogKey logKey) {
        return gson.toJson(logKey).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public void close() {

    }
}
