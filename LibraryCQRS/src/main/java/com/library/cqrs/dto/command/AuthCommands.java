package com.library.cqrs.dto.command;

import com.library.cqrs.core.mediator.cqrs.Command;
import lombok.*;

public class AuthCommands {

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
    public static class LoginCommand implements Command<String> {
        private String username;
        private String password;
    }
}
