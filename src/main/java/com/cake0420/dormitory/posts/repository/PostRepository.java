package com.cake0420.dormitory.posts.repository;

import com.cake0420.dormitory.posts.domain.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Posts, String> {
}
