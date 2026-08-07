package com.jobconnect.service;

import com.jobconnect.dto.CandidateProfileRequest;
import com.jobconnect.dto.CandidateProfileResponse;
import com.jobconnect.entity.CandidateProfile;
import com.jobconnect.repository.CandidateProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * CandidateService
 * Business service handling candidate profile retrieval and update operations.
 */
@Service
@RequiredArgsConstructor
public class CandidateService {

    private final CandidateProfileRepository candidateProfileRepository;

    /**
     * Retrieves the candidate profile for the given user ID.
     *
     * @param userId the ID of the authenticated user
     * @return CandidateProfileResponse containing the candidate's profile details
     * @throws RuntimeException if no candidate profile is found for the given user ID
     */
    public CandidateProfileResponse getMyProfile(Long userId) {
        CandidateProfile profile = candidateProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Candidate profile not found."));

        return mapToResponse(profile);
    }

    /**
     * Updates the candidate profile for the given user ID with the provided request data.
     *
     * @param userId  the ID of the authenticated user
     * @param request the updated profile data
     * @return CandidateProfileResponse containing the updated candidate's profile details
     * @throws RuntimeException if no candidate profile is found for the given user ID
     */
    @Transactional
    public CandidateProfileResponse updateMyProfile(Long userId, CandidateProfileRequest request) {
        CandidateProfile profile = candidateProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Candidate profile not found."));

        profile.setFullName(request.getFullName());
        profile.setPhone(request.getPhone());
        profile.setHeadline(request.getHeadline());
        profile.setExperienceYears(request.getExperienceYears());
        profile.setSkills(request.getSkills());
        profile.setResumeUrl(request.getResumeUrl());

        CandidateProfile savedProfile = candidateProfileRepository.save(profile);

        return mapToResponse(savedProfile);
    }

    /**
     * Maps a CandidateProfile entity to a CandidateProfileResponse DTO.
     *
     * @param profile the CandidateProfile entity
     * @return the mapped CandidateProfileResponse
     */
    private CandidateProfileResponse mapToResponse(CandidateProfile profile) {
        return CandidateProfileResponse.builder()
                .id(profile.getId())
                .email(profile.getUser().getEmail())
                .fullName(profile.getFullName())
                .phone(profile.getPhone())
                .headline(profile.getHeadline())
                .experienceYears(profile.getExperienceYears())
                .skills(profile.getSkills())
                .resumeUrl(profile.getResumeUrl())
                .build();
    }
}
