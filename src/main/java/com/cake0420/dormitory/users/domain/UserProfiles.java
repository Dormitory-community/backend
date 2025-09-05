package com.cake0420.dormitory.users.domain;

import com.cake0420.dormitory.baseEntity.BaseEntity;
import com.cake0420.dormitory.users.domain.enums.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "user_profiles",
        uniqueConstraints = @UniqueConstraint(name = "uk_user_profile_supabase_id", columnNames = "supabase_id")
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UserProfiles extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 36)
    private String id;

    @Column(length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(length = 10, nullable = false)
    private String name;

    @Column(name = "supabase_id", length = 36, nullable = false)
    private String supabase_id;

    @Column(name = "user_profile_url", length = 255, nullable = false)
    private String UserProfileUrl;
}
