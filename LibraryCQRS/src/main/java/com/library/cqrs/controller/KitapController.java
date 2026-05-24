package com.library.cqrs.controller;

import com.library.cqrs.core.mediator.Mediator;
import com.library.cqrs.dto.command.KitapCommands.*;
import com.library.cqrs.dto.query.Queries.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kitaplar")
@RequiredArgsConstructor
public class KitapController {

    private final Mediator mediator;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(mediator.send(new GetAllKitapQuery()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(mediator.send(new GetKitapByIdQuery(id)));
    }

    @GetMapping("/mevcut")
    public ResponseEntity<?> getMevcut() {
        return ResponseEntity.ok(mediator.send(new GetMevcutKitaplarQuery()));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateKitapCommand cmd) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mediator.send(cmd));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody UpdateKitapCommand cmd) {
        cmd.setId(id);
        return ResponseEntity.ok(mediator.send(cmd));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        mediator.send(new DeleteKitapCommand(id));
        return ResponseEntity.noContent().build();
    }
}
