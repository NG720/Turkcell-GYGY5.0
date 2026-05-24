package com.library.cqrs.core.security.authorization;

import com.library.cqrs.core.security.Role;
import java.util.List;

/**
 * Rol gerektiren Command veya Query'ler bu interface'i implement eder.
 *
 * Örnek:
 *   public class DeleteKitapCommand implements Command<Void>, AuthorizableRequest {
 *       public List<Role> getRequiredRoles() { return List.of(Role.ADMIN); }
 *   }
 */
public interface AuthorizableRequest {
    List<Role> getRequiredRoles();
}
