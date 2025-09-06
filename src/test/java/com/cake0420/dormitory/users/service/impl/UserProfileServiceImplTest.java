package com.cake0420.dormitory.users.service.impl;

import com.cake0420.dormitory.users.domain.UserProfiles;
import com.cake0420.dormitory.users.repository.UserProfileRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserProfileServiceImplTest {

    @Autowired
    private UserProfileRepository userProfileRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private String name;
    private String id;
    private String payload;

    @BeforeEach
    void setUp() {
        id = UUID.randomUUID().toString();
        name = "테스트";
        payload = """
            {
              "metadata": {
                "uuid": "%s",
                "time": "%s",
                "name": "%s",
                "ip_address": "127.0.0.1"
              },
              "user": {
                "id": "%s",
                "aud": "authenticated",
                "role": "",
                "email": "valid.email@supabase.com",
                "phone": "",
                "app_metadata": {
                  "provider": "email",
                  "providers": ["email"]
                },
                "user_metadata": {},
                "identities": [],
                "created_at": "0001-01-01T00:00:00Z",
                "updated_at": "0001-01-01T00:00:00Z",
                "is_anonymous": false
              }
            }
        """.formatted(UUID.randomUUID(), OffsetDateTime.now(), name, id);
    }

    @Test
    @DisplayName("인증 정보 저장 테스트")
    void registerUserProfile() {



        UserProfileServiceImpl service = new UserProfileServiceImpl(objectMapper, userProfileRepository);
        service.registerUserProfile(payload); // 수정
        UserProfiles saved = userProfileRepository.findBySupabaseId(id).orElse(null);
        assertThat(saved).isNotNull();
        assertThat(saved.getSupabaseId()).isEqualTo(id);
        assertThat(saved.getName()).isEqualTo(name);
    }

    @Test
    @DisplayName("유효하지 않은 payload 테스트")
    void registerUserProfile_invalidPayload() throws IOException {
        payload = """
            {
              "meta": {
                "uuid": "%s",
                "time": "%s",
                "name": "%s",
                "ip_address": "127.0.0.1"
              },
              "user": {
                "id": "%s",
                "aud": "authenticated",
                "role": "",
                "email": "valid.email@supabase.com",
                "phone": "",
                "app_metadata": {
                  "provider": "email",
                  "providers": ["email"]
                },
                "user_metadata": {},
                "identities": [],
                "created_at": "0001-01-01T00:00:00Z",
                "updated_at": "0001-01-01T00:00:00Z",
                "is_anonymous": false
              }
            }
        """.formatted(UUID.randomUUID(), OffsetDateTime.now(), name, id);
        UserProfileServiceImpl service = new UserProfileServiceImpl(objectMapper, userProfileRepository);
        service.registerUserProfile(payload); // 수정
        UserProfiles saved = userProfileRepository.findBySupabaseId(id).orElse(null);
        assertThat(saved).isNull();

    }
}