package com.library.service;
import com.library.entity.Kategori;
import com.library.exception.*;
import com.library.repository.KategoriRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service @RequiredArgsConstructor
public class KategoriService {
    private final KategoriRepository repo;
    public List<Kategori> getAll() { return repo.findAll(); }
    public Kategori getById(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Kategori", "id: " + id));
    }
    public Kategori add(Kategori k) {
        if (repo.existsByAd(k.getAd())) throw new ResourceAlreadyExistsException("Kategori", "ad", k.getAd());
        return repo.save(k);
    }
    public Kategori update(Long id, Kategori g) {
        Kategori m = getById(id); m.setAd(g.getAd()); m.setAciklama(g.getAciklama()); return repo.save(m);
    }
    public void delete(Long id) {
        if (!repo.existsById(id)) throw new ResourceNotFoundException("Kategori", "id: " + id);
        repo.deleteById(id);
    }
}
