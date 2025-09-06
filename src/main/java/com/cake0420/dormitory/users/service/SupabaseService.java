package com.cake0420.dormitory.users.service;

import reactor.core.publisher.Mono;

public interface SupabaseService {
    Mono<Boolean> validateToken(String token);
}
