package com.library.security;

import java.lang.annotation.*;

/**
 * Controller metodu üzerinde hangi rollerin erişebileceğini belirtir.
 *
 * Örnekler:
 *   @RoleRequired(Role.ADMIN)                    → sadece admin
 *   @RoleRequired({Role.ADMIN, Role.LIBRARIAN})  → ikisi de geçer
 *   anotasyon yok                                → JWT varsa yeter
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface RoleRequired {
    Role[] value() default {};
}
