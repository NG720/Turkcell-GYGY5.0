package com.library.controller;
import com.library.entity.Ogrenci;
import com.library.service.OgrenciService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/ogrenciler")
@RequiredArgsConstructor
public class OgrenciController {
    private final OgrenciService service;
    @GetMapping public List<Ogrenci> getAll() { return service.getAll(); }
    @GetMapping("/{id}") public Ogrenci getById(@PathVariable Long id) { return service.getById(id); }
    @PostMapping public Ogrenci add(@RequestBody Ogrenci e) { return service.add(e); }
    @PutMapping("/{id}") public Ogrenci update(@PathVariable Long id, @RequestBody Ogrenci e) { return service.update(id, e); }
    @DeleteMapping("/{id}") public ResponseEntity<String> delete(@PathVariable Long id) { service.delete(id); return ResponseEntity.ok("Silindi – id: " + id); }
}
