package com.cake0420.dormitory.posts.mapping;

import com.cake0420.dormitory.baseEntity.BaseEntity;
import com.cake0420.dormitory.posts.domain.enums.PostCategoryName;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "post_categories")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCategories extends BaseEntity  {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 36)
    private String id;

    @Column(name = "category_name", length = 10)
    @Enumerated(EnumType.STRING)
    private PostCategoryName categoryName;
}
