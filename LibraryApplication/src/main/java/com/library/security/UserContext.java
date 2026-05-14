package com.library.security;

import java.util.Collections;
import java.util.List;

/**
 * Her request'e ait kullanıcı bilgisini thread-safe biçimde tutar.
 * JwtAuthenticationFilter set eder, request bittikten sonra clear edilir.
 */
public class UserContext {

    private static final ThreadLocal<UserContext> HOLDER = new ThreadLocal<>();

    private final String     username;
    private final List<Role> roles;

    private UserContext(String username, List<Role> roles) {
        this.username = username;
        this.roles    = Collections.unmodifiableList(roles);
    }

    public static void set(String username, List<Role> roles) {
        HOLDER.set(new UserContext(username, roles));
    }

    public static UserContext get()          { return HOLDER.get(); }
    public static void clear()               { HOLDER.remove(); }
    public static boolean isAuthenticated()  { return HOLDER.get() != null; }

    public String     getUsername() { return username; }
    public List<Role> getRoles()    { return roles; }

    public boolean hasRole(Role role) { return roles.contains(role); }

    public boolean hasAnyRole(Role... required) {
        for (Role r : required) if (roles.contains(r)) return true;
        return false;
    }
}
