package com.library.cqrs.controller;

import com.library.cqrs.core.mediator.Mediator;
import com.library.cqrs.dto.command.AuthCommands.LoginCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final Mediator mediator;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginCommand cmd) {
        String token = mediator.send(cmd);
        return ResponseEntity.ok(Map.of("token", token));
    }
}
