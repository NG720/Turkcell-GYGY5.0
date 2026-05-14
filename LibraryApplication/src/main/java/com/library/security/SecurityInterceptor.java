package com.library.security;

import com.library.exception.AuthenticationException;
import com.library.exception.AuthorizationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Controller'a ulaşmadan önce iki kontrol yapar:
 *   1. Kimlik doğrulama  → UserContext dolu mu?      (401)
 *   2. Yetkilendirme     → @RoleRequired varsa rol var mı? (403)
 */
@Component
public class SecurityInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(SecurityInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) {
        if (!(handler instanceof HandlerMethod method)) return true;

        // 1. Authentication
        if (!UserContext.isAuthenticated()) {
            throw new AuthenticationException(
                    "Bu endpoint için giriş yapmanız gerekiyor. " +
                    "Header'a 'Authorization: Bearer <token>' ekleyin.");
        }

        UserContext ctx = UserContext.get();

        // 2. Authorization
        RoleRequired roleRequired = getRoleRequired(method);
        if (roleRequired != null && roleRequired.value().length > 0) {
            Role[] required = roleRequired.value();
            boolean hasRole = Arrays.stream(required).anyMatch(ctx::hasRole);
            if (!hasRole) {
                String needed = Arrays.stream(required).map(Role::name).collect(Collectors.joining(", "));
                log.warn("⛔ Yetkisiz erişim – kullanıcı: '{}', sahip: {}, gerekli: {}",
                        ctx.getUsername(), ctx.getRoles(), needed);
                throw new AuthorizationException(ctx.getUsername(), needed);
            }
        }

        log.debug("✔ Erişim onaylandı – {} {} | '{}'",
                req.getMethod(), req.getRequestURI(), ctx.getUsername());
        return true;
    }

    private RoleRequired getRoleRequired(HandlerMethod method) {
        RoleRequired ann = method.getMethodAnnotation(RoleRequired.class);
        return ann != null ? ann : method.getBeanType().getAnnotation(RoleRequired.class);
    }
}
