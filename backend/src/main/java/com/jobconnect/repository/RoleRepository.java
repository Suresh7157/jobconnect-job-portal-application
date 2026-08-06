package com.jobconnect.repository;

import com.jobconnect.entity.Role;
import com.jobconnect.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * RoleRepository
 * Spring Data JPA Repository for Role persistence operations.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Finds a Role entity by its RoleName enum value.
     */
    Optional<Role> findByName(RoleName name);
}
