package com.cake0420.dormitory.users.service.impl;

import com.cake0420.dormitory.users.domain.UserProfiles;
import com.cake0420.dormitory.users.repository.UserProfileRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserProfileServiceImplTest {

    @Autowired
    private UserProfileRepository userProfileRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();


    @Test
    @DisplayName("인증 정보 저장 테스트")
    void registerUserProfile() {

        String id = UUID.randomUUID().toString();
        String name = "테스트";
        String payload = """
            {
              "event": "INSERT",
              "record": {
                "id": "%s",
                "name": "%s"
              }
            }
            """.formatted(id, name);

        UserProfileServiceImpl service = new UserProfileServiceImpl(objectMapper, userProfileRepository);
        service.registerUserProfile(payload);
        UserProfiles saved = userProfileRepository.findBySupabaseId(id).orElse(null);
        assertThat(saved).isNotNull();
        assertThat(saved.getSupabaseId()).isEqualTo(id);
        assertThat(saved.getName()).isEqualTo(name);
    }

    @Test
    @DisplayName("유효하지 않은 payload 테스트")
    void registerUserProfile_invalidPayload() {
        String id = UUID.randomUUID().toString();
        String name = "테스트";
        String payload = """
            {
              "event": "UPDATE",
              "record": {
                "id": "%s",
                "name": "%s"
              }
            }
            """.formatted(id, name);

        UserProfileServiceImpl service = new UserProfileServiceImpl(objectMapper, userProfileRepository);
        service.registerUserProfile(payload);
        UserProfiles saved = userProfileRepository.findBySupabaseId(id).orElse(null);
        assertThat(saved).isNull();

    }
}