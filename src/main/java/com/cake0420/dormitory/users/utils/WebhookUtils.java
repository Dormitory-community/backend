package com.cake0420.dormitory.users.utils;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

@Slf4j
public class WebhookUtils {
    private static final String HMAC_SHA256 = "HmacSHA256";

    public static boolean verifySignature(String signatureHeader, String payload, String secretKey) {
        try {
            if (signatureHeader == null || signatureHeader.isBlank()) {
                log.warn("Missing signature header");
                return false;
            }

            // v1, 접두사 제거
            String sigPart = signatureHeader.startsWith("v1,") ? signatureHeader.substring(3) : signatureHeader;

            // 페이로드를 UTF-8 바이트로 변환
            byte[] payloadBytes = payload.getBytes(StandardCharsets.UTF_8);

            // 시크릿 키를 UTF-8 바이트로 변환 (Edge Function과 동일하게)
            byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);

            // HMAC-SHA256 계산
            Mac hmac = Mac.getInstance(HMAC_SHA256);
            hmac.init(new SecretKeySpec(keyBytes, HMAC_SHA256));
            byte[] computedSignature = hmac.doFinal(payloadBytes);

            // Edge Function에서 보낸 Base64 서명 디코딩
            byte[] receivedSignature;
            try {
                receivedSignature = Base64.getDecoder().decode(sigPart);
            } catch (IllegalArgumentException e) {
                log.warn("Failed to decode signature as Base64: {}", sigPart);
                return false;
            }

            // 서명 비교 (상수 시간 비교 사용)
            boolean isValid = MessageDigest.isEqual(receivedSignature, computedSignature);

            if (!isValid) {
                log.warn("Signature verification failed");
            }

            return isValid;

        } catch (Exception e) {
            log.error("Error during signature verification", e);
            return false;
        }
    }

    // 디버깅용 헬퍼 메서드 (운영 환경에서는 제거)
    public static String computeExpectedSignature(String payload, String secretKey) {
        try {
            byte[] payloadBytes = payload.getBytes(StandardCharsets.UTF_8);
            byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);

            Mac hmac = Mac.getInstance(HMAC_SHA256);
            hmac.init(new SecretKeySpec(keyBytes, HMAC_SHA256));
            byte[] signature = hmac.doFinal(payloadBytes);

            String base64Sig = Base64.getEncoder().encodeToString(signature);
            return "v1," + base64Sig;
        } catch (Exception e) {
            log.error("Error computing expected signature", e);
            return null;
        }
    }
}