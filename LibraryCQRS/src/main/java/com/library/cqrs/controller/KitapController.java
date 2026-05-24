package com.library.cqrs.controller;

import com.library.cqrs.dto.command.KitapCommands.*;
import com.library.cqrs.dto.query.Queries.*;
import com.library.cqrs.handler.command.KitapCommandHandler;
import com.library.cqrs.handler.query.QueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kitaplar")
@RequiredArgsConstructor
public class KitapController {

    private final KitapCommandHandler commandHandler;
    private final QueryHandler queryHandler;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(queryHandler.handle(new GetAllKitapQuery()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(queryHandler.handle(new GetKitapByIdQuery(id)));
    }

    @GetMapping("/mevcut")
    public ResponseEntity<?> getMevcut() {
        return ResponseEntity.ok(queryHandler.handle(new GetMevcutKitaplarQuery()));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateKitapCommand cmd) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commandHandler.handle(cmd));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody UpdateKitapCommand cmd) {
        cmd.setId(id);
        return ResponseEntity.ok(commandHandler.handle(cmd));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        commandHandler.handle(new DeleteKitapCommand(id));
        return ResponseEntity.noContent().build();
    }
}
