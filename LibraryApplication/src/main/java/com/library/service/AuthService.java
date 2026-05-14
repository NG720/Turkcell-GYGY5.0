package com.library.service;
import com.library.exception.AuthenticationException;
import com.library.security.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service @RequiredArgsConstructor
public class AuthService {
    private final JwtUtil jwtUtil;

    private static final Map<String, UserPrincipal> USERS = Map.of(
        "admin",     new UserPrincipal("admin",     "admin123", List.of(Role.ADMIN)),
        "librarian", new UserPrincipal("librarian", "lib123",   List.of(Role.LIBRARIAN)),
        "student",   new UserPrincipal("student",   "stu123",   List.of(Role.STUDENT))
    );

    public String login(String username, String password) {
        UserPrincipal user = USERS.get(username);
        if (user == null || !user.getPassword().equals(password))
            throw new AuthenticationException("Kullanıcı adı veya şifre hatalı.");
        return jwtUtil.generateToken(user.getUsername(), user.getRoles());
    }
}
