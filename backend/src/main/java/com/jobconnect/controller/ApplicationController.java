package com.jobconnect.controller;

import com.jobconnect.dto.ApplicationRequest;
import com.jobconnect.dto.ApplicationResponse;
import com.jobconnect.dto.UpdateApplicationStatusRequest;
import com.jobconnect.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ApplicationController
 * REST Controller exposing endpoints for job application submission and retrieval operations.
 */
@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    /**
     * POST /api/applications/candidate/{userId}
     * Submits a job application for the given candidate user ID.
     */
    @PostMapping("/candidate/{userId}")
    public ResponseEntity<ApplicationResponse> applyForJob(
            @PathVariable Long userId,
            @Valid @RequestBody ApplicationRequest request
    ) {
        ApplicationResponse response = applicationService.applyForJob(userId, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * GET /api/applications/candidate/{userId}
     * Retrieves all job applications submitted by the given candidate user ID.
     */
    @GetMapping("/candidate/{userId}")
    public ResponseEntity<List<ApplicationResponse>> getMyApplications(@PathVariable Long userId) {
        List<ApplicationResponse> response = applicationService.getMyApplications(userId);
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/applications/recruiter/job/{jobId}
     * Retrieves all applicants for the given job ID.
     */
    @GetMapping("/recruiter/job/{jobId}")
    public ResponseEntity<List<ApplicationResponse>> getApplicantsForJob(@PathVariable Long jobId) {
        List<ApplicationResponse> response = applicationService.getApplicantsForJob(jobId);
        return ResponseEntity.ok(response);
    }

    /**
     * PUT /api/applications/{applicationId}/status
     * Updates the status of the given application ID.
     */
    @PutMapping("/{applicationId}/status")
    public ResponseEntity<ApplicationResponse> updateApplicationStatus(
            @PathVariable Long applicationId,
            @Valid @RequestBody UpdateApplicationStatusRequest request
    ) {
        ApplicationResponse response = applicationService.updateApplicationStatus(applicationId, request);
        return ResponseEntity.ok(response);
    }
}
