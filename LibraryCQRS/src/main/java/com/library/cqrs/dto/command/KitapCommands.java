package com.library.cqrs.dto.command;

import com.library.cqrs.core.mediator.cqrs.Command;
import com.library.cqrs.entity.Kitap;
import lombok.*;

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

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
    public static class DeleteKitapCommand implements Command<Void> {
        private Integer id;
    }
}
