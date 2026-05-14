package com.library.service;
import com.library.entity.Ceza;
import com.library.exception.ResourceNotFoundException;
import com.library.repository.CezaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service @RequiredArgsConstructor
public class CezaService {
    private final CezaRepository repo;
    public List<Ceza> getAll() { return repo.findAll(); }
    public Ceza getById(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ceza", "id: " + id));
    }
    public Ceza add(Ceza c) { c.setOlusumTarihi(LocalDate.now()); c.setOdemeDurumu("Ödenmedi"); return repo.save(c); }
    public Ceza update(Long id, Ceza g) {
        Ceza m = getById(id); m.setTutar(g.getTutar()); m.setOdemeDurumu(g.getOdemeDurumu()); m.setOdemeTarihi(g.getOdemeTarihi()); m.setAciklama(g.getAciklama()); return repo.save(m);
    }
    public void delete(Long id) {
        if (!repo.existsById(id)) throw new ResourceNotFoundException("Ceza", "id: " + id);
        repo.deleteById(id);
    }
    public List<Ceza> getOdenmemisler() { return repo.findByOdemeDurumu("Ödenmedi"); }
    public List<Ceza> getByOgrenci(Long id) { return repo.findByOgrenciId(id); }
}
