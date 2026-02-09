package com.nsbm.studenthub.service;

import com.nsbm.studenthub.dto.StudentDTO;
import com.nsbm.studenthub.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

/**
 * Service interface for Student operations
 */
public interface StudentService {

    /**
     * Create a new student
     */
    StudentDTO createStudent(StudentDTO studentDTO);

    /**
     * Get all students with pagination
     */
    Page<StudentDTO> getAllStudents(Pageable pageable);

    /**
     * Get student by ID
     */
    Optional<StudentDTO> getStudentById(Long id);

    /**
     * Get student by email
     */
    Optional<StudentDTO> getStudentByEmail(String email);

    /**
     * Update student
     */
    StudentDTO updateStudent(Long id, StudentDTO studentDTO);

    /**
     * Delete student
     */
    void deleteStudent(Long id);

    /**
     * Check if email exists
     */
    boolean existsByEmail(String email);

    /**
     * Get students by batch with pagination
     */
    Page<StudentDTO> getStudentsByBatch(String batch, Pageable pageable);

    /**
     * Search students by name with pagination
     */
    Page<StudentDTO> searchStudentsByName(String name, Pageable pageable);

    /**
     * Get students by minimum GPA with pagination
     */
    Page<StudentDTO> getStudentsByMinGpa(Double gpa, Pageable pageable);

    /**
     * Convert Entity to DTO
     */
    StudentDTO toDTO(Student student);

    /**
     * Convert DTO to Entity
     */
    Student toEntity(StudentDTO studentDTO);
}
