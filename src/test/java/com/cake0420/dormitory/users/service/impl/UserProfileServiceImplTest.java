package com.cake0420.dormitory.users.service.impl;

import com.cake0420.dormitory.users.domain.UserProfiles;
import com.cake0420.dormitory.users.domain.enums.Role;
import com.cake0420.dormitory.users.exception.UserDuplicateException;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
class UserProfileServiceImplTest {

    @Autowired
    private UserProfileRepository userProfileRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private String name;
    private String id;
    private String email;
    private String payload;

    @BeforeEach
    void setUp() {
        id = UUID.randomUUID().toString();
        name = "테스트";
        email = "test@google.com";
        payload = """
            {
               "metadata": {
                 "uuid": "%s",
                 "time": "%s",
                 "name": "before-user-created",
                 "ip_address": "127.0.0.1"
               },
               "user": {
                 "id": "%s",
                 "aud": "authenticated",
                 "role": "",
                 "email": "%s",
                 "phone": "",
                 "app_metadata": {
                   "provider": "email",
                   "providers": ["email"]
                 },
                 "user_metadata": {
                   "full_name": "%s"
                 },
                 "identities": [],
                 "created_at": "0001-01-01T00:00:00Z",
                 "updated_at": "0001-01-01T00:00:00Z",
                 "is_anonymous": false
               }
             }
        """.formatted(UUID.randomUUID(), OffsetDateTime.now(), id, email,name);
    }

    @Test
    @DisplayName("인증 정보 저장 테스트")
    void registerUserProfile() {



        UserProfileServiceImpl service = new UserProfileServiceImpl(objectMapper, userProfileRepository);
        service.registerUserProfile(payload); // 수정
        UserProfiles saved = userProfileRepository.findByEmail(email).orElse(null);
        assertThat(saved).isNotNull();
        assertThat(saved.getSupabaseId()).isEqualTo(id);
        assertThat(saved.getEmail()).isEqualTo(email);
        assertThat(saved.getName()).isEqualTo(name);
    }

    @Test
    @DisplayName("유효하지 않은 payload 테스트")
    void registerUserProfile_invalidPayload() throws IOException {
        payload = """
            {
               "metadata": {
                 "uuid": "%s",
                 "time": "%s",
                 "name": "before-user-created",
                 "ip_address": "127.0.0.1"
               },
               "user": {
                 "id": "%s",
                 "aud": "authenticated",
                 "role": "",
                 "email": "",
                 "phone": "",
                 "app_metadata": {
                   "provider": "email",
                   "providers": ["email"]
                 },
                "user_metadata": {
                   "full_name": "name"
                 },
                 "identities": [],
                 "created_at": "0001-01-01T00:00:00Z",
                 "updated_at": "0001-01-01T00:00:00Z",
                 "is_anonymous": false
               }
             }
        """.formatted(UUID.randomUUID(), OffsetDateTime.now(), id);
        UserProfileServiceImpl service = new UserProfileServiceImpl(objectMapper, userProfileRepository);
        service.registerUserProfile(payload);
        UserProfiles saved = userProfileRepository.findByEmail(email).orElse(null);
        assertThat(saved).isNull();

    }

    @Test
    @DisplayName("회원 가입 되어 있는 이메일 예외 테스트")
    void DuplicateUserProfile() {
        UserProfiles userProfiles = UserProfiles.builder()
                                    .email(email)
                                    .name(name)
                                    .userProfileUrl("")
                                    .supabaseId(id)
                                    .role(Role.ROLE_USER)
                                    .build();
        userProfileRepository.save(userProfiles);
        UserProfileServiceImpl service = new UserProfileServiceImpl(objectMapper, userProfileRepository);
        assertThatThrownBy(() -> service.registerUserProfile(payload))
                                .isInstanceOf(UserDuplicateException.class);
    }
}