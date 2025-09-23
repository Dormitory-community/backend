package com.cake0420.dormitory.users.domain.dto;

import java.util.List;

public record UserResponseDTO(
        String aal,
        List<AMR> amr,
        AppMetadata app_metadata,
        String aud,
        String email,
        long exp,
        long iat,
        String iss,
        String phone,
        String role,
        String session_id,
        String sub,
        UserMetadata user_metadata,
        boolean is_anonymous
) {
    public record AMR(String method, long timestamp) {}
    public record AppMetadata(String provider, List<String> providers) {}
    public record UserMetadata(String name) {}
}
