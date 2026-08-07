package com.jobconnect.dto;

import com.jobconnect.enums.JobStatus;
import com.jobconnect.enums.JobType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * JobResponse DTO
 * Data Transfer Object returning job posting details in API responses.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobResponse {

    private Long id;
    private String title;
    private String description;
    private String location;
    private JobType jobType;
    private String salary;
    private JobStatus status;
    private String companyName;
    private LocalDateTime createdAt;
}
