package com.just_talk.chat_api_v2.repository;

import com.just_talk.chat_api_v2.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRepository extends MongoRepository<Message, String> {
    List<Message> findAllByChatId(String chatId);
}
