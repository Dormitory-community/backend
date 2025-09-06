package com.cake0420.dormitory.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class WebClientConfig {
    private final SupabaseProperties supabaseProperties;

    @Bean
    public WebClient supabaseWebClient() {
        return WebClient.builder()
                .baseUrl(supabaseProperties.getUrl() + "/auth/v1")
                .defaultHeader("apikey", supabaseProperties.getKey())
                .build();
    }
}
