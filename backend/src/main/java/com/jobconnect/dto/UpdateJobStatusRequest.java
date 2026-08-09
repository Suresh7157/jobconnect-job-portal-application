package com.jobconnect.dto;

import com.jobconnect.enums.JobStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UpdateJobStatusRequest DTO
 * Data Transfer Object capturing the job status update payload.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateJobStatusRequest {

    @NotNull(message = "Job status is required")
    private JobStatus status;
}
