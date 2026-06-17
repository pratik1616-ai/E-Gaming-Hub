package com.egaming.clan.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "clan_members")
@UniqueConstraint(columnNames = {"clan_id", "user_id"})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClanMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clan_id", nullable = false)
    private Clan clan;

    @Column(nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberRole role; // OWNER, ADMIN, MEMBER

    private String joinStatus; // MEMBER, PENDING_INVITE, PENDING_APPROVAL

    private Integer contributionPoints = 0;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime joinedAt;

    private LocalDateTime leftAt;

    public enum MemberRole {
        OWNER, ADMIN, MEMBER
    }
}
