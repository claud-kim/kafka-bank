package com.claud.kafka.consumer;

import com.claud.kafka.AppConstants;
import com.claud.kafka.GenStopwatch;
import com.claud.kafka.consumer.vo.UserProfile;
import com.claud.kafka.producer.vo.log.LogType;
import com.claud.kafka.producer.vo.send.LogKey;
import com.claud.kafka.producer.vo.send.UserBankEvent;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.TopicPartition;
import org.javasimon.SimonManager;
import org.javasimon.Split;
import org.javasimon.Stopwatch;
import org.javasimon.utils.SimonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.claud.kafka.producer.vo.log.LogType.SESSION_LOG;

public class ConsumerRunnable extends GenStopwatch implements Runnable {
    private static final Logger logger =
            LoggerFactory.getLogger(ConsumerRunnable.class);

    private final Consumer<LogKey, String> consumer;
    private final int readCountStatusUpdate;
    private final int threadIndex;
    private final AtomicBoolean stopAll;
    private final TopicPartition topicPartition;
    private boolean running = true;
    private final ManagerCustomer managerCustomer;
    private int printEvent = 100;


    private Map<TopicPartition, BlockingQueue<ConsumerRecord>> commitQueueMap = new ConcurrentHashMap<>();
    private final ExecutorService threadPool;

    public ConsumerRunnable(final Consumer<LogKey, String> consumer,
                            final int readCountStatusUpdate,
                            final int threadIndex,
                            final AtomicBoolean stopAll,
                            final int numWorkers) {
        this.consumer = consumer;
        this.readCountStatusUpdate = readCountStatusUpdate;
        this.threadIndex = threadIndex;
        this.stopAll = stopAll;
        this.threadPool = Executors.newFixedThreadPool(numWorkers);
        this.topicPartition = new TopicPartition(AppConstants.TOPIC_NAME, threadIndex);
        this.managerCustomer = ManagerCustomer.getInstance();
    }


    public synchronized boolean isRunning() {
        return running;
    }

    public synchronized void setRunning(boolean running) {
        this.running = running;
    }

    private void runConsumer() throws Exception {
        consumer.assign(Collections.singleton(topicPartition));

        int readCount = 0;
        while (isRunning()) {
            pollRecordsAndProcess(readCount);
            readCount++;
        }

    }

    private void pollRecordsAndProcess(
            final int readCount) throws Exception {

        final ConsumerRecords<LogKey, String> consumerRecords = consumer.poll(Duration.ofMillis(100));

        if (consumerRecords.count() == 0) {
            if (stopAll.get()) {
                this.setRunning(false);
            }
            return;
        }

        for (ConsumerRecord<LogKey, String> record : consumerRecords) {
            Stopwatch stopwatch = SimonManager.getStopwatch(getStopWatchName(SimonUtils.generateName()));
            Split split = stopwatch.start();

            processRecord(record);

            split.stop();
        }

        processCommits(consumer);

        if (readCount % readCountStatusUpdate == 0) {
            displayRecordsStats(consumerRecords);
        }
    }


    private void displayRecordsStats(
            final ConsumerRecords<LogKey, String> consumerRecords) {

        logger.info("New ConsumerRecords par count {} count {}, max offset ",
                consumerRecords.partitions().size(),
                consumerRecords.count());

        /*currentMap.forEach((s, value) ->
                    logger.info("key {} value {} Thread {}",
                            s, value,
                            threadIndex)
                );*/
    }

    @Override
    public void run() {
        try {
            runConsumer();
        } catch (Exception ex) {
            logger.error("Run Consumer Exited with", ex);
            throw new RuntimeException(ex);
        }
    }

    private void processCommits(Consumer<LogKey, String> consumer) {

        consumer.commitSync();

    }

    private void processRecord(ConsumerRecord<LogKey, String> record) {
        // DB sync logic

        //UserProfile

        LogKey logKey = record.key();
        logger.debug("{} {} {} {} {} ",
                record.topic(), record.partition(), record.offset(),
                record.key(), record.value());

        UserBankEvent userBankEvent = managerCustomer.getGson().fromJson(record.value(), UserBankEvent.class);
        Integer userNumber = logKey.getCustomerNumber();
        if (SESSION_LOG.equals(logKey.getLogType())) {
            if (userNumber == null) {
                logger.debug("skip {} {}", logKey, userBankEvent);
                return;
            }
        }

        UserProfile customer = managerCustomer.getCustomerAccountInfo(userNumber);
        // add value json
        customer.addTrade(record.value());

        // update info
        // Open, Join

        LogType logtype = logKey.getLogType();

        printEvent++;
        if (printEvent == Integer.MAX_VALUE) {
            printEvent = 0;
        }

        if (LogType.DEPOSIT_LOG.equals(logtype)) {
            // money update
            int money = userBankEvent.getDepositLog().getInsertMoney();
            customer.update(logKey.getLogType(), money);
            logger.debug("===== {} {}", customer, userBankEvent);
        } else if (LogType.WITHDRAW_LOG.equals(logtype)) {
            // money update
            int money = userBankEvent.getWithdrawLog().getOutputMoney();
            customer.update(logKey.getLogType(), money);
            logger.debug("===== {} {}", customer, userBankEvent);
        } else if (LogType.TRANSFER_LOG.equals(logtype)) {
            // money update
            int money = userBankEvent.getTransferLog().getOutputMoney();
            customer.update(logKey.getLogType(), money);
            logger.debug("===== {} {}", customer, userBankEvent);
        } else if (LogType.REGISTER_LOG.equals(logtype)) {
            // info update
            String birthDay = userBankEvent.getRegisterLog().getBirthDay();
            String name = userBankEvent.getRegisterLog().getUserName();
            Date time = userBankEvent.getRegisterLog().getTime();
            int customerNumber = userBankEvent.getRegisterLog().getUserNumber();
            customer.getCustomer().setCustomerName(name);
            customer.getCustomer().setBirthDay(birthDay);
            customer.getCustomer().setCustomerNumber(customerNumber);
            // 가입 일시 등록
            customer.getCustomer().setRegisterTime(time);
            logger.debug("===== {} {}", customer, userBankEvent);

        } else if (LogType.ACCOUNT_OPEN_LOG.equals(logtype)) {
            String accountNumber = userBankEvent.getOpeningAccountLog().getAccountNumber();
            customer.getAccount().setAccountNumber(accountNumber);
            logger.debug("===== {} {}", customer, userBankEvent);

        } else if (SESSION_LOG.equals(logtype)) {
            // SESSION_LOG updateSessionCnt
            customer.getCustomer().updateSessionCnt();
            logger.debug("===== {} {}", customer, userBankEvent);
        }

        if (printEvent % 1000 == 0) {
            logger.info("===== {} {}", customer, userBankEvent);
        }
    }


    @Override
    public String getStopWatchName(String methodName) {
        return String.format("%s%s", getServicePrefix(),
                methodName.substring(methodName.lastIndexOf('.') + 1));
    }

    @Override
    public String getServicePrefix() {
        return "consumer.";
    }
}