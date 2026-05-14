package com.library.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;

@Getter
@AllArgsConstructor
public class UserPrincipal {
    private final String     username;
    private final String     password;
    private final List<Role> roles;
}
