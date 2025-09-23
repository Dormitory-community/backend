package com.cake0420.dormitory.posts.service.impl;

import com.cake0420.dormitory.global.exception.TokenException;
import com.cake0420.dormitory.posts.domain.Posts;
import com.cake0420.dormitory.posts.domain.dto.CreatePostDTO;
import com.cake0420.dormitory.posts.domain.dto.DeletePostDTO;
import com.cake0420.dormitory.posts.domain.dto.PostResponseDTO;
import com.cake0420.dormitory.posts.domain.dto.UpdatePostDTO;
import com.cake0420.dormitory.posts.mapping.PostTagMap;
import com.cake0420.dormitory.posts.mapping.Tags;
import com.cake0420.dormitory.posts.repository.PostRepository;
import com.cake0420.dormitory.posts.repository.PostTagMapRepository;
import com.cake0420.dormitory.posts.repository.TagRepository;
import com.cake0420.dormitory.posts.service.PostService;
import com.cake0420.dormitory.users.domain.UserProfiles;
import com.cake0420.dormitory.users.repository.UserProfileRepository;
import com.cake0420.dormitory.users.service.SupabaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final UserProfileRepository userProfileRepository;
    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final PostTagMapRepository postTagMapRepository;
    private final SupabaseService supabaseService;

    @Override
    @Transactional
    public void CreatePost(String token, CreatePostDTO dto) {
        Mono<String> email =  supabaseService.validateToken(token);
        email.subscribe(userEmail -> {
                UserProfiles userProfiles = userProfileRepository.findByEmail(userEmail)
                        .orElseThrow(() -> new NoSuchElementException("존배하지 않는 이메일입니다."));
                Posts posts = Posts.builder()
                        .title(dto.title())
                        .content(dto.content())
                        .anonymity(dto.anonymity())
                        .location(dto.location())
                        .user(userProfiles)
                        .build();
                Posts savedPosts = postRepository.save(posts);
                dto.tags().stream()
                        .map(tagName -> tagRepository.findByTag(tagName)
                                .orElseGet(() -> tagRepository.save(
                                        Tags.builder().tag(tagName).build()
                                )))
                        .forEach(tag -> postTagMapRepository.save(
                                PostTagMap.builder()
                                        .post(savedPosts)
                                        .tags(tag)
                                        .build()
                        ));

        },error -> {
            log.error("토큰 검증 중 오류 발생: " + error.getMessage());
            throw new TokenException(error.getMessage());
        });
    }

    @Override
    public void UpdatePost(String token, UpdatePostDTO dto) {

    }

    @Override
    public void DeletePost(String token, DeletePostDTO dto) {

    }

    @Override
    @Transactional(readOnly = true)
    public Page<PostResponseDTO> GetPost(int page, int size) {
        return null;
    }
}
