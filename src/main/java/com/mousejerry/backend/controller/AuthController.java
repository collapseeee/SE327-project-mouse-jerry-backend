package com.mousejerry.backend.controller;

import com.mousejerry.backend.dto.AuthResponseDTO;
import com.mousejerry.backend.dto.LoginRequestDTO;
import com.mousejerry.backend.dto.RegisterRequestDTO;
import com.mousejerry.backend.entity.User;
import com.mousejerry.backend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@Valid @RequestBody RegisterRequestDTO registerRequest) {
        User user = userService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "message", "User registered successfully",
                "id", user.getId(),
                "username", user.getUsername()
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        AuthResponseDTO response = userService.login(loginRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        String token = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        }
        userService.logout(token);
        return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
    }
}
