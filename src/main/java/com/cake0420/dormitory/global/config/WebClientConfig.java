package com.cake0420.dormitory.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    private final String baseUrl;
    private final String key;

    public WebClientConfig(@Value("${supabase.url}") String baseUrl,
                           @Value("${supabase.key}") String key) {
        this.baseUrl = baseUrl;
        this.key = key;
    }

    @Bean
    public WebClient supabaseWebClient() {
        return WebClient.builder()
                .baseUrl(baseUrl + "/auth/v1")
                .defaultHeader("apikey", key)
                .build();
    }
}
