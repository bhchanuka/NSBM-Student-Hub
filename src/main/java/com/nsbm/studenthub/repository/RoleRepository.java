package com.nsbm.studenthub.repository;

import com.nsbm.studenthub.entity.ERole;
import com.nsbm.studenthub.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Role Repository for role management
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Find role by name
     */
    Optional<Role> findByName(ERole name);
}
