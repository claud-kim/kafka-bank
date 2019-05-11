package com.claud.kafka.consumer;

import com.claud.kafka.AppConstants;
import com.claud.kafka.LogKeyDeserialize;
import com.claud.kafka.consumer.rest.UserController;
import com.claud.kafka.consumer.rest.UserService;
import com.claud.kafka.producer.vo.send.LogKey;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

import static spark.Spark.get;

import static java.util.concurrent.Executors.newFixedThreadPool;

public class ConsumerMain {
    private static final Logger logger =
            LoggerFactory.getLogger(ConsumerMain.class);


    private static Consumer<LogKey, String> createConsumer() {
        final Properties props = new Properties();

        //Turn off auto commit - "enable.auto.commit".
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                AppConstants.BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG,
                "KafkaExampleConsumer");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                LogKeyDeserialize.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class.getName());
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1);


        // Create the consumer using props.
        return new KafkaConsumer<>(props);
    }

    public static void main(String[] args) throws Exception {

        // Rest API
        new UserController(new UserService());

        // 1:N partition - consumer thread
        final List<PartitionInfo> partitionInfos = createConsumer().partitionsFor(AppConstants.TOPIC_NAME);

        final int threadCount = partitionInfos.size();
        logger.info("----- {}", threadCount);
        System.out.println(ManagerCustomer.getInstance().printSummary());


        final ExecutorService executorService = newFixedThreadPool(threadCount);
        final AtomicBoolean stopAll = new AtomicBoolean();
        final List<Consumer> consumerList = new ArrayList<>(threadCount);


        IntStream.range(0, threadCount).forEach(index -> {
            final Consumer<LogKey, String> consumer = createConsumer();

            final int workerCount = AppConstants.CONSUMER_NUM_PER_THREAD;

            final ConsumerRunnable stockPriceConsumer =
                    new ConsumerRunnable(consumer,
                            AppConstants.CONSUMER_UPDATE_NUM, index, stopAll, workerCount);


            consumerList.add(consumer);
            executorService.submit(stockPriceConsumer);
        });

        //Register nice shutdown of thread pool, then close consumer.
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Stopping app");
            stopAll.set(true);
            sleep();
            consumerList.forEach(Consumer::wakeup);
            executorService.shutdown();
            try {
                executorService.awaitTermination(5_000, TimeUnit.MILLISECONDS);
                if (!executorService.isShutdown())
                    executorService.shutdownNow();
                System.out.println("=================ConsumerMain====================");
                System.out.println(ManagerCustomer.getInstance().printSummary());
                System.out.println("=====================================");
            } catch (InterruptedException e) {
                logger.warn("shutting down", e);
            }
            sleep();
            consumerList.forEach(Consumer::close);
        }));
    }

    private static void sleep() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            logger.error("", e);
        }
    }

}
