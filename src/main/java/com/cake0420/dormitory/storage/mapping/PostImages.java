package com.cake0420.dormitory.storage.mapping;

import com.cake0420.dormitory.baseEntity.BaseEntity;
import com.cake0420.dormitory.storage.domain.Images;
import com.cake0420.dormitory.users.domain.UserProfiles;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "post_images")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class PostImages extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 36)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_post_images_image_id"))
    private Images image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_post_images_user_id"))
    private UserProfiles user;
}
