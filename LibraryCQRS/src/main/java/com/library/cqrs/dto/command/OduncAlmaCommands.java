package com.library.cqrs.dto.command;

import lombok.*;
import java.time.LocalDate;

public class OduncAlmaCommands {

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public static class CreateOduncAlmaCommand {
        private Integer ogrenciId;
        private Integer kitapId;
        private Integer gorevliId;
        private LocalDate planlananIadeTarihi;
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public static class IadeEtCommand {
        private Integer oduncId;
        private Integer gorevliId;
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public static class DeleteOduncAlmaCommand {
        private Integer id;
    }
}
