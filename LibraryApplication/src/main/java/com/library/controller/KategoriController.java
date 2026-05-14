package com.library.controller;
import com.library.entity.Kategori;
import com.library.security.*;
import com.library.service.KategoriService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/kategoriler")
@RequiredArgsConstructor
public class KategoriController {
    private final KategoriService service;

    @GetMapping
    public List<Kategori> getAll() { return service.getAll(); }

    @GetMapping("/{id}")
    public Kategori getById(@PathVariable Long id) { return service.getById(id); }

    @RoleRequired({ Role.ADMIN, Role.LIBRARIAN })
    @PostMapping
    public Kategori add(@RequestBody Kategori k) { return service.add(k); }

    @RoleRequired({ Role.ADMIN, Role.LIBRARIAN })
    @PutMapping("/{id}")
    public Kategori update(@PathVariable Long id, @RequestBody Kategori k) { return service.update(id, k); }

    @RoleRequired(Role.ADMIN)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) { service.delete(id); return ResponseEntity.ok("Silindi – id: " + id); }
}
