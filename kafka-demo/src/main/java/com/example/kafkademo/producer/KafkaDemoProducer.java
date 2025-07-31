package com.example.kafkademo.producer;

import com.example.kafkademo.constant.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class KafkaDemoProducer {

    private @Autowired KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message, String callback) {
        String topic = Topic.TWO;
        // 带回调方法
        if (callback != null) {
            kafkaTemplate.send(topic, message)
                    .addCallback(new ListenableFutureCallback<>() {
                        @Override
                        public void onSuccess(SendResult<String, String> result) {
                            System.out.println("Kafka onSuccess");
                        }

                        @Override
                        public void onFailure(Throwable throwable) {
                            System.out.println("Kafka onFailure" + throwable.getMessage());
                        }
                    });
            System.out.println("发送之后 主线程继续运行");
        }
        // 正常发送
        else {
            kafkaTemplate.send(topic, message);
        }
    }
}
