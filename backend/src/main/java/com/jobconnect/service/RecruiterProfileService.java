package com.jobconnect.service;

import com.jobconnect.dto.RecruiterProfileRequest;
import com.jobconnect.dto.RecruiterProfileResponse;
import com.jobconnect.entity.RecruiterProfile;
import com.jobconnect.repository.RecruiterProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * RecruiterProfileService
 * Business service handling recruiter profile retrieval and update operations.
 */
@Service
@RequiredArgsConstructor
public class RecruiterProfileService {

    private final RecruiterProfileRepository recruiterProfileRepository;

    /**
     * Retrieves the recruiter profile for the given user ID.
     *
     * @param userId the ID of the authenticated user
     * @return RecruiterProfileResponse containing the recruiter's profile details
     * @throws RuntimeException if no recruiter profile is found for the given user ID
     */
    public RecruiterProfileResponse getMyProfile(Long userId) {
        RecruiterProfile profile = recruiterProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Recruiter profile not found."));

        return mapToResponse(profile);
    }

    /**
     * Updates the recruiter profile for the given user ID with the provided request data.
     *
     * @param userId  the ID of the authenticated user
     * @param request the updated profile data
     * @return RecruiterProfileResponse containing the updated recruiter's profile details
     * @throws RuntimeException if no recruiter profile is found for the given user ID
     */
    @Transactional
    public RecruiterProfileResponse updateMyProfile(Long userId, RecruiterProfileRequest request) {
        RecruiterProfile profile = recruiterProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Recruiter profile not found."));

        profile.setCompanyName(request.getCompanyName());
        profile.setCompanyWebsite(request.getCompanyWebsite());
        profile.setDesignation(request.getDesignation());

        RecruiterProfile savedProfile = recruiterProfileRepository.save(profile);

        return mapToResponse(savedProfile);
    }

    /**
     * Maps a RecruiterProfile entity to a RecruiterProfileResponse DTO.
     *
     * @param profile the RecruiterProfile entity
     * @return the mapped RecruiterProfileResponse
     */
    private RecruiterProfileResponse mapToResponse(RecruiterProfile profile) {
        return RecruiterProfileResponse.builder()
                .id(profile.getId())
                .email(profile.getUser().getEmail())
                .companyName(profile.getCompanyName())
                .companyWebsite(profile.getCompanyWebsite())
                .designation(profile.getDesignation())
                .build();
    }
}
