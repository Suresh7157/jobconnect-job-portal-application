package com.jobconnect.service;

import com.jobconnect.dto.AdminUserResponse;
import com.jobconnect.dto.ApplicationResponse;
import com.jobconnect.dto.JobResponse;
import com.jobconnect.entity.Application;
import com.jobconnect.entity.CandidateProfile;
import com.jobconnect.entity.Job;
import com.jobconnect.entity.RecruiterProfile;
import com.jobconnect.entity.User;
import com.jobconnect.repository.ApplicationRepository;
import com.jobconnect.repository.CandidateProfileRepository;
import com.jobconnect.repository.JobRepository;
import com.jobconnect.repository.RecruiterProfileRepository;
import com.jobconnect.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * AdminService
 * Business service handling administrative operations for user, job, and application management.
 */
@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final CandidateProfileRepository candidateProfileRepository;
    private final RecruiterProfileRepository recruiterProfileRepository;
    private final JobRepository jobRepository;
    private final ApplicationRepository applicationRepository;

    /**
     * Retrieves all registered users in the system.
     *
     * @return list of AdminUserResponse containing user details (excluding passwords)
     */
    public List<AdminUserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapUserToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all job postings in the system.
     *
     * @return list of JobResponse containing all job details
     */
    public List<JobResponse> getAllJobs() {
        return jobRepository.findAll()
                .stream()
                .map(this::mapJobToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all job applications in the system.
     *
     * @return list of ApplicationResponse containing all application details
     */
    public List<ApplicationResponse> getAllApplications() {
        return applicationRepository.findAll()
                .stream()
                .map(this::mapApplicationToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Deletes a user and all their associated data in the correct dependency order.
     *
     * @param userId the ID of the user to delete
     * @throws RuntimeException if no user is found for the given ID
     */
    @Transactional
    public void deleteUser(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found."));

        candidateProfileRepository.findByUserId(userId).ifPresent(candidateProfile -> {
            List<Application> applications = applicationRepository.findByCandidateProfileId(candidateProfile.getId());
            applicationRepository.deleteAll(applications);
            candidateProfileRepository.delete(candidateProfile);
        });

        recruiterProfileRepository.findByUserId(userId).ifPresent(recruiterProfile -> {
            List<Job> jobs = jobRepository.findByRecruiterProfileId(recruiterProfile.getId());
            for (Job job : jobs) {
                List<Application> applications = applicationRepository.findByJobId(job.getId());
                applicationRepository.deleteAll(applications);
                jobRepository.delete(job);
            }
            recruiterProfileRepository.delete(recruiterProfile);
        });

        userRepository.deleteById(userId);
    }

    /**
     * Deletes a job posting and all its associated applications.
     *
     * @param jobId the ID of the job to delete
     * @throws RuntimeException if no job is found for the given ID
     */
    @Transactional
    public void deleteJob(Long jobId) {
        jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found."));

        List<Application> applications = applicationRepository.findByJobId(jobId);
        applicationRepository.deleteAll(applications);

        jobRepository.deleteById(jobId);
    }

    /**
     * Maps a User entity to an AdminUserResponse DTO.
     *
     * @param user the User entity
     * @return the mapped AdminUserResponse
     */
    private AdminUserResponse mapUserToResponse(User user) {
        return AdminUserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole().getName())
                .createdAt(user.getCreatedAt())
                .build();
    }

    /**
     * Maps a Job entity to a JobResponse DTO.
     *
     * @param job the Job entity
     * @return the mapped JobResponse
     */
    private JobResponse mapJobToResponse(Job job) {
        return JobResponse.builder()
                .id(job.getId())
                .title(job.getTitle())
                .description(job.getDescription())
                .location(job.getLocation())
                .jobType(job.getJobType())
                .salary(job.getSalary())
                .status(job.getStatus())
                .companyName(job.getRecruiterProfile().getCompanyName())
                .createdAt(job.getCreatedAt())
                .build();
    }

    /**
     * Maps an Application entity to an ApplicationResponse DTO.
     *
     * @param application the Application entity
     * @return the mapped ApplicationResponse
     */
    private ApplicationResponse mapApplicationToResponse(Application application) {
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
