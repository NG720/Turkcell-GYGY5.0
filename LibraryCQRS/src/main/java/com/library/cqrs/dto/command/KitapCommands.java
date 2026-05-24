package com.library.cqrs.dto.command;

import lombok.*;

public class KitapCommands {

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public static class CreateKitapCommand {
        private String isbn;
        private String baslik;
        private String yazar;
        private Integer kategoriId;
        private String yayinevi;
        private Integer yayinYili;
        private Integer toplamKopya;
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public static class UpdateKitapCommand {
        private Integer id;
        private String baslik;
        private String yazar;
        private Integer kategoriId;
        private String yayinevi;
        private Integer yayinYili;
        private Integer toplamKopya;
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public static class DeleteKitapCommand {
        private Integer id;
    }
}
