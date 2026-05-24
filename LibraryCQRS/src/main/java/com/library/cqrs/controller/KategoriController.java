package com.library.cqrs.controller;

import com.library.cqrs.core.mediator.Mediator;
import com.library.cqrs.dto.command.KategoriCommands.*;
import com.library.cqrs.dto.query.Queries.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kategoriler")
@RequiredArgsConstructor
public class KategoriController {

    private final Mediator mediator;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(mediator.send(new GetAllKategoriQuery()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(mediator.send(new GetKategoriByIdQuery(id)));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateKategoriCommand cmd) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mediator.send(cmd));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody UpdateKategoriCommand cmd) {
        cmd.setId(id);
        return ResponseEntity.ok(mediator.send(cmd));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        mediator.send(new DeleteKategoriCommand(id));
        return ResponseEntity.noContent().build();
    }
}
