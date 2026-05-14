package com.library.controller;
import com.library.entity.OduncAlma;
import com.library.service.OduncAlmaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/oduncalmaler")
@RequiredArgsConstructor
public class OduncAlmaController {
    private final OduncAlmaService service;
    @GetMapping public List<OduncAlma> getAll() { return service.getAll(); }
    @GetMapping("/{id}") public OduncAlma getById(@PathVariable Long id) { return service.getById(id); }
    @PostMapping public OduncAlma add(@RequestBody OduncAlma e) { return service.add(e); }
    @PutMapping("/{id}") public OduncAlma update(@PathVariable Long id, @RequestBody OduncAlma e) { return service.update(id, e); }
    @DeleteMapping("/{id}") public ResponseEntity<String> delete(@PathVariable Long id) { service.delete(id); return ResponseEntity.ok("Silindi – id: " + id); }
}
