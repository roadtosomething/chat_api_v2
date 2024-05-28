package com.just_talk.chat_api_v2.service;

import com.just_talk.chat_api_v2.model.Chat;
import com.just_talk.chat_api_v2.model.ChatStatus;
import com.just_talk.chat_api_v2.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChatService {
    @Autowired
    private ChatRepository chatRepository;


    public Optional<Chat> saveChat(Chat chat) {
        if (chatRepository.findAllByAuthorUserName(chat.getAuthorUserName()).isEmpty()||
                chatRepository.findAllByAuthorUserName(chat.getAuthorUserName())==null ) {
            return Optional.of(chatRepository.save(chat));
        }
        else {
            return Optional.empty();
        }
    }

    public List<Chat> getChats() {
        return chatRepository
                .findAll()
                .stream()
                .filter(c->c.getChatStatus().equals(ChatStatus.ACTIVE))
                .collect(Collectors.toList());
    }

    public Optional<Chat> getChatById(String id) {
        return chatRepository.findById(id);
    }

    public void deleteChats() {
        chatRepository.deleteAll();
    }
}
