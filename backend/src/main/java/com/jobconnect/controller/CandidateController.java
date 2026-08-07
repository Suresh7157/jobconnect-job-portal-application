package com.jobconnect.controller;

import com.jobconnect.dto.CandidateProfileRequest;
import com.jobconnect.dto.CandidateProfileResponse;
import com.jobconnect.service.CandidateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * CandidateController
 * REST Controller exposing endpoints for candidate profile retrieval and update operations.
 */
@RestController
@RequestMapping("/api/candidate")
@RequiredArgsConstructor
public class CandidateController {

    private final CandidateService candidateService;

    /**
     * GET /api/candidate/profile/{userId}
     * Retrieves the candidate profile for the given user ID.
     */
    @GetMapping("/profile/{userId}")
    public ResponseEntity<CandidateProfileResponse> getMyProfile(@PathVariable Long userId) {
        CandidateProfileResponse response = candidateService.getMyProfile(userId);
        return ResponseEntity.ok(response);
    }

    /**
     * PUT /api/candidate/profile/{userId}
     * Updates the candidate profile for the given user ID.
     */
    @PutMapping("/profile/{userId}")
    public ResponseEntity<CandidateProfileResponse> updateMyProfile(
            @PathVariable Long userId,
            @Valid @RequestBody CandidateProfileRequest request
    ) {
        CandidateProfileResponse response = candidateService.updateMyProfile(userId, request);
        return ResponseEntity.ok(response);
    }
}
