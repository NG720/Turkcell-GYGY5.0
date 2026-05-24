package com.library.cqrs.dto.command;

import lombok.*;
import java.time.LocalDate;

public class OgrenciCommands {

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public static class CreateOgrenciCommand {
        private String ogrenciNo;
        private String ad;
        private String soyad;
        private String email;
        private String bolum;
        private LocalDate kayitTarihi;
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public static class UpdateOgrenciCommand {
        private Integer id;
        private String ad;
        private String soyad;
        private String email;
        private String bolum;
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public static class DeleteOgrenciCommand {
        private Integer id;
    }
}
