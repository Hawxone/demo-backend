package com.dimas.product.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/test")
public class TestController {

    @GetMapping("/all")
    public String allAccess(){
        return "Public Content";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> userAccess(Authentication authentication) {




        return ResponseEntity.ok(authentication.getPrincipal());
    }

    @GetMapping("/mod")
    @PreAuthorize("hasRole('MODERATOR')")
    public String mmoderatorAccess() {
        return "Moderator Board";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin Board";
    }
}
