package com.library.controller;
import com.library.entity.Yazar;
import com.library.service.YazarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/yazarler")
@RequiredArgsConstructor
public class YazarController {
    private final YazarService service;
    @GetMapping public List<Yazar> getAll() { return service.getAll(); }
    @GetMapping("/{id}") public Yazar getById(@PathVariable Long id) { return service.getById(id); }
    @PostMapping public Yazar add(@RequestBody Yazar e) { return service.add(e); }
    @PutMapping("/{id}") public Yazar update(@PathVariable Long id, @RequestBody Yazar e) { return service.update(id, e); }
    @DeleteMapping("/{id}") public ResponseEntity<String> delete(@PathVariable Long id) { service.delete(id); return ResponseEntity.ok("Silindi – id: " + id); }
}
