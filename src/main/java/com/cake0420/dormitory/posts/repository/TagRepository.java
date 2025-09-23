package com.cake0420.dormitory.posts.repository;

import com.cake0420.dormitory.posts.mapping.Tags;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tags, String> {
    Optional<Tags> findByTag(String tag);
}
