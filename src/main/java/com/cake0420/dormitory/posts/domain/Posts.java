package com.cake0420.dormitory.posts.domain;

import com.cake0420.dormitory.baseEntity.BaseEntity;
import com.cake0420.dormitory.posts.mapping.PostCategories;
import com.cake0420.dormitory.users.domain.UserProfiles;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Table(name = "posts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Posts extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 36)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_posts_user_id"))
    private UserProfiles user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_posts_category_id"))
    private PostCategories category;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(length = 1000)
    private String content;

    @Column(length = 50)
    private String location;

    @Column(name = "like_count")
    @Builder.Default
    private Long likeCount = 0L;

    @Column(name = "bookmark_count")
    @Builder.Default
    private Long bookmarkCount = 0L;

    @Column(name = "views")
    @Builder.Default
    private Long views = 0L;

    @Column(nullable = false)
    private Boolean anonymity;

}
