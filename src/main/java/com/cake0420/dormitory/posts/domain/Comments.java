package com.cake0420.dormitory.posts.domain;

import com.cake0420.dormitory.baseEntity.BaseEntity;
import com.cake0420.dormitory.users.domain.UserProfiles;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Comments extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 36)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_comments_post_id"))
    private Posts post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_comments_user_id"))
    private UserProfiles user;

    @Column(length = 10)
    private String name;

    @Column(length = 1000, nullable = false)
    private String content;

    @Column(name = "order_number")
    private Long orderNumber;

    @Column(name = "is_parent", nullable = false)
    private Boolean isParent = false;

    @Column(nullable = false)
    private Boolean anonymity;
}
