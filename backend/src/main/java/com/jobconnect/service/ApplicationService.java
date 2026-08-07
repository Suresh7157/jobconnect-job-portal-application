package com.jobconnect.service;

import com.jobconnect.dto.ApplicationRequest;
import com.jobconnect.dto.ApplicationResponse;
import com.jobconnect.dto.UpdateApplicationStatusRequest;
import com.jobconnect.entity.Application;
import com.jobconnect.entity.CandidateProfile;
import com.jobconnect.entity.Job;
import com.jobconnect.repository.ApplicationRepository;
import com.jobconnect.repository.CandidateProfileRepository;
import com.jobconnect.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ApplicationService
 * Business service handling job application submission and retrieval operations.
 */
@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final CandidateProfileRepository candidateProfileRepository;
    private final JobRepository jobRepository;

    /**
     * Submits a job application for the candidate identified by the given user ID.
     *
     * @param userId  the ID of the authenticated candidate user
     * @param request the application payload containing the job ID
     * @return ApplicationResponse containing the submitted application details
     * @throws RuntimeException if candidate profile or job is not found, or a duplicate application exists
     */
    @Transactional
    public ApplicationResponse applyForJob(Long userId, ApplicationRequest request) {
        CandidateProfile candidateProfile = candidateProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Candidate profile not found."));

        Job job = jobRepository.findById(request.getJobId())
                .orElseThrow(() -> new RuntimeException("Job not found."));

        applicationRepository.findByCandidateProfileIdAndJobId(candidateProfile.getId(), job.getId())
                .ifPresent(existing -> {
                    throw new RuntimeException("You have already applied for this job.");
                });

        Application application = Application.builder()
                .candidateProfile(candidateProfile)
                .job(job)
                .build();

        Application savedApplication = applicationRepository.save(application);

        return mapToResponse(savedApplication);
    }

    /**
     * Retrieves all job applications submitted by the candidate identified by the given user ID.
     *
     * @param userId the ID of the authenticated candidate user
     * @return list of ApplicationResponse containing the candidate's applications
     * @throws RuntimeException if no candidate profile is found for the given user ID
     */
    public List<ApplicationResponse> getMyApplications(Long userId) {
        CandidateProfile candidateProfile = candidateProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Candidate profile not found."));

        return applicationRepository.findByCandidateProfileId(candidateProfile.getId())
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all applications submitted for a specific job.
     *
     * @param jobId the ID of the job
     * @return list of ApplicationResponse containing all applicants for the job
     */
    public List<ApplicationResponse> getApplicantsForJob(Long jobId) {
        return applicationRepository.findByJobId(jobId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Updates the status of an existing application.
     *
     * @param applicationId the ID of the application to update
     * @param request       the payload containing the new application status
     * @return ApplicationResponse containing the updated application details
     * @throws RuntimeException if no application is found for the given ID
     */
    @Transactional
    public ApplicationResponse updateApplicationStatus(Long applicationId, UpdateApplicationStatusRequest request) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found."));

        application.setStatus(request.getStatus());

        Application savedApplication = applicationRepository.save(application);

        return mapToResponse(savedApplication);
    }

    /**
     * Maps an Application entity to an ApplicationResponse DTO.
     *
     * @param application the Application entity
     * @return the mapped ApplicationResponse
     */
    private ApplicationResponse mapToResponse(Application application) {
        return ApplicationResponse.builder()
                .applicationId(application.getId())
                .jobId(application.getJob().getId())
                .jobTitle(application.getJob().getTitle())
                .candidateName(application.getCandidateProfile().getFullName())
                .recruiterCompany(application.getJob().getRecruiterProfile().getCompanyName())
                .status(application.getStatus())
                .appliedAt(application.getAppliedAt())
                .build();
    }
}
