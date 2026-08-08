package com.jobconnect.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * RecruiterProfileResponse DTO
 * Data Transfer Object returning recruiter profile details in API responses.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecruiterProfileResponse {

    private Long id;
    private String email;
    private String companyName;
    private String companyWebsite;
    private String designation;
}
