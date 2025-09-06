package com.cake0420.dormitory.users.utils;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Slf4j
public class WebhookUtils {
    private static final String HMAC_SHA256 = "HmacSHA256";


    public static boolean verifySignature(String payload, String signatureHeader, String secretWithPrefix) {
        try {
            if (signatureHeader == null || !signatureHeader.startsWith("v1=")) {
                return false;
            }
            String signatureFromHeader = signatureHeader.substring(3);
            // whsec_ 제거 후 Base64 디코딩
            String base64Secret = secretWithPrefix.replace("whsec_", "");
            byte[] secretKeyBytes = Base64.getDecoder().decode(base64Secret);

            // HMAC-SHA256 계산
            Mac hmac = Mac.getInstance(HMAC_SHA256);
            SecretKeySpec keySpec = new SecretKeySpec(secretKeyBytes, HMAC_SHA256);
            hmac.init(keySpec);

            byte[] hash = hmac.doFinal(payload.getBytes());
            String computedSignature = Base64.getEncoder().encodeToString(hash);

            return computedSignature.equals(signatureFromHeader);
        } catch (Exception e) {
            log.error("verifySignature error : {}", e.getMessage());
            return false;
        }
    }
}
