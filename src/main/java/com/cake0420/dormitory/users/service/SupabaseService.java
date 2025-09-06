package com.cake0420.dormitory.users.service;

import com.cake0420.dormitory.users.domain.dto.SupabaseUserDTO;
import reactor.core.publisher.Mono;

public interface SupabaseService {
    Mono<Boolean> validateToken(String token);

    SupabaseUserDTO getUserFromToken(String token);
}
