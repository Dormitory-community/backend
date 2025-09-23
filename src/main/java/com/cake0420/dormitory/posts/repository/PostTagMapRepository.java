package com.cake0420.dormitory.posts.repository;

import com.cake0420.dormitory.posts.mapping.PostTagMap;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostTagMapRepository extends JpaRepository<PostTagMap, String> {
}
