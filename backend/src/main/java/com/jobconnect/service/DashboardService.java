package com.jobconnect.service;

import com.jobconnect.dto.CandidateDashboardResponse;
import com.jobconnect.dto.RecruiterDashboardResponse;
import com.jobconnect.entity.CandidateProfile;
import com.jobconnect.entity.RecruiterProfile;
import com.jobconnect.enums.ApplicationStatus;
import com.jobconnect.exception.ResourceNotFoundException;
import com.jobconnect.repository.ApplicationRepository;
import com.jobconnect.repository.CandidateProfileRepository;
import com.jobconnect.repository.JobRepository;
import com.jobconnect.repository.RecruiterProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * DashboardService
 * Business service handling aggregated statistics for candidate and recruiter dashboards.
 */
@Service
@RequiredArgsConstructor
public class DashboardService {

    private final CandidateProfileRepository candidateProfileRepository;
    private final RecruiterProfileRepository recruiterProfileRepository;
    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;

    /**
     * Retrieves aggregated application statistics for the candidate identified by the given user ID.
     *
     * @param userId the ID of the authenticated candidate user
     * @return CandidateDashboardResponse containing application counts by status
     * @throws RuntimeException if no candidate profile is found for the given user ID
     */
    public CandidateDashboardResponse getCandidateDashboard(Long userId) {
        CandidateProfile candidateProfile = candidateProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate profile not found."));

        Long candidateProfileId = candidateProfile.getId();

        long totalApplications = applicationRepository.countByCandidateProfileId(candidateProfileId);
        long applied = applicationRepository.countByCandidateProfileIdAndStatus(candidateProfileId, ApplicationStatus.APPLIED);
        long shortlisted = applicationRepository.countByCandidateProfileIdAndStatus(candidateProfileId, ApplicationStatus.SHORTLISTED);
        long hired = applicationRepository.countByCandidateProfileIdAndStatus(candidateProfileId, ApplicationStatus.HIRED);
        long rejected = applicationRepository.countByCandidateProfileIdAndStatus(candidateProfileId, ApplicationStatus.REJECTED);

        return CandidateDashboardResponse.builder()
                .totalApplications(totalApplications)
                .applied(applied)
                .shortlisted(shortlisted)
                .hired(hired)
                .rejected(rejected)
                .build();
    }

    /**
     * Retrieves aggregated job and applicant statistics for the recruiter identified by the given user ID.
     *
     * @param userId the ID of the authenticated recruiter user
     * @return RecruiterDashboardResponse containing job and applicant counts
     * @throws RuntimeException if no recruiter profile is found for the given user ID
     */
    public RecruiterDashboardResponse getRecruiterDashboard(Long userId) {
        RecruiterProfile recruiterProfile = recruiterProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Recruiter profile not found."));

        Long recruiterProfileId = recruiterProfile.getId();

        long totalJobs = jobRepository.countByRecruiterProfileId(recruiterProfileId);
        long totalApplicants = applicationRepository.countByJobRecruiterProfileId(recruiterProfileId);
        long shortlistedCandidates = applicationRepository.countByJobRecruiterProfileIdAndStatus(recruiterProfileId, ApplicationStatus.SHORTLISTED);
        long hiredCandidates = applicationRepository.countByJobRecruiterProfileIdAndStatus(recruiterProfileId, ApplicationStatus.HIRED);

        return RecruiterDashboardResponse.builder()
                .totalJobs(totalJobs)
                .totalApplicants(totalApplicants)
                .shortlistedCandidates(shortlistedCandidates)
                .hiredCandidates(hiredCandidates)
                .build();
    }
}
