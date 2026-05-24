package com.library.cqrs.dto.command;

import com.library.cqrs.core.mediator.cqrs.Command;
import com.library.cqrs.entity.Ogrenci;
import lombok.*;
import java.time.LocalDate;

public class OgrenciCommands {

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
    public static class CreateOgrenciCommand implements Command<Ogrenci> {
        private String ogrenciNo;
        private String ad;
        private String soyad;
        private String email;
        private String bolum;
        private LocalDate kayitTarihi;
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
    public static class UpdateOgrenciCommand implements Command<Ogrenci> {
        private Integer id;
        private String ad;
        private String soyad;
        private String email;
        private String bolum;
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
    public static class DeleteOgrenciCommand implements Command<Void> {
        private Integer id;
    }
}
