package com.jobconnect.controller;

import com.jobconnect.dto.CandidateDashboardResponse;
import com.jobconnect.dto.RecruiterDashboardResponse;
import com.jobconnect.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * DashboardController
 * REST Controller exposing endpoints for candidate and recruiter dashboard statistics.
 */
@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    /**
     * GET /api/dashboard/candidate/{userId}
     * Retrieves aggregated application statistics for the given candidate user ID.
     */
    @GetMapping("/candidate/{userId}")
    public ResponseEntity<CandidateDashboardResponse> getCandidateDashboard(@PathVariable Long userId) {
        CandidateDashboardResponse response = dashboardService.getCandidateDashboard(userId);
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/dashboard/recruiter/{userId}
     * Retrieves aggregated job and applicant statistics for the given recruiter user ID.
     */
    @GetMapping("/recruiter/{userId}")
    public ResponseEntity<RecruiterDashboardResponse> getRecruiterDashboard(@PathVariable Long userId) {
        RecruiterDashboardResponse response = dashboardService.getRecruiterDashboard(userId);
        return ResponseEntity.ok(response);
    }
}
