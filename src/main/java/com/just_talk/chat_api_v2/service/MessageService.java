package com.just_talk.chat_api_v2.service;

import com.just_talk.chat_api_v2.model.Message;
import com.just_talk.chat_api_v2.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public List<Message> getChatMessages(String chatId){
        return messageRepository.findAllByChatId(chatId);
    }

    public Message saveChatMessage(Message message){
        return messageRepository.save(message);
    }
}
