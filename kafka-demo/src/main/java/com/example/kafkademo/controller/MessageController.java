package com.example.kafkademo.controller;

import com.example.kafkademo.producer.KafkaDemoProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/kafka")
public class MessageController {

    private final KafkaDemoProducer kafkaDemoProducer;

    @Autowired
    public MessageController(KafkaDemoProducer kafkaDemoProducer) {
        this.kafkaDemoProducer = kafkaDemoProducer;
    }

    @PostMapping("/publish")
    public String sendMessageToKafkaTopic(
            @RequestParam("message") String message,
            @RequestParam("callback") String callback
    ) {
        kafkaDemoProducer.sendMessage(message, callback);
        return "Message sent to the Kafka Topic";
    }
}