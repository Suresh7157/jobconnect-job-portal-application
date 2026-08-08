package com.jobconnect.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * RecruiterProfileRequest DTO
 * Data Transfer Object capturing recruiter profile creation or update payload.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecruiterProfileRequest {

    @NotBlank(message = "Company name is required")
    @Size(max = 100)
    private String companyName;

    @Size(max = 150)
    private String companyWebsite;

    @Size(max = 100)
    private String designation;
}
