package com.egaming.community.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "likes")
@UniqueConstraint(columnNames = {"user_id", "post_id", "likeable_type"})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    private Long postId;

    private Long commentId;

    private Long streamId;

    @Column(nullable = false)
    private String likeableType; // POST, COMMENT, STREAM, HIGHLIGHT

    @CreationTimestamp
    private LocalDateTime createdAt;
}
