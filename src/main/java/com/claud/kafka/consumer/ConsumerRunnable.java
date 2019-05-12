package com.claud.kafka.consumer;

import com.claud.kafka.AppConstants;
import com.claud.kafka.consumer.vo.UserProfile;
import com.claud.kafka.producer.vo.log.LogType;
import com.claud.kafka.producer.vo.send.LogKey;
import com.claud.kafka.producer.vo.send.UserBankEvent;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.claud.kafka.producer.vo.log.LogType.SESSION_LOG;

public class ConsumerRunnable implements Runnable {
    private static final Logger logger =
            LoggerFactory.getLogger(ConsumerRunnable.class);

    private final Consumer<LogKey, String> consumer;
    private final int readCountStatusUpdate;
    private final int threadIndex;
    private final AtomicBoolean stopAll;
    private final TopicPartition topicPartition;
    private boolean running = true;
    private final ManagerCustomer managerCustomer;


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

        final Map<LogKey, String> lastRecordPerStock = new ConcurrentHashMap<>();

        int readCount = 0;
        while (isRunning()) {
            pollRecordsAndProcess(lastRecordPerStock, readCount);
            readCount++;
        }

    }

    private void pollRecordsAndProcess(
            final Map<LogKey, String> current,
            final int readCount) throws Exception {

        final ConsumerRecords<LogKey, String> consumerRecords = consumer.poll(Duration.ofMillis(100));

        if (consumerRecords.count() == 0) {
            if (stopAll.get()) {
                this.setRunning(false);
            }
            return;
        }

        for (ConsumerRecord<LogKey, String> record : consumerRecords) {

            processRecord(record);
        }

        processCommits(consumer);

        if (readCount % readCountStatusUpdate == 0) {
            displayRecordsStats(current, consumerRecords);
        }
    }


    private void displayRecordsStats(
            final Map<LogKey, String> currentMap,
            final ConsumerRecords<LogKey, String> consumerRecords) {

        System.out.printf("New ConsumerRecords par count %d count %d, max offset\n",
                consumerRecords.partitions().size(),
                consumerRecords.count());
        currentMap.forEach((s, value) ->
                System.out.printf("key %s value %s Thread %d\n",
                        s.toString(), value,
                        threadIndex));
        System.out.println();
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
        //SAVE TO DB

        //UserProfile

        LogKey logKey = record.key();
        logger.info("{} {} {} {} {} ",
                record.topic(), record.partition(), record.offset(),
                record.key(), record.value());

        UserBankEvent userBankEvent = managerCustomer.getGson().fromJson(record.value(), UserBankEvent.class);
        Integer userNumber = logKey.getCustomerNumber();
        if (SESSION_LOG.equals(logKey.getLogType())) {
            if (userNumber == null) {
                logger.info(" skip {} {}", logKey, userBankEvent);
                return;
            }
        }

        UserProfile customer = managerCustomer.getCustomerAccountInfo(userNumber);
        // add value json
        customer.addTrade(record.value());

        // update info
        // Open, Join

        LogType logtype = logKey.getLogType();
        long money = 0L;
        if (LogType.DEPOSIT_LOG.equals(logtype)) {
            // money update
            money = userBankEvent.getDepositLog().getInsertMoney();
            customer.update(logKey.getLogType(), (int) money);
            logger.info("===== {} {}", customer, userBankEvent);
        } else if (LogType.WITHDRAW_LOG.equals(logtype)) {
            // money update
            money = userBankEvent.getWithdrawLog().getOutputMoney();
            customer.update(logKey.getLogType(), (int) money);
            logger.info("===== {} {}", customer, userBankEvent);
        } else if (LogType.TRANSFER_LOG.equals(logtype)) {
            // money update
            money = userBankEvent.getTransferLog().getOutputMoney();
            customer.update(logKey.getLogType(), (int) money);
            logger.info("===== {} {}", customer, userBankEvent);
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
            logger.info("===== {} {}", customer, userBankEvent);

        } else if (LogType.ACCOUNT_OPEN_LOG.equals(logtype)) {
            String accuntNumber = userBankEvent.getOpeningAccountLog().getAccountNumber();
            customer.getAccount().setAccountNumber(accuntNumber);
            logger.info("===== {} {}", customer, userBankEvent);

        } else if (SESSION_LOG.equals(logtype)) {
            // NONE
            customer.getCustomer().updateSessionCnt();

        }


    }


}