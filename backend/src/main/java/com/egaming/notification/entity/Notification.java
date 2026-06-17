package com.egaming.notification.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType type;

    private Long relatedEntityId;

    private String relatedEntityType; // EVENT, STREAM, MESSAGE, FRIEND_REQUEST, etc.

    private String actionUrl;

    private Boolean isRead = false;

    private LocalDateTime readAt;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public enum NotificationType {
        EVENT_STARTING,
        EVENT_ENDED,
        NEW_FOLLOWER,
        STREAM_STARTED,
        NEW_MESSAGE,
        TOURNAMENT_RESULT,
        FANTASY_RESULT,
        FRIEND_REQUEST,
        CLAN_INVITATION,
        ACHIEVEMENT_UNLOCKED,
        REFERRAL_BONUS,
        WITHDRAWAL_STATUS,
        PAYMENT_STATUS
    }
}
