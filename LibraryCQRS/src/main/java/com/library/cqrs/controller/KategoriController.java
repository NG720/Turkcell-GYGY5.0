package com.library.cqrs.controller;

import com.library.cqrs.dto.command.KategoriCommands.*;
import com.library.cqrs.dto.query.Queries.*;
import com.library.cqrs.handler.command.KategoriCommandHandler;
import com.library.cqrs.handler.query.QueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kategoriler")
@RequiredArgsConstructor
public class KategoriController {

    private final KategoriCommandHandler commandHandler;
    private final QueryHandler queryHandler;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(queryHandler.handle(new GetAllKategoriQuery()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(queryHandler.handle(new GetKategoriByIdQuery(id)));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateKategoriCommand cmd) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commandHandler.handle(cmd));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody UpdateKategoriCommand cmd) {
        cmd.setId(id);
        return ResponseEntity.ok(commandHandler.handle(cmd));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        commandHandler.handle(new DeleteKategoriCommand(id));
        return ResponseEntity.noContent().build();
    }
}
