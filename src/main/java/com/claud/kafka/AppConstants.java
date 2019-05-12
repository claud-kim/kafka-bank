package com.claud.kafka;

import java.util.Random;

final public class AppConstants {
    public static final String BOOTSTRAP_SERVERS = "localhost:9092";
    public static final String TOPIC_NAME = "test";

    // producer
    public static final int PRODUCER_NUM = 5;
    public static final int GEN_USER_SIZE = 50_000; //5_000; //

    public static final int GEN_SIZE_PER_SECOND = 1000; // 100

    // consumer
    public static final int CONSUMER_NUM_PER_THREAD = 1;
    public static final int CONSUMER_UPDATE_NUM = 100;


    public static final int INSERT_MONEY_MIN = 500;
    public static final int INSERT_MONEY_MAX = 1000;

    public static final String IN_GEN = "in_gen.txt";
    public static final String OUT_GEN = "out_gen.txt";

    public static int randomIntBetween(final Random random, final int min, int max) {
        return random.nextInt(max-min) + min;
    }

    public static int insertRandomRange(final Random random) {
        return randomIntBetween(random, INSERT_MONEY_MIN, INSERT_MONEY_MAX+1);
    }


}
