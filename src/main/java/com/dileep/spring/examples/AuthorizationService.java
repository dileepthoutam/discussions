package com.dileep.spring.examples;

import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    public boolean canAccessUserEndpoint(AuthenticatedUser user) {
        return user != null; // any authenticated user
    }

    public boolean canAccessAdminEndpoint(AuthenticatedUser user) {
        return user != null && "ADMIN".equals(user.getRole());
    }
}
