package com.example.kafkademo.consumer;

import com.example.kafkademo.constant.Group;
import com.example.kafkademo.constant.Topic;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.TopicSuffixingStrategy;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;

@Service
public class KafkaDemoConsumer {

    private static final Logger log = LoggerFactory.getLogger(KafkaDemoConsumer.class);

    /**
     *
     * @param record
     */
    @KafkaListener(topics =Topic.ONE, groupId = Group.ONE)
    public void listen(ConsumerRecord<?, ?> record) {
        System.out.printf("Received Message: topic=%s, partition=%d, offset=%d, key=%s, value=%s\n",
                record.topic(), record.partition(), record.offset(), record.key(), record.value());
    }


    /**
     * 正常消费主题，失败后会自动重试 3 次，间隔 5 秒
     */
    @RetryableTopic(
            attempts = "3",                         // 最多重试 3 次（共尝试 4 次）
            backoff = @Backoff(delay = 5000),       // 每次重试间隔 5 秒
            topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_DELAY_VALUE, // 重试主题后缀：-retry-0, -retry-1...
            dltTopicSuffix = "-dlt"                 // 死信队列主题后缀
    )
    @KafkaListener(topics = Topic.TWO, groupId = Group.TWO)
    public void retryableListen(ConsumerRecord<?, ?> record) {
        String value = record.value().toString();
        log.info("Processing message: offset={}, value={}", record.offset(), value);

        // 模拟业务异常（用于测试重试）
        if (value.contains("fail")) {
            log.warn("Simulating failure for message: {}", value);
            throw new RuntimeException("Simulated processing failure for: " + value);
        }

        // 正常处理
        log.info("Successfully processed message: {}", value);
    }

    /**
     * 当所有重试都失败后，消息会被发送到 DLT（Dead-Letter Topic）
     * 这个方法处理 DLT 中的消息
     */
    @DltHandler
    public void dltHandler(ConsumerRecord<?, ?> record) {
        log.error("Message failed after all retries. Sending to DLT. Topic: {}, Offset: {}, Value: {}",
                record.topic(), record.offset(), record.value());
        // 可以记录告警、存数据库、人工处理等
    }
}