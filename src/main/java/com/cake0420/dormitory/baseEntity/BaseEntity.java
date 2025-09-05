package com.cake0420.dormitory.baseEntity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @CreatedDate
    @Column(updatable = false, nullable = false, name = "created_at")
    private Instant createdAt = Instant.now();

    @LastModifiedDate
    @Column(nullable = false, name = "updated_at")
    private Instant updatedAt = Instant.now();

}