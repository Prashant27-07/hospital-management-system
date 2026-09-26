package com.hms.auth.dto;

import lombok.*;

import java.time.Instant;

// Deliberately excludes passwordHash.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private Long userId;
    private String username;
    private String role;
    private String status;
    private Instant createdAt;
}
