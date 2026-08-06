package com.jobconnect.service;

import com.jobconnect.dto.AuthenticationResponse;
import com.jobconnect.dto.LoginRequest;
import com.jobconnect.dto.RegisterRequest;
import com.jobconnect.entity.CandidateProfile;
import com.jobconnect.entity.RecruiterProfile;
import com.jobconnect.entity.Role;
import com.jobconnect.entity.User;
import com.jobconnect.enums.RoleName;
import com.jobconnect.exception.EmailAlreadyExistsException;
import com.jobconnect.exception.InvalidCredentialsException;
import com.jobconnect.exception.RoleNotFoundException;
import com.jobconnect.repository.CandidateProfileRepository;
import com.jobconnect.repository.RecruiterProfileRepository;
import com.jobconnect.repository.RoleRepository;
import com.jobconnect.repository.UserRepository;
import com.jobconnect.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * AuthenticationService
 * Core business service handling user registration, authentication, and JWT token issuance.
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CandidateProfileRepository candidateProfileRepository;
    private final RecruiterProfileRepository recruiterProfileRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Registers a new User and creates their corresponding role-specific profile (Candidate or Recruiter).
     */
    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {
        // 1. Validate email uniqueness
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email is already registered: " + request.getEmail());
        }

        // 2. Fetch requested role entity
        Role role = roleRepository.findByName(request.getRole())
                .orElseThrow(() -> new RoleNotFoundException("Role not found: " + request.getRole()));

        // 3. Encrypt password & save User
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();
        User savedUser = userRepository.save(user);

        // 4. Create role-specific profile based on selected role
        if (role.getName() == RoleName.ROLE_CANDIDATE) {
            CandidateProfile candidateProfile = CandidateProfile.builder()
                    .user(savedUser)
                    .fullName(request.getFullName())
                    .phone(request.getPhone())
                    .build();
            candidateProfileRepository.save(candidateProfile);
        } else if (role.getName() == RoleName.ROLE_RECRUITER) {
            if (request.getCompanyName() == null || request.getCompanyName().isBlank()) {
                throw new IllegalArgumentException("Company name is required for recruiter registration.");
            }
            RecruiterProfile recruiterProfile = RecruiterProfile.builder()
                    .user(savedUser)
                    .companyName(request.getCompanyName())
                    .companyWebsite(request.getCompanyWebsite())
                    .designation(request.getDesignation())
                    .build();
            recruiterProfileRepository.save(recruiterProfile);
        }

        // 5. Generate JWT token
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                savedUser.getEmail(),
                savedUser.getPassword(),
                List.of(new SimpleGrantedAuthority(savedUser.getRole().getName().name()))
        );
        String jwtToken = jwtService.generateToken(userDetails);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .message("User registered successfully")
                .build();
    }

    /**
     * Authenticates user credentials and generates a signed JWT token.
     */
    public AuthenticationResponse login(LoginRequest request) {
        // 1. Authenticate credentials using Spring Security AuthenticationManager
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (BadCredentialsException ex) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        // 2. Fetch authenticated user from database
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

        // 3. Generate JWT token
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getRole().getName().name()))
        );
        String jwtToken = jwtService.generateToken(userDetails);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .message("Login successful")
                .build();
    }
}
