package com.cake0420.dormitory.users.service.impl;

import com.cake0420.dormitory.users.domain.UserProfiles;
import com.cake0420.dormitory.users.domain.dto.SupabaseUserDTO;
import com.cake0420.dormitory.users.domain.enums.Role;
import com.cake0420.dormitory.users.repository.UserProfileRepository;
import com.cake0420.dormitory.users.service.UserProfileService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {
    private final ObjectMapper objectMapper;
    private final UserProfileRepository userProfileRepository;


    @Override
    public void registerUserProfile(String payload) {

        try {
            JsonNode json = objectMapper.readTree(payload);
            SupabaseUserDTO dto = validatePayload(json);
            if (dto == null) return;

            boolean exists = userProfileRepository.findBySupabaseId(dto.id()).isPresent();
            if (exists) return;

            UserProfiles profile = UserProfiles.builder()
                    .name(dto.name())
                    .userProfileUrl("")
                    .role(Role.ROLE_USER)
                    .supabaseId(dto.id())
                    .build();
            userProfileRepository.save(profile);
        } catch (IllegalStateException e) {
            throw e;
        } catch (JsonProcessingException e) {
            log.error("JsonParseException : {}", e.getMessage());
            throw new RuntimeException(e);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private SupabaseUserDTO validatePayload(JsonNode json) {
        JsonNode userNode = json.get("user");
        if (userNode == null) return null;
        if (!userNode.has("user_metadata") || !userNode.get("user_metadata").hasNonNull("full_name")) {
            return null;
        }


        String supabaseId = userNode.get("id").asText();
        String name = userNode.get("user_metadata").get("full_name").asText();
        if (supabaseId == null || supabaseId.isBlank() || name == null || name.isBlank()) return null;


        return new SupabaseUserDTO(supabaseId, name);
    }
}
