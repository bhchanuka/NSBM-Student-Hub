package com.nsbm.studenthub.controller;

import com.nsbm.studenthub.dto.StudentDTO;
import com.nsbm.studenthub.exception.ResourceNotFoundException;
import com.nsbm.studenthub.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for Student CRUD operations
 * Supports pagination and sorting
 */
@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class StudentController {

    private final StudentService studentService;

    /**
     * Create a new student
     * Requires ADMIN or MODERATOR role
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<StudentDTO> createStudent(@Valid @RequestBody StudentDTO studentDTO) {
        StudentDTO createdStudent = studentService.createStudent(studentDTO);
        return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
    }

    /**
     * Get all students with pagination and sorting
     * Accessible by all authenticated users
     * 
     * @param page    Page number (0-indexed)
     * @param size    Number of records per page
     * @param sortBy  Field to sort by (default: id)
     * @param sortDir Sort direction: asc or desc (default: asc)
     */
    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Page<StudentDTO>> getAllStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<StudentDTO> students = studentService.getAllStudents(pageable);

        return ResponseEntity.ok(students);
    }

    /**
     * Get student by ID
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id) {
        StudentDTO student = studentService.getStudentById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
        return ResponseEntity.ok(student);
    }

    /**
     * Get student by email
     */
    @GetMapping("/email/{email}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<StudentDTO> getStudentByEmail(@PathVariable String email) {
        StudentDTO student = studentService.getStudentByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with email: " + email));
        return ResponseEntity.ok(student);
    }

    /**
     * Update student
     * Requires ADMIN or MODERATOR role
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<StudentDTO> updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody StudentDTO studentDTO) {
        StudentDTO updatedStudent = studentService.updateStudent(id, studentDTO);
        return ResponseEntity.ok(updatedStudent);
    }

    /**
     * Delete student
     * Requires ADMIN role only
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get students by batch with pagination
     */
    @GetMapping("/batch/{batch}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Page<StudentDTO>> getStudentsByBatch(
            @PathVariable String batch,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<StudentDTO> students = studentService.getStudentsByBatch(batch, pageable);

        return ResponseEntity.ok(students);
    }

    /**
     * Search students by name with pagination
     */
    @GetMapping("/search")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Page<StudentDTO>> searchStudentsByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<StudentDTO> students = studentService.searchStudentsByName(name, pageable);

        return ResponseEntity.ok(students);
    }

    /**
     * Get students by minimum GPA with pagination
     */
    @GetMapping("/gpa/{minGpa}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Page<StudentDTO>> getStudentsByMinGpa(
            @PathVariable Double minGpa,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "gpa") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<StudentDTO> students = studentService.getStudentsByMinGpa(minGpa, pageable);

        return ResponseEntity.ok(students);
    }
}
