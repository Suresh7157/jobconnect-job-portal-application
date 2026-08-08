package com.jobconnect.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CandidateDashboardResponse DTO
 * Data Transfer Object returning aggregated application statistics for a candidate's dashboard.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateDashboardResponse {

    private long totalApplications;
    private long applied;
    private long shortlisted;
    private long hired;
    private long rejected;
}
