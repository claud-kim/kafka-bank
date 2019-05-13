package com.claud.kafka.producer;

import com.claud.kafka.*;
import com.claud.kafka.producer.vo.send.LogKey;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.claud.kafka.AppConstants.IN_GEN;

public class ProducerMain {
    private static final Logger logger =
            LoggerFactory.getLogger(ProducerMain.class);


    private static Producer<LogKey, String> createProducer() {
        final Properties props = new Properties();
        setupBootstrapAndSerializers(props);

        logger.info("Configuring Kafka Producer " + props);
        return new KafkaProducer<>(props);
    }

    private static void setupBootstrapAndSerializers(Properties props) {

        // Configure serializers.
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                AppConstants.BOOTSTRAP_SERVERS);

        // Configure client id.
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "kafkaProducer");

        // Configure key serializer class to StringSerializer.
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                LogKeySerialize.class.getName());


        // Custom Serializer - config "value.serializer"
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());

        // Custom partitioner - add
        props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, LogKeyPartitioner.class.getName());

    }

    public static void main(String[] args) {

        GenStatus.getInstance().init(AppConstants.GEN_USER_SIZE);

        Producer<LogKey, String> producer = createProducer();

        final List<ProducerRunnable> stockSenders = getSenderList(producer, AppConstants.PRODUCER_NUM);

        final ExecutorService executorService =
                Executors.newFixedThreadPool(stockSenders.size());


        stockSenders.forEach(executorService::submit);


        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            executorService.shutdown();
            try {
                executorService.awaitTermination(200, TimeUnit.MILLISECONDS);
                logger.info("Flushing and closing producer");
                producer.flush();
                producer.close(Duration.ofMillis(10_000));


                System.out.println("=================ProducerMain====================");
                String genIn = GenStatus.getInstance().printSummary();
                FileUtil.writeFile(IN_GEN, genIn);
                System.out.println(genIn);

                AppConstants.printPrettyMap();

                System.out.println("=====================================");

            } catch (InterruptedException e) {
                logger.warn("shutting down", e);
            }
        }));


    }

    private static List<ProducerRunnable> getSenderList(
            final Producer<LogKey, String> producer, final int size) {

        List<ProducerRunnable> lists = new ArrayList<>();

        int start = 0;
        int perThread = GenStatus.getInstance().getUserTotalSize() / size;
        for (int i = 0; i < size; i++) {
            lists.add(new ProducerRunnable(AppConstants.TOPIC_NAME, start, start + perThread, GenStatus.getInstance(), producer)
            );

            start += perThread;
        }

        return lists;
    }

}














