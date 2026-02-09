package com.nsbm.studenthub.repository;

import com.nsbm.studenthub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * User Repository for user management
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find user by username
     */
    Optional<User> findByUsername(String username);

    /**
     * Check if username already exists
     */
    Boolean existsByUsername(String username);

    /**
     * Check if email already exists
     */
    Boolean existsByEmail(String email);
}
