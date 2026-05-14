package com.library.controller;
import com.library.entity.Kitap;
import com.library.service.KitapService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/kitapler")
@RequiredArgsConstructor
public class KitapController {
    private final KitapService service;
    @GetMapping public List<Kitap> getAll() { return service.getAll(); }
    @GetMapping("/{id}") public Kitap getById(@PathVariable Long id) { return service.getById(id); }
    @PostMapping public Kitap add(@RequestBody Kitap e) { return service.add(e); }
    @PutMapping("/{id}") public Kitap update(@PathVariable Long id, @RequestBody Kitap e) { return service.update(id, e); }
    @DeleteMapping("/{id}") public ResponseEntity<String> delete(@PathVariable Long id) { service.delete(id); return ResponseEntity.ok("Silindi – id: " + id); }
}
