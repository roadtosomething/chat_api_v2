package com.just_talk.chat_api_v2.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "messages")
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Message {
    @Id
    private String id;
    private String sender;
    private String receiver;
    private String chatId;
    private String text;
    private Date timestamp;
}
