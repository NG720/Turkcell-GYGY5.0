package com.library.cqrs.dto.command;

import lombok.*;

// ── Kategori Komutları ────────────────────────────────────────────────────────

public class KategoriCommands {

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public static class CreateKategoriCommand {
        private String ad;
        private String aciklama;
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public static class UpdateKategoriCommand {
        private Integer id;
        private String ad;
        private String aciklama;
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public static class DeleteKategoriCommand {
        private Integer id;
    }
}
