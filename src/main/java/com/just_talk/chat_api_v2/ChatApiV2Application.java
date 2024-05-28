package com.just_talk.chat_api_v2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@RequestMapping("/chat-api")
public class ChatApiV2Application {

    public static void main(String[] args) {
        SpringApplication.run(ChatApiV2Application.class, args);
    }

}
