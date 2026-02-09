package com.nsbm.studenthub.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Role Entity for Role-Based Access Control
 */
@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false, length = 20)
    private ERole name;
}
