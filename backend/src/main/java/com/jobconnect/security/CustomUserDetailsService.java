package com.jobconnect.security;

import com.jobconnect.entity.User;
import com.jobconnect.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * CustomUserDetailsService
 * Implements Spring Security's UserDetailsService interface to load user credentials and authorities from database by email.
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // 1. Fetch user from database by email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // 2. Resolve authority string while transaction and Hibernate session are still active
        String authority = user.getRole().getName().name();

        // 3. Return CustomUserDetails with pre-resolved authority to avoid LazyInitializationException
        return new CustomUserDetails(user, authority);
    }
}
