package com.jobconnect.controller;

import com.jobconnect.dto.JobResponse;
import com.jobconnect.enums.JobType;
import com.jobconnect.service.PublicJobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * PublicJobController
 * REST Controller exposing publicly accessible endpoints for job browsing and search operations.
 */
@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class PublicJobController {

    private final PublicJobService publicJobService;

    /**
     * GET /api/jobs
     * Retrieves all OPEN job postings, optionally filtered by job type.
     */
    @GetMapping
    public ResponseEntity<List<JobResponse>> getAllJobs(
            @RequestParam(required = false) JobType jobType
    ) {
        List<JobResponse> response = publicJobService.getAllJobs(jobType);
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/jobs/{jobId}
     * Retrieves a single job posting by its ID.
     */
    @GetMapping("/{jobId}")
    public ResponseEntity<JobResponse> getJobById(@PathVariable Long jobId) {
        JobResponse response = publicJobService.getJobById(jobId);
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/jobs/search/title
     * Searches for OPEN job postings by title keyword, optionally filtered by job type.
     */
    @GetMapping("/search/title")
    public ResponseEntity<List<JobResponse>> searchByTitle(
            @RequestParam String title,
            @RequestParam(required = false) JobType jobType
    ) {
        List<JobResponse> response = publicJobService.searchByTitle(title, jobType);
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/jobs/search/location
     * Searches for OPEN job postings by location keyword, optionally filtered by job type.
     */
    @GetMapping("/search/location")
    public ResponseEntity<List<JobResponse>> searchByLocation(
            @RequestParam String location,
            @RequestParam(required = false) JobType jobType
    ) {
        List<JobResponse> response = publicJobService.searchByLocation(location, jobType);
        return ResponseEntity.ok(response);
    }
}
