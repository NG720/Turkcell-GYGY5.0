package com.library.cqrs.dto.command;

import com.library.cqrs.core.mediator.cqrs.Command;
import com.library.cqrs.entity.OduncAlma;
import lombok.*;
import java.time.LocalDate;

public class OduncAlmaCommands {

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
    public static class CreateOduncAlmaCommand implements Command<OduncAlma> {
        private Integer ogrenciId;
        private Integer kitapId;
        private Integer gorevliId;
        private LocalDate planlananIadeTarihi;
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
    public static class IadeEtCommand implements Command<OduncAlma> {
        private Integer oduncId;
        private Integer gorevliId;
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
    public static class DeleteOduncAlmaCommand implements Command<Void> {
        private Integer id;
    }
}
