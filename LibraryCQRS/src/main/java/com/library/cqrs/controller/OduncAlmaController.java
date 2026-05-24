package com.library.cqrs.controller;

import com.library.cqrs.core.mediator.Mediator;
import com.library.cqrs.dto.command.OduncAlmaCommands.*;
import com.library.cqrs.dto.query.Queries.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/oduncler")
@RequiredArgsConstructor
public class OduncAlmaController {

    private final Mediator mediator;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(mediator.send(new GetAllOduncAlmaQuery()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(mediator.send(new GetOduncAlmaByIdQuery(id)));
    }

    @GetMapping("/aktif")
    public ResponseEntity<?> getAktif() {
        return ResponseEntity.ok(mediator.send(new GetAktifOdunclarQuery()));
    }

    @PostMapping
    public ResponseEntity<?> oduncVer(@RequestBody CreateOduncAlmaCommand cmd) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mediator.send(cmd));
    }

    @PatchMapping("/{id}/iade")
    public ResponseEntity<?> iadeEt(@PathVariable Integer id, @RequestBody IadeEtCommand cmd) {
        cmd.setOduncId(id);
        return ResponseEntity.ok(mediator.send(cmd));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        mediator.send(new DeleteOduncAlmaCommand(id));
        return ResponseEntity.noContent().build();
    }
}
