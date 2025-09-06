package com.cake0420.dormitory.users.controller;

import com.cake0420.dormitory.global.config.WebClientConfig;
import com.cake0420.dormitory.users.service.SupabaseService;
import com.cake0420.dormitory.users.service.UserProfileService;
import com.cake0420.dormitory.users.utils.WebhookUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "유저 프로필 API")
@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserProfileController {
    private final SupabaseService supabaseService;
    private final UserProfileService userProfileService;
    private final WebClientConfig webClientConfig;



    @PostMapping("/user-profile")
    @Operation(summary = "유저 정보 저장",
                description = """
                            supabse webhook 기능을 이용해 회원가입시 해당 api를 호출합니다.
                        """
    )
    public ResponseEntity<String> registerUserProfile(@RequestHeader(name = "x-supabase-signature", required = false) String signatureHeader,
                                                      @RequestHeader(name = "Authorization", required = false) String authorizationHeader,
                                                      @RequestBody String payload) {
        boolean validAuth = supabaseService.validateServiceKey(authorizationHeader);
        boolean isValid = WebhookUtils.verifySignature(signatureHeader, payload, webClientConfig.getSupabaseWebhookSecret());

        if (!validAuth) {
            log.warn("유효하지 않은 토큰");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 토큰입니다.");

        }
        if (!isValid) {
            log.warn("유효하지 않은 시그니처");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 시그니처입니다.");
        }

        try {
            userProfileService.registerUserProfile(payload);
            return ResponseEntity.ok().body("유저 정보가 등록되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 내부 오류 발생");
        }
    }
}
