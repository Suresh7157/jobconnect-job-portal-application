package com.jobconnect.dto;

import com.jobconnect.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * AdminUserResponse DTO
 * Data Transfer Object returning user details in admin API responses.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminUserResponse {

    private Long id;
    private String email;
    private RoleName role;
    private LocalDateTime createdAt;
}
