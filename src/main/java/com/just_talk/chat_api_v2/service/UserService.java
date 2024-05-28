package com.just_talk.chat_api_v2.service;

import com.just_talk.chat_api_v2.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {

    private final WebClient.Builder webClientBuilder;

    public Mono<UserDto> getUserClaimsByToken(String token) {
        return webClientBuilder.build()
                .get()
                .uri("http://security-app:8083/api/v1/auth/info")
                .header(HttpHeaders.AUTHORIZATION, "Bearer "+token)
                .retrieve()
                .bodyToMono(UserDto.class);
    }
}