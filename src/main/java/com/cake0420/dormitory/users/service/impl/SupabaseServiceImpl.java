package com.cake0420.dormitory.users.service.impl;

import com.cake0420.dormitory.global.config.WebClientConfig;
import com.cake0420.dormitory.users.service.SupabaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class SupabaseServiceImpl implements SupabaseService {
    private final WebClient supabaseWebClient;
    private final WebClientConfig webClientConfig;

    @Override
    public Mono<Boolean> validateToken(String token) {
        return supabaseWebClient.get()
                .uri("/user")
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .toBodilessEntity()
                .map(response -> response.getStatusCode().is2xxSuccessful())
                .onErrorReturn(false);
    }

    @Override
    public boolean validateServiceKey(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) return false;
        String token = authHeader.substring(7);
        return token.equals(webClientConfig.getServiceKey());
    }


}
