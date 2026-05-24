package com.library.cqrs.controller;

import com.library.cqrs.dto.command.OduncAlmaCommands.*;
import com.library.cqrs.dto.query.Queries.*;
import com.library.cqrs.handler.command.OduncAlmaCommandHandler;
import com.library.cqrs.handler.query.QueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/oduncler")
@RequiredArgsConstructor
public class OduncAlmaController {

    private final OduncAlmaCommandHandler commandHandler;
    private final QueryHandler queryHandler;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(queryHandler.handle(new GetAllOduncAlmaQuery()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(queryHandler.handle(new GetOduncAlmaByIdQuery(id)));
    }

    @GetMapping("/aktif")
    public ResponseEntity<?> getAktif() {
        return ResponseEntity.ok(queryHandler.handle(new GetAktifOdunclarQuery()));
    }

    @PostMapping
    public ResponseEntity<?> oduncVer(@RequestBody CreateOduncAlmaCommand cmd) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commandHandler.handle(cmd));
    }

    @PatchMapping("/{id}/iade")
    public ResponseEntity<?> iadeEt(@PathVariable Integer id, @RequestBody IadeEtCommand cmd) {
        cmd.setOduncId(id);
        return ResponseEntity.ok(commandHandler.handle(cmd));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        commandHandler.handle(new DeleteOduncAlmaCommand(id));
        return ResponseEntity.noContent().build();
    }
}
