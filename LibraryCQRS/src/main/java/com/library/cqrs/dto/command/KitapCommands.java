package com.library.cqrs.dto.command;

import com.library.cqrs.core.mediator.cqrs.Command;
import com.library.cqrs.core.security.Role;
import com.library.cqrs.core.security.authorization.AuthorizableRequest;
import com.library.cqrs.entity.Kitap;
import lombok.*;
import java.util.List;

public class KitapCommands {

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
    public static class CreateKitapCommand implements Command<Kitap> {
        private String isbn;
        private String baslik;
        private String yazar;
        private Integer kategoriId;
        private String yayinevi;
        private Integer yayinYili;
        private Integer toplamKopya;
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
    public static class UpdateKitapCommand implements Command<Kitap> {
        private Integer id;
        private String baslik;
        private String yazar;
        private Integer kategoriId;
        private String yayinevi;
        private Integer yayinYili;
        private Integer toplamKopya;
    }

    // Sadece ADMIN silebilir
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
    public static class DeleteKitapCommand implements Command<Void>, AuthorizableRequest {
        private Integer id;

        @Override
        public List<Role> getRequiredRoles() {
            return List.of(Role.ADMIN);
        }
    }
}
