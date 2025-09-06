package com.cake0420.dormitory.users.controller;

import com.cake0420.dormitory.users.service.UserProfileService;
import com.cake0420.dormitory.users.utils.WebhookUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "유저 프로필 API")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserProfileController {
    private final UserProfileService userProfileService;

    @Value("${supabase.webhook.secret}")
    private String SUPABASE_WEBHOOK_SECRET;

    @PostMapping("/user-profile")
    @Operation(summary = "유저 정보 저장",
                description = """
                            supabse webhook 기능을 이용해 회원가입시 해당 api를 호출합니다.
                        """
    )
    public ResponseEntity<String> registerUserProfile(@RequestHeader(name = "x-supabase-signature", required = false) String signatureHeader,
                                                      @RequestBody String payload) {
        boolean isValid = WebhookUtils.verifySignature(signatureHeader, payload, SUPABASE_WEBHOOK_SECRET);

        if (!isValid) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 시그니처입니다.");
        }

        try {
            userProfileService.registerUserProfile(payload);
            return ResponseEntity.ok().body("유저 정보가 등록되었습니다.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 등록된 유저입니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 내부 오류 발생");
        }
    }
}
