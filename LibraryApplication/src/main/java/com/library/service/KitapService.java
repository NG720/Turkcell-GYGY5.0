package com.library.service;
import com.library.entity.Kitap;
import com.library.exception.*;
import com.library.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service @RequiredArgsConstructor
public class KitapService {
    private final KitapRepository repo;
    private final YazarRepository yazarRepo;
    private final KategoriRepository kategoriRepo;
    public List<Kitap> getAll() { return repo.findAll(); }
    public Kitap getById(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Kitap", "id: " + id));
    }
    public Kitap add(Kitap k) {
        yazarRepo.findById(k.getYazar().getId()).orElseThrow(() -> new ResourceNotFoundException("Yazar", k.getYazar().getId()));
        kategoriRepo.findById(k.getKategori().getId()).orElseThrow(() -> new ResourceNotFoundException("Kategori", k.getKategori().getId()));
        if (repo.findByIsbn(k.getIsbn()).isPresent()) throw new ResourceAlreadyExistsException("Kitap", "isbn", k.getIsbn());
        return repo.save(k);
    }
    public Kitap update(Long id, Kitap g) {
        Kitap m = getById(id);
        m.setIsbn(g.getIsbn()); m.setBaslik(g.getBaslik()); m.setYazar(g.getYazar());
        m.setKategori(g.getKategori()); m.setYayinevi(g.getYayinevi()); m.setYayinYili(g.getYayinYili());
        m.setSayfaSayisi(g.getSayfaSayisi()); m.setToplamKopya(g.getToplamKopya());
        m.setMevcutKopya(g.getMevcutKopya()); m.setDil(g.getDil()); m.setRafKonum(g.getRafKonum());
        return repo.save(m);
    }
    public void delete(Long id) {
        if (!repo.existsById(id)) throw new ResourceNotFoundException("Kitap", "id: " + id);
        repo.deleteById(id);
    }
    public List<Kitap> getMevcutKitaplar() { return repo.findByMevcutKopyaGreaterThan(0); }
}
