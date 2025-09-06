package com.cake0420.dormitory.users.service;

import com.fasterxml.jackson.databind.JsonNode;

public interface UserProfileService {
    void registerUserProfile(JsonNode payload);
}
