package com.jobconnect.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CandidateProfileResponse DTO
 * Data Transfer Object returning candidate profile details in API responses.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateProfileResponse {

    private Long id;
    private String email;
    private String fullName;
    private String phone;
    private String headline;
    private Integer experienceYears;
    private String skills;
    private String resumeUrl;
}
