package com.claud.kafka;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import spark.ResponseTransformer;

public class JsonUtil {

    public static String toRestJson(Object object) {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .serializeNulls()
                .create()
                .toJson(object);
    }

    public static ResponseTransformer restJson() {
        return JsonUtil::toRestJson;
    }


    public static Gson gson() {
        return new GsonBuilder()
                .create();
    }
}