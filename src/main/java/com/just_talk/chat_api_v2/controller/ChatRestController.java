package com.just_talk.chat_api_v2.controller;

import com.just_talk.chat_api_v2.dto.UserDto;
import com.just_talk.chat_api_v2.exception.AuthException;
import com.just_talk.chat_api_v2.exception.ChatServiceEception;
import com.just_talk.chat_api_v2.model.Chat;
import com.just_talk.chat_api_v2.model.ChatStatus;
import com.just_talk.chat_api_v2.model.UserRole;
import com.just_talk.chat_api_v2.service.ChatService;
import com.just_talk.chat_api_v2.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/chats")
public class ChatRestController {

    @Autowired
    private ChatService chatService;
    @Autowired
    private UserService userService;

    @DeleteMapping
    public ResponseEntity<?> deleteAllChats(@RequestHeader(HttpHeaders.AUTHORIZATION) String bearerToken) {
        try {
            String token = bearerToken.substring(7);
            UserDto user = userService.getUserClaimsByToken(token).block();
            if (!user.isEnabled()){
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            else if (user.getRole()!= UserRole.ADMIN){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            chatService.deleteChats();
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>("Deleted chats was wrong: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    @RequestMapping("/create")
    public ResponseEntity<?> createChat(@RequestHeader(HttpHeaders.AUTHORIZATION) String bearerToken) {
        log.info('[' + bearerToken + "] is authorized");
        try {
            String token = bearerToken.substring(7);
            log.info('[' + token + "] is complete");
            UserDto user = userService.getUserClaimsByToken(token).block();
            log.info("{} is logged in", user.toString());
            if (!user.isEnabled()){
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            Optional<Chat> result = chatService.saveChat(
                    Chat
                            .builder()
                            .authorName(userService.getUserClaimsByToken(token).map(UserDto::getFirstName).block())
                            .authorUserName(userService.getUserClaimsByToken(token).map(UserDto::getUsername).block())
                            .createdDate(new Date())
                            .id(UUID.randomUUID().toString())
                            .chatStatus(ChatStatus.ACTIVE)
                            .build());
            if (result.isPresent()) {
                return ResponseEntity.ok(result.get());
            }
            else {
                return new ResponseEntity<>("Your chat is already exist", HttpStatus.UNAUTHORIZED);
            }
        }
        catch (Exception e) {
            return new ResponseEntity<>("Saved chats was wrong: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public List<Chat> getChats() {
        return chatService.getChats();
    }
}
