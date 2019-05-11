package com.claud.kafka;

import com.claud.kafka.producer.vo.send.LogKey;
import com.claud.kafka.producer.vo.log.LogType;
import com.google.gson.Gson;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;

import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LogKeyPartitioner implements Partitioner {
    private Gson gson = JsonUtil.gson();
    private static final Set<LogType> enterLog = new HashSet<LogType>();

    static {
        enterLog.add(LogType.JOIN_LOG);
        enterLog.add(LogType.OPENING_ACCOUNT_LOG);

    }

    @Override
    public int partition(final String topic,
                         final Object objectKey,
                         final byte[] keyBytes,
                         final Object value,
                         final byte[] valueBytes,
                         final Cluster cluster) {
        final List<PartitionInfo> partitionInfoList =
                cluster.availablePartitionsForTopic(topic);

        final int partitionCount = partitionInfoList.size();
        final int importantPartition = 0;
        final int normalPartitionCount = partitionCount - 1;
        LogKey key = gson.fromJson(new String(keyBytes, StandardCharsets.UTF_8), LogKey.class);
        LogType logType = key.getLogType();

        if (enterLog.contains(logType) || key.getCustomerNumber() == null) {
            return importantPartition;
        }

        return Math.abs(key.hashCode()) % normalPartitionCount;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
