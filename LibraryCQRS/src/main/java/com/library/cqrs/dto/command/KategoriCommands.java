package com.library.cqrs.dto.command;

import com.library.cqrs.core.mediator.cqrs.Command;
import com.library.cqrs.entity.Kategori;
import lombok.*;

public class KategoriCommands {

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
    public static class CreateKategoriCommand implements Command<Kategori> {
        private String ad;
        private String aciklama;
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
    public static class UpdateKategoriCommand implements Command<Kategori> {
        private Integer id;
        private String ad;
        private String aciklama;
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
    public static class DeleteKategoriCommand implements Command<Void> {
        private Integer id;
    }
}
