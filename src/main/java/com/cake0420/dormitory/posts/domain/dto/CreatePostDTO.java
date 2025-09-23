package com.cake0420.dormitory.posts.domain.dto;

import com.cake0420.dormitory.posts.domain.enums.PostCategoryName;

import java.util.List;

public record CreatePostDTO(String title,
                            String content,
                            PostCategoryName postCategoryName,
                            String location,
                            Boolean anonymity,
                            List<String> tags) {
}
