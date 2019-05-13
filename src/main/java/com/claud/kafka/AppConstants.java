package com.claud.kafka;

import org.javasimon.Simon;
import org.javasimon.SimonManager;
import org.javasimon.Stopwatch;

import java.util.*;

final public class AppConstants {

    private AppConstants() {
    }

    public static final String BOOTSTRAP_SERVERS = "localhost:9092";
    public static final String TOPIC_NAME = "test";

    // producer
    public static final int PRODUCER_NUM = 5;
    public static final int GEN_USER_SIZE = 50_000; //500; //

    public static final int GEN_SIZE_PER_SECOND = 1_000; // 100

    // consumer
    public static final int CONSUMER_NUM_PER_THREAD = 3;
    public static final int CONSUMER_UPDATE_NUM = 100;


    public static final int INSERT_MONEY_MIN = 500;
    public static final int INSERT_MONEY_MAX = 1_000;

    public static final String IN_GEN = "in_gen.txt";
    public static final String OUT_GEN = "out_gen.txt";

    public static int randomIntBetween(final Random random, final int min, int max) {
        return random.nextInt(max - min) + min;
    }

    public static int insertRandomRange(final Random random) {
        return randomIntBetween(random, INSERT_MONEY_MIN, INSERT_MONEY_MAX + 1);
    }

    public static Map<String, String> getProfiler() {
        Map<String, String> retval = new TreeMap<>();

        // Configuration
        retval.put("BOOTSTRAP_SERVERS", BOOTSTRAP_SERVERS);
        retval.put("TOPIC_NAME", TOPIC_NAME);
        retval.put("PRODUCER_NUM", Integer.toString(PRODUCER_NUM));
        retval.put("GEN_USER_SIZE", Integer.toString(GEN_USER_SIZE));
        retval.put("GEN_SIZE_PER_SECOND", Integer.toString(GEN_SIZE_PER_SECOND));
        retval.put("CONSUMER_NUM_PER_THREAD", Integer.toString(CONSUMER_NUM_PER_THREAD));
        retval.put("CONSUMER_UPDATE_NUM", Integer.toString(CONSUMER_UPDATE_NUM));
        retval.put("INSERT_MONEY_MIN", Integer.toString(INSERT_MONEY_MIN));
        retval.put("INSERT_MONEY_MAX", Integer.toString(INSERT_MONEY_MAX));
        retval.put("IN_GEN", IN_GEN);
        retval.put("OUT_GEN", OUT_GEN);

        Map<String, Simon> simons = new TreeMap<>();
        findLeafNodes(simons, SimonManager.getRootSimon());

        StringBuffer buffer = new StringBuffer('[');
        double totalEventTime = 0.0;
        long totalEventCnt = 0L;
        for (Simon simon : simons.values()) {
            if (!Stopwatch.class.isInstance(simon)){
                continue;
            }

            Stopwatch stopwatch = (Stopwatch)simon;
            buffer.append(toStringStopWatch(stopwatch)).append(",");
            String name = simon.getName();

            if (name.startsWith("svc.")) {
                long reqCnt = stopwatch.getCounter();
                retval.put(name + "REG.CNT", Long.toString(reqCnt));

                long respTime = stopwatch.getTotal();
                retval.put(name + "RESP.TIME", Long.toString(respTime));

            } else if (name.startsWith("consumer.")) {

                long reqCnt = stopwatch.getCounter();
                retval.put(name + "REG.CNT", Long.toString(reqCnt));
                long respTime = stopwatch.getTotal();
                retval.put(name + "RESP.TIME", Long.toString(respTime));

                totalEventCnt += reqCnt;
                totalEventTime += respTime;

            } else if (name.startsWith("producer.")) {

                long reqCnt = stopwatch.getCounter();
                retval.put(name + "REG.CNT", Long.toString(reqCnt));
                long respTime = stopwatch.getTotal();
                retval.put(name + "RESP.TIME", Long.toString(respTime));

                totalEventCnt += reqCnt;
                totalEventTime += respTime;

            }
        }
        buffer.append(']');

        retval.put("totalEventCnt", Long.toString(totalEventCnt));
        retval.put("totalEventTime", getMillis(totalEventTime));
        retval.put("METRIC", buffer.toString());


        return retval;
    }

    public static void printPrettyMap() {
        for (Map.Entry<String, String> entry : AppConstants.getProfiler().entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }

    private static String toStringStopWatch(Stopwatch stopwatch) {
        Map<String, String> retMap = new HashMap<>();
        retMap.put("name", stopwatch.getName());
        retMap.put("tot", getMillis(stopwatch.getTotal()));
        retMap.put("cnt", Long.toString(stopwatch.getCounter()));
        retMap.put("max", getMillis(stopwatch.getMax()));
        retMap.put("min", getMillis(stopwatch.getMin()));
        retMap.put("avg", getMillis(stopwatch.getMean()));

        return retMap.toString();
    }

    private static String getMillis(double respTime) {
        return String.format("%.1f", respTime * 0.000_001);
    }

    private static void findLeafNodes(Map<String, Simon> retMap, Simon rootSimon) {
        List<Simon> children = rootSimon.getChildren();
        if (children == null || children.isEmpty()) {
            retMap.put(rootSimon.getName(), rootSimon);
            return;
        }

        for (Simon child : children) {
            findLeafNodes(retMap, child);
        }
    }

}
