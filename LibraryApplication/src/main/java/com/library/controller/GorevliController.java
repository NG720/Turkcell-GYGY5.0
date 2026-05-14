package com.library.controller;
import com.library.entity.Gorevli;
import com.library.service.GorevliService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/gorevliler")
@RequiredArgsConstructor
public class GorevliController {
    private final GorevliService service;
    @GetMapping public List<Gorevli> getAll() { return service.getAll(); }
    @GetMapping("/{id}") public Gorevli getById(@PathVariable Long id) { return service.getById(id); }
    @PostMapping public Gorevli add(@RequestBody Gorevli e) { return service.add(e); }
    @PutMapping("/{id}") public Gorevli update(@PathVariable Long id, @RequestBody Gorevli e) { return service.update(id, e); }
    @DeleteMapping("/{id}") public ResponseEntity<String> delete(@PathVariable Long id) { service.delete(id); return ResponseEntity.ok("Silindi – id: " + id); }
}
