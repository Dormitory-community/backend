package com.cake0420.dormitory.users.repository;

import com.cake0420.dormitory.users.domain.UserProfiles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfiles, String> {

    Optional<UserProfiles> findBySupabaseId(String supabaseId);
}
