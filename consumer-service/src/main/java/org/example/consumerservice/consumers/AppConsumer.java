package org.example.consumerservice.consumers;

import org.example.common.Constants;
import org.example.common.models.Message;
import org.example.consumerservice.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaNull;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
public class AppConsumer {
    @Autowired
    private MessageRepository repository;

    @KafkaListener(topics = Constants.CREATE_TOPIC, groupId = Constants.CONSUMER_GROUP)
    public void listenCreate(Message message) {
        if (!repository.existsById(message.getId())) {
            repository.save(message);
        }
    }

    @KafkaListener(topics = Constants.UPDATE_TOPIC, groupId = Constants.CONSUMER_GROUP)
    public void listenUpdate(Message message) {
        if (repository.existsById(message.getId())) {
            repository.save(message);
        }
    }

    @KafkaListener(topics = Constants.DELETE_TOPIC, groupId = Constants.CONSUMER_GROUP)
    public void listenDelete(String id) {
        repository.deleteById(Integer.parseInt(id));
    }

    @KafkaListener(topics = Constants.READ_TOPIC, groupId = Constants.CONSUMER_GROUP)
    @SendTo
    public Object listenRead(String id) {
        return repository.findById(Integer.parseInt(id))
                .map(Object.class::cast)
                .orElse(KafkaNull.INSTANCE);
    }
}
