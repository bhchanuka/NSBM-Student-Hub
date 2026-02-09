package com.nsbm.studenthub.config;

import com.nsbm.studenthub.entity.*;
import com.nsbm.studenthub.repository.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.HashSet;
import java.util.Set;

/**
 * Data Initializer to populate roles and default admin user on startup
 */
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Initialize roles if not present
        initializeRoles();

        // Create default admin user if not exists
        createDefaultAdmin();

        // Add sample students for testing
        createSampleStudents();
    }

    private void initializeRoles() {
        for (ERole roleEnum : ERole.values()) {
            if (roleRepository.findByName(roleEnum).isEmpty()) {
                Role role = Role.builder().name(roleEnum).build();
                roleRepository.save(role);
                logger.info("Created role: {}", roleEnum);
            }
        }
    }

    private void createDefaultAdmin() {
        if (!userRepository.existsByUsername("admin")) {
            Set<Role> adminRoles = new HashSet<>();
            roleRepository.findByName(ERole.ROLE_ADMIN).ifPresent(adminRoles::add);
            roleRepository.findByName(ERole.ROLE_MODERATOR).ifPresent(adminRoles::add);
            roleRepository.findByName(ERole.ROLE_USER).ifPresent(adminRoles::add);

            User admin = User.builder()
                    .username("admin")
                    .email("admin@nsbm.ac.lk")
                    .password(passwordEncoder.encode("admin123")) // Encrypted password
                    .roles(adminRoles)
                    .build();

            userRepository.save(admin);
            logger.info("Created default admin user: admin / admin123");
        }

        // Also create a regular user for testing
        if (!userRepository.existsByUsername("user")) {
            Set<Role> userRoles = new HashSet<>();
            roleRepository.findByName(ERole.ROLE_USER).ifPresent(userRoles::add);

            User user = User.builder()
                    .username("user")
                    .email("user@nsbm.ac.lk")
                    .password(passwordEncoder.encode("user123")) // Encrypted password
                    .roles(userRoles)
                    .build();

            userRepository.save(user);
            logger.info("Created default user: user / user123");
        }

        // Create a moderator for testing
        if (!userRepository.existsByUsername("moderator")) {
            Set<Role> modRoles = new HashSet<>();
            roleRepository.findByName(ERole.ROLE_MODERATOR).ifPresent(modRoles::add);
            roleRepository.findByName(ERole.ROLE_USER).ifPresent(modRoles::add);

            User moderator = User.builder()
                    .username("moderator")
                    .email("mod@nsbm.ac.lk")
                    .password(passwordEncoder.encode("mod123")) // Encrypted password
                    .roles(modRoles)
                    .build();

            userRepository.save(moderator);
            logger.info("Created default moderator: moderator / mod123");
        }
    }

    private void createSampleStudents() {
        if (studentRepository.count() == 0) {
            Student[] students = {
                    Student.builder().name("John Doe").email("john.doe@nsbm.ac.lk").batch("2023").gpa(3.75).build(),
                    Student.builder().name("Jane Smith").email("jane.smith@nsbm.ac.lk").batch("2023").gpa(3.90).build(),
                    Student.builder().name("Bob Wilson").email("bob.wilson@nsbm.ac.lk").batch("2022").gpa(3.45).build(),
                    Student.builder().name("Alice Brown").email("alice.brown@nsbm.ac.lk").batch("2022").gpa(3.85)
                            .build(),
                    Student.builder().name("Charlie Davis").email("charlie.davis@nsbm.ac.lk").batch("2024").gpa(3.60)
                            .build(),
            };

            for (Student student : students) {
                studentRepository.save(student);
            }

            logger.info("Created {} sample students", students.length);
        }
    }
}
