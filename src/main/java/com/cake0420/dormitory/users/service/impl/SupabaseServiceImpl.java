package com.cake0420.dormitory.users.service.impl;

import com.cake0420.dormitory.users.domain.dto.UserResponseDTO;
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

    @Override
    public Mono<String> validateToken(String token) {
        return supabaseWebClient.get()
                .uri("/user")
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(UserResponseDTO.class)
                .map(UserResponseDTO::email)
                .onErrorResume(e -> {
                    log.warn("토큰 검증 실패: {}", e.getMessage());
                    return Mono.empty();
                });
    }

//    public Optional<String> validateEcc(String token) {
//        try {
//            PublicKey publicKey = getPublicKeyFromPem(eccPublicKeyPem);
//            Claims claims = Jwts.parser()
//                    .verifyWith(publicKey)
//                    .build()
//                    .parseSignedClaims(token)
//                    .getPayload();
//
//            return Optional.ofNullable(claims.get("email", String.class));
//        } catch (Exception e) {
//            log.warn("ECC 토큰 검증 실패: {}", e.getMessage());
//            return Optional.empty();
//        }
//    }
//
//    private PublicKey getPublicKeyFromPem(String pem) throws Exception {
//        String publicKeyPem = pem
//                .replace("-----BEGIN PUBLIC KEY-----", "")
//                .replace("-----END PUBLIC KEY-----", "")
//                .replaceAll("\\s", "");
//        byte[] decoded = Base64.getDecoder().decode(publicKeyPem);
//        java.security.spec.X509EncodedKeySpec spec = new java.security.spec.X509EncodedKeySpec(decoded);
//        java.security.KeyFactory kf = java.security.KeyFactory.getInstance("EC");
//        return kf.generatePublic(spec);
//    }
}
