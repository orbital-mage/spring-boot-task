package org.example.consumerservice.repositories;

import org.example.common.models.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRepository extends MongoRepository<Message, Integer> {
}
