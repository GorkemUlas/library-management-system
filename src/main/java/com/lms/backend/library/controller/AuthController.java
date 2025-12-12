package com.lms.backend.library.controller;

import com.lms.backend.library.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // -----------------------------
    // REGISTER
    // -----------------------------
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Map<String, String> request) {

        String name = request.get("name");
        String email = request.get("email");
        String password = request.get("password");

        String token = authService.register(name, email, password);

        return ResponseEntity.ok(token);
    }

    // -----------------------------
    // LOGIN
    // -----------------------------
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> request) {

        String email = request.get("email");
        String password = request.get("password");

        String token = authService.login(email, password);

        return ResponseEntity.ok(token);
    }
}
