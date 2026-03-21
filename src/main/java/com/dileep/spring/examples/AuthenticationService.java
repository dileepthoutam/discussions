package com.dileep.spring.examples;

import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    public AuthenticatedUser authenticate(String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }

        String token = authHeader.substring(7);

        if ("TOKEN_USER".equals(token)) {
            return new AuthenticatedUser("rosh", "USER");
        }

        if ("TOKEN_ADMIN".equals(token)) {
            return new AuthenticatedUser("admin", "ADMIN");
        }

        return null;
    }
}
