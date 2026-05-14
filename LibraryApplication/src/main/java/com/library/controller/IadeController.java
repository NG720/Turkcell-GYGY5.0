package com.library.controller;
import com.library.entity.Iade;
import com.library.service.IadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/iadeler")
@RequiredArgsConstructor
public class IadeController {
    private final IadeService service;
    @GetMapping public List<Iade> getAll() { return service.getAll(); }
    @GetMapping("/{id}") public Iade getById(@PathVariable Long id) { return service.getById(id); }
    @PostMapping public Iade add(@RequestBody Iade e) { return service.add(e); }
    @PutMapping("/{id}") public Iade update(@PathVariable Long id, @RequestBody Iade e) { return service.update(id, e); }
    @DeleteMapping("/{id}") public ResponseEntity<String> delete(@PathVariable Long id) { service.delete(id); return ResponseEntity.ok("Silindi – id: " + id); }
}
