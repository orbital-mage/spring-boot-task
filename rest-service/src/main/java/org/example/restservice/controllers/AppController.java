package org.example.restservice.controllers;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.example.common.Constants;
import org.example.common.models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RestController
public class AppController {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private ReplyingKafkaTemplate<String, Object, Object> replyingKafkaTemplate;

    @PostMapping("/create")
    public void create(@RequestBody Message message) {
        kafkaTemplate.send(Constants.CREATE_TOPIC, message);
    }

    @PutMapping("/update")
    public void update(@RequestBody Message message) {
        kafkaTemplate.send(Constants.UPDATE_TOPIC, message);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam int id) {
        kafkaTemplate.send(Constants.DELETE_TOPIC, String.valueOf(id));
    }

    @GetMapping("/read")
    public Message read(@RequestParam int id) throws Exception {
        if (!replyingKafkaTemplate.waitForAssignment(Duration.ofSeconds(10))) {
            throw new IllegalStateException("Reply container did not initialize");
        }

        var future = replyingKafkaTemplate.sendAndReceive(
                new ProducerRecord<>(Constants.READ_TOPIC, String.valueOf(id))
        );

        var value = future.get(10, TimeUnit.SECONDS).value();

        if (value == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Message not found");
        }

        return (Message) value;
    }
}
