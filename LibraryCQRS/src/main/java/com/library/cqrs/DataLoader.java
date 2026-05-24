package com.library.cqrs;

import com.library.cqrs.core.security.Role;
import com.library.cqrs.entity.User;
import com.library.cqrs.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;

    @Override
    public void run(String... args) {
        userRepository.saveAll(List.of(
            User.builder().username("admin").password("admin123")
                .roles(List.of(Role.ADMIN)).build(),
            User.builder().username("librarian").password("lib123")
                .roles(List.of(Role.LIBRARIAN)).build(),
            User.builder().username("student").password("stu123")
                .roles(List.of(Role.STUDENT)).build()
        ));
    }
}
