package com.library.service;
import com.library.entity.Yazar;
import com.library.exception.ResourceNotFoundException;
import com.library.repository.YazarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service @RequiredArgsConstructor
public class YazarService {
    private final YazarRepository repo;
    public List<Yazar> getAll() { return repo.findAll(); }
    public Yazar getById(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Yazar", "id: " + id));
    }
    public Yazar add(Yazar y) { return repo.save(y); }
    public Yazar update(Long id, Yazar g) {
        Yazar m = getById(id); m.setAd(g.getAd()); m.setSoyad(g.getSoyad()); m.setUyruk(g.getUyruk()); return repo.save(m);
    }
    public void delete(Long id) {
        if (!repo.existsById(id)) throw new ResourceNotFoundException("Yazar", "id: " + id);
        repo.deleteById(id);
    }
}
