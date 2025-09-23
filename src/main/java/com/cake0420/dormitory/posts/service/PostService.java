package com.cake0420.dormitory.posts.service;

import com.cake0420.dormitory.posts.domain.dto.CreatePostDTO;
import com.cake0420.dormitory.posts.domain.dto.DeletePostDTO;
import com.cake0420.dormitory.posts.domain.dto.PostResponseDTO;
import com.cake0420.dormitory.posts.domain.dto.UpdatePostDTO;
import org.springframework.data.domain.Page;

public interface PostService {
    void CreatePost(String token, CreatePostDTO dto);
    void UpdatePost(String token, UpdatePostDTO dto);
    void DeletePost(String token, DeletePostDTO dto);
    Page<PostResponseDTO> GetPost(int page, int size);
}
