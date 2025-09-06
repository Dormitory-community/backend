package com.cake0420.dormitory.users.utils;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Base64;

@Slf4j
public class WebhookUtils {
    private static final String HMAC_SHA256 = "HmacSHA256";

    public static boolean verifySignature(String signatureHeader, byte[] payloadBytes, String base64SecretKey) {
        try {
            if (signatureHeader == null || signatureHeader.isBlank()) return false;
            String sigPart = signatureHeader.startsWith("v1,") || signatureHeader.startsWith("v1=")
                    ? signatureHeader.substring(3) : signatureHeader;

            byte[] key = decodeBase64Lenient(base64SecretKey);

            // compute HMAC
            Mac hmac = Mac.getInstance(HMAC_SHA256);
            hmac.init(new SecretKeySpec(key, HMAC_SHA256));
            byte[] computed = hmac.doFinal(payloadBytes);

            // signaturePart may be base64 or hex; try base64 first then hex fallback
            byte[] sigBytes;
            try {
                sigBytes = decodeBase64Lenient(sigPart);
            } catch (IllegalArgumentException ex) {
                sigBytes = hexToBytes(sigPart);
            }
            return MessageDigest.isEqual(sigBytes, computed);
        } catch (Exception e) {
            // production: do not log secrets/payloads
            return false;
        }
    }

    private static byte[] decodeBase64Lenient(String s) {
        String norm = s.replace('-', '+').replace('_', '/');
        int pad = (4 - (norm.length() % 4)) % 4;
        if (pad > 0) norm += "=".repeat(pad);
        return Base64.getDecoder().decode(norm);
    }

    private static byte[] hexToBytes(String s) {
        if (s.length() % 2 != 0) throw new IllegalArgumentException("Invalid hex length");
        byte[] out = new byte[s.length()/2];
        for (int i=0;i<s.length();i+=2) {
            out[i/2] = (byte) Integer.parseInt(s.substring(i,i+2), 16);
        }
        return out;
    }
}
