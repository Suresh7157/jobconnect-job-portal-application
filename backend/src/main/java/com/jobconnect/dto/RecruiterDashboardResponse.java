package com.jobconnect.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * RecruiterDashboardResponse DTO
 * Data Transfer Object returning aggregated job and applicant statistics for a recruiter's dashboard.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecruiterDashboardResponse {

    private long totalJobs;
    private long totalApplicants;
    private long shortlistedCandidates;
    private long hiredCandidates;
}
