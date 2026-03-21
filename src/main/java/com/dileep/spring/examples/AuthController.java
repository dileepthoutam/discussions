package com.dileep.spring.examples;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        if ("rosh".equals(request.getUsername())
                && "rosh".equals(request.getPassword())) {

            return ResponseEntity.ok(
                    Map.of(
                            "token", "TOKEN_USER",
                            "message", "Authentication successful"
                    )
            );
        }

        return ResponseEntity.status(401)
                .body(Map.of("error", "Invalid credentials"));
    }
}
