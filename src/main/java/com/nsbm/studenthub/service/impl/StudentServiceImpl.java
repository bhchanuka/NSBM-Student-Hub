package com.nsbm.studenthub.service.impl;

import com.nsbm.studenthub.dto.StudentDTO;
import com.nsbm.studenthub.entity.Student;
import com.nsbm.studenthub.exception.DuplicateResourceException;
import com.nsbm.studenthub.exception.ResourceNotFoundException;
import com.nsbm.studenthub.repository.StudentRepository;
import com.nsbm.studenthub.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

/**
 * Implementation of StudentService
 * Handles all business logic for student operations
 */
@Service
@RequiredArgsConstructor
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public StudentDTO createStudent(StudentDTO studentDTO) {
        // Check if email already exists
        if (studentRepository.existsByEmail(studentDTO.getEmail())) {
            throw new DuplicateResourceException("Email already exists: " + studentDTO.getEmail());
        }

        Student student = toEntity(studentDTO);
        Student savedStudent = studentRepository.save(student);
        return toDTO(savedStudent);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StudentDTO> getAllStudents(Pageable pageable) {
        return studentRepository.findAll(pageable).map(this::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StudentDTO> getStudentById(Long id) {
        return studentRepository.findById(id).map(this::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StudentDTO> getStudentByEmail(String email) {
        return studentRepository.findByEmail(email).map(this::toDTO);
    }

    @Override
    public StudentDTO updateStudent(Long id, StudentDTO studentDTO) {
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));

        // Check if new email is already taken by another student
        if (!existingStudent.getEmail().equals(studentDTO.getEmail())
                && studentRepository.existsByEmail(studentDTO.getEmail())) {
            throw new DuplicateResourceException("Email already exists: " + studentDTO.getEmail());
        }

        existingStudent.setName(studentDTO.getName());
        existingStudent.setEmail(studentDTO.getEmail());
        existingStudent.setBatch(studentDTO.getBatch());
        existingStudent.setGpa(studentDTO.getGpa());

        Student updatedStudent = studentRepository.save(existingStudent);
        return toDTO(updatedStudent);
    }

    @Override
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Student not found with id: " + id);
        }
        studentRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return studentRepository.existsByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StudentDTO> getStudentsByBatch(String batch, Pageable pageable) {
        return studentRepository.findByBatch(batch, pageable).map(this::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StudentDTO> searchStudentsByName(String name, Pageable pageable) {
        return studentRepository.findByNameContainingIgnoreCase(name, pageable).map(this::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StudentDTO> getStudentsByMinGpa(Double gpa, Pageable pageable) {
        return studentRepository.findByGpaGreaterThanEqual(gpa, pageable).map(this::toDTO);
    }

    @Override
    public StudentDTO toDTO(Student student) {
        return StudentDTO.builder()
                .id(student.getId())
                .name(student.getName())
                .email(student.getEmail())
                .batch(student.getBatch())
                .gpa(student.getGpa())
                .build();
    }

    @Override
    public Student toEntity(StudentDTO studentDTO) {
        return Student.builder()
                .id(studentDTO.getId())
                .name(studentDTO.getName())
                .email(studentDTO.getEmail())
                .batch(studentDTO.getBatch())
                .gpa(studentDTO.getGpa())
                .build();
    }
}
