package com.just_talk.chat_api_v2.controller;


import com.just_talk.chat_api_v2.dto.UserDto;
import com.just_talk.chat_api_v2.exception.ChatServiceEception;
import com.just_talk.chat_api_v2.model.Chat;
import com.just_talk.chat_api_v2.model.Message;
import com.just_talk.chat_api_v2.service.ChatService;
import com.just_talk.chat_api_v2.service.MessageService;
import com.just_talk.chat_api_v2.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.bind.annotation.*;


import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/chat/messages")
public class MessageRestController {

    @Autowired
    private UserService userService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private ChatService chatService;

    @PostMapping("/{chatId}")
    public Message sendMessage(@PathVariable String chatId, @RequestBody Message message, @RequestHeader(HttpHeaders.AUTHORIZATION) String bearerToken) {
        String token = bearerToken.substring(7);
        UserDto user = userService.getUserClaimsByToken(token).block();
        Optional<Chat> chat = chatService.getChatById(chatId);
        if (chat.isPresent()) {
            assert user != null;
            return messageService.saveChatMessage(
                    message.toBuilder()
                            .id(UUID.randomUUID().toString())
                            .sender(user.getUsername())
                            .receiver(chat.get().getAuthorUserName())
                            .chatId(chatId)
                            .text(message.getText())
                            .timestamp(new Date())
                            .build());
        }
        else {
            throw new ChatServiceEception(HttpStatus.INTERNAL_SERVER_ERROR, "Chat is missing");
        }
    }

    @GetMapping("/{chatId}")
    public List<Message> getMessages(@PathVariable String chatId,@RequestHeader(HttpHeaders.AUTHORIZATION) String bearerToken) {
        String token = bearerToken.substring(7);
        log.info("Login with token {}", token);
        UserDto user = userService.getUserClaimsByToken(token).block();
        log.info("User info: {}", user);
        String username = user.getUsername();
        return messageService.getChatMessages(chatId)
                .stream()
                .filter((m)->m.getSender().equals(username) ||
                        m.getReceiver().equals(username))
                .collect(Collectors.toList());
    }

}
