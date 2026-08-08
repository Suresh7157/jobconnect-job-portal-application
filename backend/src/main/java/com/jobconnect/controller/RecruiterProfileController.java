package com.jobconnect.controller;

import com.jobconnect.dto.RecruiterProfileRequest;
import com.jobconnect.dto.RecruiterProfileResponse;
import com.jobconnect.service.RecruiterProfileService;
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
 * RecruiterProfileController
 * REST Controller exposing endpoints for recruiter profile retrieval and update operations.
 */
@RestController
@RequestMapping("/api/recruiter/profile")
@RequiredArgsConstructor
public class RecruiterProfileController {

    private final RecruiterProfileService recruiterProfileService;

    /**
     * GET /api/recruiter/profile/{userId}
     * Retrieves the recruiter profile for the given user ID.
     */
    @GetMapping("/{userId}")
    public ResponseEntity<RecruiterProfileResponse> getMyProfile(@PathVariable Long userId) {
        RecruiterProfileResponse response = recruiterProfileService.getMyProfile(userId);
        return ResponseEntity.ok(response);
    }

    /**
     * PUT /api/recruiter/profile/{userId}
     * Updates the recruiter profile for the given user ID.
     */
    @PutMapping("/{userId}")
    public ResponseEntity<RecruiterProfileResponse> updateMyProfile(
            @PathVariable Long userId,
            @Valid @RequestBody RecruiterProfileRequest request
    ) {
        RecruiterProfileResponse response = recruiterProfileService.updateMyProfile(userId, request);
        return ResponseEntity.ok(response);
    }
}
