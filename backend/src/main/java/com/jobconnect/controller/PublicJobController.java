package com.jobconnect.controller;

import com.jobconnect.dto.JobResponse;
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
     * GET /api/jobs/
     * Retrieves all available job postings.
     */
    @GetMapping
    public ResponseEntity<List<JobResponse>> getAllJobs() {
        List<JobResponse> response = publicJobService.getAllJobs();
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
     * Searches for job postings by title keyword.
     */
    @GetMapping("/search/title")
    public ResponseEntity<List<JobResponse>> searchByTitle(@RequestParam String title) {
        List<JobResponse> response = publicJobService.searchByTitle(title);
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/jobs/search/location
     * Searches for job postings by location keyword.
     */
    @GetMapping("/search/location")
    public ResponseEntity<List<JobResponse>> searchByLocation(@RequestParam String location) {
        List<JobResponse> response = publicJobService.searchByLocation(location);
        return ResponseEntity.ok(response);
    }
}
