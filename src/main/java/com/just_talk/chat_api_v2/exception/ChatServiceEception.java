package com.just_talk.chat_api_v2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

public class ChatServiceEception extends HttpStatusCodeException {
    public ChatServiceEception(HttpStatus status, String message) {
        super(status, message);
    }
}
