package com.jobconnect.dto;

import com.jobconnect.enums.RoleName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * RegisterRequest DTO
 * Data Transfer Object capturing user registration payload.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @NotNull(message = "Role is required")
    private RoleName role;

    @NotBlank(message = "Full name is required")
    private String fullName;

    private String phone;

    // Optional fields for Recruiter role registration
    private String companyName;
    private String companyWebsite;
    private String designation;
}
