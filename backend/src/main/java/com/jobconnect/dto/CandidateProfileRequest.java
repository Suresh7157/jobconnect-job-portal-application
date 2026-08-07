package com.jobconnect.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CandidateProfileRequest DTO
 * Data Transfer Object capturing candidate profile creation or update payload.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateProfileRequest {

    @NotBlank(message = "Full name is required")
    private String fullName;

    @Size(max = 20)
    private String phone;

    @Size(max = 150)
    private String headline;

    private Integer experienceYears;

    @Size(max = 255)
    private String skills;

    @Size(max = 255)
    private String resumeUrl;
}
