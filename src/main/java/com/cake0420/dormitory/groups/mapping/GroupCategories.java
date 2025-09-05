package com.cake0420.dormitory.groups.mapping;

import com.cake0420.dormitory.baseEntity.BaseEntity;
import com.cake0420.dormitory.groups.domain.enums.GroupCategoryName;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "group_categories")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupCategories extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 36)
    private String id;

    @Column(name = "category_name", length = 11)
    private GroupCategoryName categoryName;
}
