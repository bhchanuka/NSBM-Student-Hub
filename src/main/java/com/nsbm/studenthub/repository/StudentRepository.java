package com.nsbm.studenthub.repository;

import com.nsbm.studenthub.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Student Repository for database operations
 * Extends JpaRepository for CRUD and pagination support
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    /**
     * Find student by email
     */
    Optional<Student> findByEmail(String email);

    /**
     * Check if email already exists
     */
    boolean existsByEmail(String email);

    /**
     * Find students by batch with pagination
     */
    Page<Student> findByBatch(String batch, Pageable pageable);

    /**
     * Find students by name containing (case-insensitive) with pagination
     */
    Page<Student> findByNameContainingIgnoreCase(String name, Pageable pageable);

    /**
     * Find students by GPA greater than or equal to with pagination
     */
    Page<Student> findByGpaGreaterThanEqual(Double gpa, Pageable pageable);
}
