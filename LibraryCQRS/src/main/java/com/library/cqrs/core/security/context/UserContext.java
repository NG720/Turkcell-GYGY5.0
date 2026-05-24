package com.library.cqrs.core.security.context;

import com.library.cqrs.core.security.Role;
import java.util.Collections;
import java.util.List;

/**
 * Request boyunca geçerli kullanıcı bilgisini tutar.
 * ThreadLocal kullandığı için her request kendi bağlamını taşır.
 *
 * Akış:
 *   1. JwtAuthFilter → UserContext.set(username, roles)
 *   2. Handler / Pipeline → UserContext.get()
 *   3. JwtAuthFilter (finally) → UserContext.clear()
 */
public class UserContext {

    private static final ThreadLocal<UserContext> HOLDER = new ThreadLocal<>();

    private final String username;
    private final List<Role> roles;

    private UserContext(String username, List<Role> roles) {
        this.username = username;
        this.roles = Collections.unmodifiableList(roles);
    }

    public static void set(String username, List<Role> roles) {
        HOLDER.set(new UserContext(username, roles));
    }

    public static UserContext get() {
        return HOLDER.get();
    }

    public static void clear() {
        HOLDER.remove();
    }

    public String getUsername() { return username; }
    public List<Role> getRoles() { return roles; }

    public boolean hasRole(Role role) {
        return roles.contains(role);
    }

    public boolean hasAnyRole(List<Role> required) {
        return required.stream().anyMatch(roles::contains);
    }
}
