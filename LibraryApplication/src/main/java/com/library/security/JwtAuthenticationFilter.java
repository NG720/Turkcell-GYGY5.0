package com.library.security;

import com.library.exception.AuthenticationException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * Her request'te Authorization header'ını okur.
 *   - Token geçerliyse  → UserContext'i doldurur
 *   - Token bozuksa     → AuthenticationException (401)
 *   - Token yoksa       → UserContext boş kalır; SecurityInterceptor yakalar
 */
@Component
@Order(3)
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger log    = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private static final String BEARER = "Bearer ";

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest  req,
                                    HttpServletResponse res,
                                    FilterChain         chain)
            throws ServletException, IOException {
        try {
            String token = extractToken(req);
            if (token != null) {
                String     username = jwtUtil.extractUsername(token);
                List<Role> roles    = jwtUtil.extractRoles(token);
                UserContext.set(username, roles);
                log.debug("✔ JWT doğrulandı – kullanıcı: '{}', roller: {}", username, roles);
            }
            chain.doFilter(req, res);
        } catch (JwtException ex) {
            throw new AuthenticationException("Geçersiz veya süresi dolmuş token: " + ex.getMessage());
        } finally {
            UserContext.clear();
        }
    }

    private String extractToken(HttpServletRequest req) {
        String header = req.getHeader("Authorization");
        return (header != null && header.startsWith(BEARER))
                ? header.substring(BEARER.length()) : null;
    }

    /** Login endpoint JWT gerektirmez */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest req) {
        return req.getRequestURI().startsWith("/api/auth");
    }
}
