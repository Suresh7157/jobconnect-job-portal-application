package com.jobconnect.security;

import com.jobconnect.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * CustomUserDetails
 * Custom implementation of Spring Security's UserDetails interface.
 * Wraps the User entity to expose the authenticated user's ID to controllers.
 * The authority string is resolved eagerly at construction time to avoid
 * LazyInitializationException when getAuthorities() is called outside a
 * transaction.
 */
public class CustomUserDetails implements UserDetails {

    private final User user;
    private final String authority;

    public CustomUserDetails(User user, String authority) {
        this.user = user;
        this.authority = authority;
    }

    /**
     * Returns the authenticated user's database ID.
     */
    public Long getId() {
        return user.getId();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(authority));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
