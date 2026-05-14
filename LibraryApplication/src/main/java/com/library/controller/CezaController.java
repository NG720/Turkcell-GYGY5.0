package com.library.controller;
import com.library.entity.Ceza;
import com.library.service.CezaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cezaler")
@RequiredArgsConstructor
public class CezaController {
    private final CezaService service;
    @GetMapping public List<Ceza> getAll() { return service.getAll(); }
    @GetMapping("/{id}") public Ceza getById(@PathVariable Long id) { return service.getById(id); }
    @PostMapping public Ceza add(@RequestBody Ceza e) { return service.add(e); }
    @PutMapping("/{id}") public Ceza update(@PathVariable Long id, @RequestBody Ceza e) { return service.update(id, e); }
    @DeleteMapping("/{id}") public ResponseEntity<String> delete(@PathVariable Long id) { service.delete(id); return ResponseEntity.ok("Silindi – id: " + id); }
}
