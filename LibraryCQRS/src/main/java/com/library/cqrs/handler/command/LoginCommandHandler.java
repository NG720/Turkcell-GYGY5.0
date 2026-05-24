package com.library.cqrs.handler.command;

import com.library.cqrs.core.mediator.cqrs.CommandHandler;
import com.library.cqrs.core.security.authorization.AuthenticatedException;
import com.library.cqrs.core.security.jwt.JwtService;
import com.library.cqrs.dto.command.AuthCommands.LoginCommand;
import com.library.cqrs.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginCommandHandler implements CommandHandler<LoginCommand, String> {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public String handle(LoginCommand cmd) {
        var user = userRepository.findByUsername(cmd.getUsername())
                .orElseThrow(() -> new AuthenticatedException("Kullanıcı bulunamadı: " + cmd.getUsername()));

        // Gerçek projede BCrypt kullanılır, burada düz karşılaştırma
        if (!cmd.getPassword().equals(user.getPassword()))
            throw new AuthenticatedException("Şifre hatalı.");

        return jwtService.generateToken(user.getUsername(), user.getRoles());
    }
}
