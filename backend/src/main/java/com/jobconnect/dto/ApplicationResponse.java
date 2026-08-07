package com.jobconnect.dto;

import com.jobconnect.enums.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * ApplicationResponse DTO
 * Data Transfer Object returning job application details in API responses.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationResponse {

    private Long applicationId;
    private Long jobId;
    private String jobTitle;
    private String candidateName;
    private String recruiterCompany;
    private ApplicationStatus status;
    private LocalDateTime appliedAt;
}
