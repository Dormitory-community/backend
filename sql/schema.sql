CREATE TABLE user_profiles(
                              id VARCHAR(36) PRIMARY KEY,
                              role VARCHAR(10) NOT NULL,
                              name VARCHAR(100) NOT NULL ,
                              user_profile_url VARCHAR(255) NOT NULL,
                              supabase_id VARCHAR(36) NOT NULL,
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              CONSTRAINT uk_user_profile_supabase_id UNIQUE (supabase_id)
);


CREATE TABLE post_categories (
                                 id VARCHAR(36) PRIMARY KEY,
                                 category_name VARCHAR(100) ,
                                 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                 updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE post_tags (
                           id VARCHAR(36) PRIMARY KEY,
                           post_id VARCHAR(36) NOT NULL ,
                           tag VARCHAR(10) NOT NULL ,
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           CONSTRAINT fk_post_tags_post_id FOREIGN KEY (post_id) REFERENCES posts(id)
);

CREATE TABLE posts (
                       id VARCHAR(36) PRIMARY KEY ,
                       user_id VARCHAR(36) NOT NULL ,
                       title VARCHAR(50) NOT NULL,
                       content VARCHAR(1000) ,
                       category_id VARCHAR(36) NOT NULL ,
                       location VARCHAR(50) ,
                       like_count BIGINT,
                       bookmark_count BIGINT,
                       views BIGINT,
                       anonymity BOOLEAN NOT NULL ,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       CONSTRAINT fk_posts_caterory_id FOREIGN KEY (category_id) REFERENCES post_categories(id) ,
                       CONSTRAINT fk_posts_user_id FOREIGN KEY (user_id) REFERENCES user_profiles(id)
);

CREATE TABLE comments (
                          id VARCHAR(36) PRIMARY KEY ,
                          post_id VARCHAR(36) NOT NULL ,
                          user_id VARCHAR(36) NOT NULL ,
                          name VARCHAR(100) ,
                          content VARCHAR(1000) NOT NULL ,
                          order_number BIGINT,
                          is_parent BOOLEAN NOT NULL DEFAULT FALSE,
                          anonymity BOOLEAN NOT NULL ,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          CONSTRAINT fk_comments_post_id FOREIGN KEY (post_id) REFERENCES posts(id),
                          CONSTRAINT fk_comments_user_id FOREIGN KEY (user_id) REFERENCES user_profiles(id)
);


CREATE TABLE group_cartegories (
                                   id VARCHAR(36) PRIMARY KEY,
                                   category_name VARCHAR(100) ,
                                   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE group_recruitments (
                                    id VARCHAR(36) PRIMARY KEY ,
                                    title VARCHAR(50) NOT NULL ,
                                    content VARCHAR(1000) NOT NULL ,
                                    name VARCHAR(100),
                                    category_id VARCHAR(36) NOT NULL ,
                                    start_date DATE,
                                    end_date DATE,
                                    location VARCHAR(100) ,
                                    max_participant INT,
                                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                    CONSTRAINT fk_group_recruitments_category_id FOREIGN KEY (category_id) REFERENCES group_cartegories(id)
);

CREATE TABLE likes (
                       id VARCHAR(36) PRIMARY KEY ,
                       post_id VARCHAR(36) NOT NULL ,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       CONSTRAINT fk_likes_post_id FOREIGN KEY (post_id) REFERENCES posts(id)
);

CREATE TABLE bookmarks (
                           id VARCHAR(36) PRIMARY KEY ,
                           post_id VARCHAR(36) NOT NULL ,
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           CONSTRAINT fk_bookmarks_post_id FOREIGN KEY (post_id) REFERENCES posts(id)
);

CREATE TABLE temp_images (
    id VARCHAR(36) PRIMARY KEY ,
    user_id VARCHAR(36) NOT NULL ,
    image_url VARCHAR(255) NOT NULL ,
    owner_type ENUM('POST', 'GROUP') NOT NULL ,
    status ENUM('TEMP', 'USED') DEFAULT 'TEMP',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_temp_images_user_id FOREIGN KEY (user_id) REFERENCES user_profiles(id)
);

CREATE TABLE images (
    id VARCHAR(36) PRIMARY KEY ,
    user_id VARCHAR(36) NOT NULL ,
    image_url VARCHAR(255) NOT NULL ,
    owner_type ENUM('POST', 'GROUP') NOT NULL ,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_images_user_id FOREIGN KEY (user_id) REFERENCES user_profiles(id)

);


CREATE TABLE post_images (
    id VARCHAR(36) PRIMARY KEY ,
    image_id VARCHAR(36) NOT NULL ,
    user_id VARCHAR(36) NOT NULL ,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_post_images_image_id FOREIGN KEY (image_id) REFERENCES images(id),
    CONSTRAINT fk_post_images_user_id FOREIGN KEY (user_id) REFERENCES user_profiles(id)

);

CREATE TABLE group_images (
    id VARCHAR(36) PRIMARY KEY ,
    image_id VARCHAR(36) NOT NULL ,
    user_id VARCHAR(36) NOT NULL ,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_group_images_image_id FOREIGN KEY (image_id) REFERENCES images(id),
    CONSTRAINT fk_group_images_user_id FOREIGN KEY (user_id) REFERENCES user_profiles(id)

);


CREATE TABLE post_reports (
    id VARCHAR(36) PRIMARY KEY ,
    post_id VARCHAR(36) NOT NULL ,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_post_reports_post_id FOREIGN KEY (post_id) REFERENCES posts(id)
);

CREATE TABLE comment_reports (
    id VARCHAR(36) PRIMARY KEY ,
    comment_id VARCHAR(36) NOT NULL ,
    is_parent BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_comment_reports_comment_id FOREIGN KEY (comment_id) REFERENCES comments(id)
);
