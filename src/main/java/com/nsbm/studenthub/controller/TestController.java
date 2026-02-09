package com.nsbm.studenthub.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Test Controller for verifying role-based access
 */
@RestController
@RequestMapping("/api/test")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TestController {

    /**
     * Public endpoint - accessible without authentication
     */
    @GetMapping("/public")
    public String publicAccess() {
        return "NSBM Student Hub - Public Content";
    }

    /**
     * User endpoint - requires USER, MODERATOR, or ADMIN role
     */
    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public String userAccess() {
        return "User Content - Welcome to NSBM Student Hub!";
    }

    /**
     * Moderator endpoint - requires MODERATOR or ADMIN role
     */
    @GetMapping("/mod")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public String moderatorAccess() {
        return "Moderator Board - You have moderator access!";
    }

    /**
     * Admin endpoint - requires ADMIN role only
     */
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin Board - You have full administrative access!";
    }
}
