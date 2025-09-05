package com.cake0420.dormitory.storage.domain;

import com.cake0420.dormitory.baseEntity.BaseEntity;
import com.cake0420.dormitory.storage.domain.enums.OwnerType;
import com.cake0420.dormitory.users.domain.UserProfiles;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "images")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Images extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 36)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_images_user_id"))
    private UserProfiles user;

    @Column(name = "image_url", length = 255, nullable = false)
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "owner_type", nullable = false)
    private OwnerType ownerType;
}
