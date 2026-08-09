package com.jobconnect.controller;

import com.jobconnect.dto.JobRequest;
import com.jobconnect.dto.JobResponse;
import com.jobconnect.dto.UpdateJobStatusRequest;
import com.jobconnect.security.CustomUserDetails;
import com.jobconnect.service.JobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * JobController
 * REST Controller exposing endpoints for recruiter job posting management.
 */
@RestController
@RequestMapping("/api/recruiter/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    /**
     * POST /api/recruiter/jobs/{userId}
     * Creates a new job posting for the given recruiter user ID.
     */
    @PostMapping("/{userId}")
    public ResponseEntity<JobResponse> createJob(
            @AuthenticationPrincipal CustomUserDetails principal,
            @PathVariable Long userId,
            @Valid @RequestBody JobRequest request
    ) {
        if (!principal.getId().equals(userId)) {
            throw new AccessDeniedException("Access denied.");
        }
        JobResponse response = jobService.createJob(userId, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * GET /api/recruiter/jobs/{userId}
     * Retrieves all job postings belonging to the given recruiter user ID.
     */
    @GetMapping("/{userId}")
    public ResponseEntity<List<JobResponse>> getMyJobs(
            @AuthenticationPrincipal CustomUserDetails principal,
            @PathVariable Long userId
    ) {
        if (!principal.getId().equals(userId)) {
            throw new AccessDeniedException("Access denied.");
        }
        List<JobResponse> response = jobService.getMyJobs(userId);
        return ResponseEntity.ok(response);
    }

    /**
     * PUT /api/recruiter/jobs/{userId}/{jobId}
     * Updates an existing job posting owned by the given recruiter user ID.
     */
    @PutMapping("/{userId}/{jobId}")
    public ResponseEntity<JobResponse> updateJob(
            @AuthenticationPrincipal CustomUserDetails principal,
            @PathVariable Long userId,
            @PathVariable Long jobId,
            @Valid @RequestBody JobRequest request
    ) {
        if (!principal.getId().equals(userId)) {
            throw new AccessDeniedException("Access denied.");
        }
        JobResponse response = jobService.updateJob(userId, jobId, request);
        return ResponseEntity.ok(response);
    }

    /**
     * DELETE /api/recruiter/jobs/{userId}/{jobId}
     * Deletes a job posting owned by the given recruiter user ID.
     */
    @DeleteMapping("/{userId}/{jobId}")
    public ResponseEntity<Void> deleteJob(
            @AuthenticationPrincipal CustomUserDetails principal,
            @PathVariable Long userId,
            @PathVariable Long jobId
    ) {
        if (!principal.getId().equals(userId)) {
            throw new AccessDeniedException("Access denied.");
        }
        jobService.deleteJob(userId, jobId);
        return ResponseEntity.noContent().build();
    }

    /**
     * PATCH /api/recruiter/jobs/{userId}/{jobId}/status
     * Updates the status of a job posting owned by the given recruiter user ID.
     */
    @PatchMapping("/{userId}/{jobId}/status")
    public ResponseEntity<JobResponse> updateJobStatus(
            @AuthenticationPrincipal CustomUserDetails principal,
            @PathVariable Long userId,
            @PathVariable Long jobId,
            @Valid @RequestBody UpdateJobStatusRequest request
    ) {
        if (!principal.getId().equals(userId)) {
            throw new AccessDeniedException("Access denied.");
        }
        JobResponse response = jobService.updateJobStatus(userId, jobId, request);
        return ResponseEntity.ok(response);
    }
}
