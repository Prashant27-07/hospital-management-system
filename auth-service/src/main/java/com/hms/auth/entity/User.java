package com.hms.auth.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false, unique = true, length = 100)
    private String username;

    // Never expose this field in any DTO. See UserResponse.
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    // Role kept as a plain code (e.g. "ADMIN", "DOCTOR") rather than a JPA
    // relation to the Role entity, so that other services which only need the
    // role name (for authorization) don't have to reach into auth-service's schema.
    @Column(nullable = false, length = 30)
    private String role;

    @Column(nullable = false, length = 20)
    @Builder.Default
    private String status = "ACTIVE";

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    void onCreate() {
        this.createdAt = Instant.now();
    }
}
