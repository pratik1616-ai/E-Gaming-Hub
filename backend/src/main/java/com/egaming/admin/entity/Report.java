package com.egaming.admin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "reports")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long reporterId;

    private Long reportedUserId;

    private Long reportedPostId;

    private Long reportedCommentId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportReason reason;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportStatus status; // PENDING, UNDER_REVIEW, RESOLVED, DISMISSED

    private Long resolverId;

    @Column(columnDefinition = "TEXT")
    private String resolutionNote;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime resolvedAt;

    public enum ReportType {
        USER, POST, COMMENT, STREAM
    }

    public enum ReportReason {
        INAPPROPRIATE_CONTENT,
        HARASSMENT,
        HATE_SPEECH,
        SPAM,
        FRAUD,
        CHEATING,
        SELF_HARM,
        OTHER
    }

    public enum ReportStatus {
        PENDING, UNDER_REVIEW, RESOLVED, DISMISSED
    }
}
