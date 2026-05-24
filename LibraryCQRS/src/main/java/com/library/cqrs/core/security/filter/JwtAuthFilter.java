package com.library.cqrs.core.security.filter;

import com.library.cqrs.core.security.context.UserContext;
import com.library.cqrs.core.security.jwt.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Her request'te Authorization header'ından token'ı alır.
 * Geçerliyse UserContext'e kullanıcı adı ve rolleri set eder.
 * Token yoksa veya geçersizse UserContext boş kalır →
 * AuthorizationBehavior pipeline'da 401 fırlatır.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        try {
            String header = request.getHeader("Authorization");

            if (header != null && header.startsWith("Bearer ")) {
                String token = header.substring(7);

                if (jwtService.isValid(token)) {
                    String username = jwtService.extractUsername(token);
                    var roles = jwtService.extractRoles(token);
                    UserContext.set(username, roles);
                }
            }

            chain.doFilter(request, response);
        } finally {
            UserContext.clear(); // memory leak önlemek için
        }
    }

    // /api/auth/** endpoint'lerini filtreden muaf tut
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getRequestURI().startsWith("/api/auth");
    }
}
