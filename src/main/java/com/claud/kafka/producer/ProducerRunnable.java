package com.claud.kafka.producer;

import com.claud.kafka.AppConstants;
import com.claud.kafka.GenStatus;
import com.claud.kafka.GenStopwatch;
import com.claud.kafka.producer.vo.gen.GenAccountInfo;
import com.claud.kafka.producer.vo.log.ActionType;
import com.claud.kafka.producer.vo.log.LogType;
import com.claud.kafka.producer.vo.send.LogKey;
import com.claud.kafka.producer.vo.send.UserBankEvent;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.javasimon.SimonManager;
import org.javasimon.Split;
import org.javasimon.Stopwatch;
import org.javasimon.utils.SimonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ProducerRunnable extends GenStopwatch implements Runnable {

    private static final Logger logger =
            LoggerFactory.getLogger(ProducerRunnable.class);

    private final int numLogEventPerSecond;
    private final int userNumStart;
    private final Random random;
    private final int userNumEnd;
    private long currentTime;
    private final GenStatus status;
    private final String topic;

    private StringBuffer buffer;

    private final Producer<LogKey, String> producer;


    public ProducerRunnable(String topic, int userNumStart, int userNumEnd, GenStatus status, Producer<LogKey, String> producer) {

        this.topic = topic;
        this.numLogEventPerSecond = AppConstants.GEN_SIZE_PER_SECOND / AppConstants.PRODUCER_NUM;
        this.userNumStart = userNumStart;
        this.userNumEnd = userNumEnd;
        this.status = status;
        long currentTime = System.currentTimeMillis() + userNumStart;
        this.random = new Random(currentTime);
        this.currentTime = currentTime / 1000;
        this.producer = producer;
    }

    @Override
    public void run() {

        while (true) {

            // gen now time create user event
            buffer = new StringBuffer();

            List<UserBankEvent> events = new ArrayList<>();
            for (int i = 0; i < numLogEventPerSecond; i++) {
                UserBankEvent event = createRandomUserEvent();
                events.add(event);
                //logger.debug("gen {} {}", i, event.toJson());
            }


            buffer.append("sender size=" + events.size());
            // sender

            Future<RecordMetadata> last = null;
            ProducerRecord<LogKey, String> record = null;
            for (UserBankEvent event : events) {

                Stopwatch stopwatch = SimonManager.getStopwatch(getStopWatchName(SimonUtils.generateName()));
                Split split = stopwatch.start();

                record = new ProducerRecord<>(topic, event.getLogkey(), event.toJson());
                last = producer.send(record);

                split.stop();
            }

            //producer.flush();

            //sleep next time
            try {
                displayRecordMetaData(record, last);
                while (!nextTime()) {
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                if (Thread.interrupted()) {
                    // closes producer
                    producer.close();
                    break;
                }
            } catch (ExecutionException e) {
                logger.error("problem sending record to producer", e);
            }


        }
    }


    public static int randomIntBetween(final Random random, final int min, int max) {
        return random.nextInt(max - min) + min;
    }

    private UserBankEvent createRandomUserEvent() {
        UserBankEvent userBankEvent = null;

        int selectUserNumber = randomIntBetween(this.random, this.userNumStart, this.userNumEnd);

        GenAccountInfo selectGenAccountInfo = this.status.getGenAccountInfo(selectUserNumber);

        if (selectGenAccountInfo == null) {
            selectGenAccountInfo = new GenAccountInfo(selectUserNumber, genAccount(selectUserNumber), genName(selectUserNumber), genBirthDay(),
                    0, ActionType.SESSION_NULL, LogType.SESSION_LOG);

            this.status.putCustomActionStatus(selectUserNumber, selectGenAccountInfo);
        }

        userBankEvent = selectGenAccountInfo.genUserBankEvent(this.random);

        //logger.debug("user status {}", select);

        return userBankEvent;
    }

    private void displayRecordMetaData(final ProducerRecord<LogKey, String> record,
                                       final Future<RecordMetadata> future)
            throws InterruptedException, ExecutionException {

        final RecordMetadata recordMetadata = future.get();

        logger.info(String.format("\n\t\t\tkey=%s, value=%s " +
                        "\n\t\t\tsent to topic=%s part=%d off=%d at time=%s",
                record.key().toString(),
                record.value(),
                recordMetadata.topic(),
                recordMetadata.partition(),
                recordMetadata.offset(),
                new Date(recordMetadata.timestamp())
        ));
    }

    private String genBirthDay() {
        return "19811009";
    }

    private String genName(int userNumber) {
        return userNumber + "_name";
    }

    private String genAccount(int userNumber) {
        return userNumber + "-1";
    }

    private boolean nextTime() {
        long checkNext = System.currentTimeMillis() / 1000;
        if (currentTime != checkNext) {
            buffer.append(", nextTime=" + checkNext);
            logger.info("{}", buffer);
            currentTime = checkNext;
            return true;
        }

        return false;
    }


    @Override
    public String getStopWatchName(String methodName) {
        return String.format("%s%s", getServicePrefix(),
                methodName.substring(methodName.lastIndexOf('.') + 1));
    }

    @Override
    public String getServicePrefix() {
        return "producer.";
    }
}
