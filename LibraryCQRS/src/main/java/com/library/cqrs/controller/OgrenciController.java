package com.library.cqrs.controller;

import com.library.cqrs.core.mediator.Mediator;
import com.library.cqrs.dto.command.OgrenciCommands.*;
import com.library.cqrs.dto.query.Queries.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ogrenciler")
@RequiredArgsConstructor
public class OgrenciController {

    private final Mediator mediator;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(mediator.send(new GetAllOgrenciQuery()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(mediator.send(new GetOgrenciByIdQuery(id)));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateOgrenciCommand cmd) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mediator.send(cmd));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody UpdateOgrenciCommand cmd) {
        cmd.setId(id);
        return ResponseEntity.ok(mediator.send(cmd));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        mediator.send(new DeleteOgrenciCommand(id));
        return ResponseEntity.noContent().build();
    }
}
