package com.jobconnect.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JwtAuthenticationFilter
 * Filter that intercepts incoming HTTP requests to validate JWT tokens in the Authorization header.
 * Extends OncePerRequestFilter to guarantee single execution per request dispatch.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // 1. Check if Authorization header is missing or does not start with "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // 2. Extract JWT token substring after "Bearer " (7 characters)
            jwt = authHeader.substring(7);
            userEmail = jwtService.extractUsername(jwt);

            // 3. If email exists and user is not already authenticated in SecurityContext
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                // 4. Load UserDetails from database
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

                // 5. Validate token signature and expiration against userDetails
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // 6. Store authenticated user in Spring SecurityContext
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception e) {
            // Safely handle invalid/malformed/expired tokens without crashing filter execution
            logger.error("Cannot set user authentication from JWT: " + e.getMessage());
        }

        // 7. Pass request to next filter in Spring Security filter chain
        filterChain.doFilter(request, response);
    }
}
