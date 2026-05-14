package com.library.controller;
import com.library.service.AuthService;
import lombok.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest req) {
        return new LoginResponse(authService.login(req.getUsername(), req.getPassword()), "Bearer", req.getUsername());
    }

    @Getter @NoArgsConstructor @AllArgsConstructor
    public static class LoginRequest { private String username; private String password; }

    @Getter @AllArgsConstructor
    public static class LoginResponse { private final String token; private final String type; private final String username; }
}
