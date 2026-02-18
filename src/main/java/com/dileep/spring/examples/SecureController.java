package com.dileep.spring.examples;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class SecureController {

    private final AuthenticationService authenticationService;
    private final AuthorizationService authorizationService;

    public SecureController(AuthenticationService authenticationService,
                            AuthorizationService authorizationService) {
        this.authenticationService = authenticationService;
        this.authorizationService = authorizationService;
    }

    @GetMapping("/user")
    public ResponseEntity<String> userEndpoint(
            @RequestHeader(value = "Authorization", required = false) String authHeader) {

        // 1️⃣ AUTHENTICATION
        AuthenticatedUser user = authenticationService.authenticate(authHeader);
        if (user == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        // 2️⃣ AUTHORIZATION (depends on authentication)
        if (!authorizationService.canAccessUserEndpoint(user)) {
            return ResponseEntity.status(403).body("Forbidden");
        }

        return ResponseEntity.ok("User data accessed by " + user.getUsername());
    }

    @DeleteMapping("/admin")
    public ResponseEntity<String> adminEndpoint(
            @RequestHeader(value = "Authorization", required = false) String authHeader) {

        // 1️⃣ AUTHENTICATION
        AuthenticatedUser user = authenticationService.authenticate(authHeader);
        if (user == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        // 2️⃣ AUTHORIZATION (depends on authentication result)
        if (!authorizationService.canAccessAdminEndpoint(user)) {
            return ResponseEntity.status(403).body("Forbidden: Admin only");
        }

        return ResponseEntity.ok("Admin resource deleted by " + user.getUsername());
    }

//    // OLD CODE
//    @GetMapping("/user")
//    public ResponseEntity<String> userEndpoint(
//            @RequestHeader("Authorization") String authHeader) {
//
//        // Authentication check
//        if (!"Bearer TOKEN_USER".equals(authHeader)
//                && !"Bearer TOKEN_ADMIN".equals(authHeader)) {
//            return ResponseEntity.status(401).body("Unauthorized");
//        }
//
//        return ResponseEntity.ok("User data accessed");
//    }
//
//    @DeleteMapping("/admin")
//    public ResponseEntity<String> adminEndpoint(
//            @RequestHeader("Authorization") String authHeader) {
//
//        // Authorization check
//        if (!"Bearer TOKEN_ADMIN".equals(authHeader)) {
//            return ResponseEntity.status(403).body("Forbidden: Admin only");
//        }
//
//        return ResponseEntity.ok("Admin resource deleted");
//    }
}
