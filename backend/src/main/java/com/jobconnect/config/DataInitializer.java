package com.jobconnect.config;

import com.jobconnect.entity.Role;
import com.jobconnect.entity.User;
import com.jobconnect.enums.RoleName;
import com.jobconnect.repository.RoleRepository;
import com.jobconnect.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * DataInitializer
 * Executes automatically upon Spring Boot application startup to seed default database roles.
 */
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Check if roles table is empty before seeding
        if (roleRepository.count() == 0) {
            Role adminRole = Role.builder().name(RoleName.ROLE_ADMIN).build();
            Role recruiterRole = Role.builder().name(RoleName.ROLE_RECRUITER).build();
            Role candidateRole = Role.builder().name(RoleName.ROLE_CANDIDATE).build();

            roleRepository.saveAll(List.of(adminRole, recruiterRole, candidateRole));
            System.out.println("Default roles (ROLE_ADMIN, ROLE_RECRUITER, ROLE_CANDIDATE) initialized successfully.");
        }

        // Seed default admin user if not already present
        if (!userRepository.existsByEmail("admin@test.com")) {
            Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("ROLE_ADMIN not found during admin user initialization."));

            User adminUser = User.builder()
                    .email("admin@test.com")
                    .password(passwordEncoder.encode("Admin@123"))
                    .role(adminRole)
                    .build();

            userRepository.save(adminUser);
            System.out.println("Default admin user (admin@test.com) initialized successfully.");
        }
    }
}
