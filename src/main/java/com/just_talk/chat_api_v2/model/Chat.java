package com.just_talk.chat_api_v2.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Document(collection = "chats")
public class Chat {
    @Id
    private String id;
    private String authorName;
    private String authorUserName;
    private ChatStatus chatStatus;
    private Date createdDate;
}
