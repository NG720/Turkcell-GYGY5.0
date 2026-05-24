package com.library.cqrs.controller;

import com.library.cqrs.dto.command.OgrenciCommands.*;
import com.library.cqrs.dto.query.Queries.*;
import com.library.cqrs.handler.command.OgrenciCommandHandler;
import com.library.cqrs.handler.query.QueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ogrenciler")
@RequiredArgsConstructor
public class OgrenciController {

    private final OgrenciCommandHandler commandHandler;
    private final QueryHandler queryHandler;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(queryHandler.handle(new GetAllOgrenciQuery()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(queryHandler.handle(new GetOgrenciByIdQuery(id)));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateOgrenciCommand cmd) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commandHandler.handle(cmd));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody UpdateOgrenciCommand cmd) {
        cmd.setId(id);
        return ResponseEntity.ok(commandHandler.handle(cmd));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        commandHandler.handle(new DeleteOgrenciCommand(id));
        return ResponseEntity.noContent().build();
    }
}
