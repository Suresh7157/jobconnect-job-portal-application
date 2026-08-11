package com.jobconnect.controller;

import com.jobconnect.dto.AdminUserResponse;
import com.jobconnect.dto.ApplicationResponse;
import com.jobconnect.dto.JobResponse;
import com.jobconnect.dto.UpdateApplicationStatusRequest;
import com.jobconnect.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * AdminController
 * REST Controller exposing administrative endpoints for managing users, jobs, and applications.
 */
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    /**
     * GET /api/admin/users
     * Retrieves all registered users in the system.
     */
    @GetMapping("/users")
    public ResponseEntity<List<AdminUserResponse>> getAllUsers() {
        List<AdminUserResponse> response = adminService.getAllUsers();
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/admin/jobs
     * Retrieves all job postings in the system.
     */
    @GetMapping("/jobs")
    public ResponseEntity<List<JobResponse>> getAllJobs() {
        List<JobResponse> response = adminService.getAllJobs();
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/admin/applications
     * Retrieves all job applications in the system.
     */
    @GetMapping("/applications")
    public ResponseEntity<List<ApplicationResponse>> getAllApplications() {
        List<ApplicationResponse> response = adminService.getAllApplications();
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/admin/applications/{applicationId}
     * Retrieves a single job application by its ID.
     */
    @GetMapping("/applications/{applicationId}")
    public ResponseEntity<ApplicationResponse> getApplicationById(@PathVariable Long applicationId) {
        ApplicationResponse response = adminService.getApplicationById(applicationId);
        return ResponseEntity.ok(response);
    }

    /**
     * PUT /api/admin/applications/{applicationId}/status
     * Updates the status of the given application.
     */
    @PutMapping("/applications/{applicationId}/status")
    public ResponseEntity<ApplicationResponse> updateApplicationStatus(
            @PathVariable Long applicationId,
            @Valid @RequestBody UpdateApplicationStatusRequest request
    ) {
        ApplicationResponse response = adminService.updateApplicationStatus(applicationId, request);
        return ResponseEntity.ok(response);
    }

    /**
     * DELETE /api/admin/applications/{applicationId}
     * Deletes a single job application by its ID.
     */
    @DeleteMapping("/applications/{applicationId}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long applicationId) {
        adminService.deleteApplication(applicationId);
        return ResponseEntity.noContent().build();
    }

    /**
     * DELETE /api/admin/users/{userId}
     * Deletes a user and all their associated data.
     */
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        adminService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    /**
     * DELETE /api/admin/jobs/{jobId}
     * Deletes a job posting and all its associated applications.
     */
    @DeleteMapping("/jobs/{jobId}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long jobId) {
        adminService.deleteJob(jobId);
        return ResponseEntity.noContent().build();
    }
}
