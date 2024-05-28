package com.just_talk.chat_api_v2.repository;

import com.just_talk.chat_api_v2.model.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends MongoRepository<Chat,String> {
    List<Chat> findAllByAuthorUserName(String authorUserName);
    Optional<Chat> findById (String chatId);
}
