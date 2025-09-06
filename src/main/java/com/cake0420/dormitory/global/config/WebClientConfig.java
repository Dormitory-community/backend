package com.cake0420.dormitory.global.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@Getter
public class WebClientConfig {
    private final String baseUrl;
    private final String key;
    private final String serviceKey;
    private final String supabaseWebhookSecret;
    public WebClientConfig(@Value("${supabase.url}") String baseUrl,
                           @Value("${supabase.key}") String key,
                           @Value("${supabase.service-key}") String serviceKey,
                           @Value("${supabase.webhook.secret}") String supabaseWebhookSecret) {
        this.baseUrl = baseUrl;
        this.key = key;
        this.serviceKey = serviceKey;
        this.supabaseWebhookSecret = supabaseWebhookSecret;
    }

    @Bean
    public WebClient supabaseWebClient() {
        return WebClient.builder()
                .baseUrl(baseUrl + "/auth/v1")
                .defaultHeader("apikey", key)
                .build();
    }
}
