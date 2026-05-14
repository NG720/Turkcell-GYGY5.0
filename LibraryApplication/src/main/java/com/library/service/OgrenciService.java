package com.library.service;
import com.library.entity.Ogrenci;
import com.library.exception.*;
import com.library.repository.OgrenciRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service @RequiredArgsConstructor
public class OgrenciService {
    private final OgrenciRepository repo;
    public List<Ogrenci> getAll() { return repo.findAll(); }
    public Ogrenci getById(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Öğrenci", "id: " + id));
    }
    public Ogrenci add(Ogrenci o) {
        if (repo.findByOgrenciNo(o.getOgrenciNo()).isPresent()) throw new ResourceAlreadyExistsException("Öğrenci", "ogrenciNo", o.getOgrenciNo());
        if (repo.findByEmail(o.getEmail()).isPresent()) throw new ResourceAlreadyExistsException("Öğrenci", "email", o.getEmail());
        return repo.save(o);
    }
    public Ogrenci update(Long id, Ogrenci g) {
        Ogrenci m = getById(id);
        m.setAd(g.getAd()); m.setSoyad(g.getSoyad()); m.setEmail(g.getEmail());
        m.setTelefon(g.getTelefon()); m.setBolum(g.getBolum()); m.setSinif(g.getSinif()); m.setAktifMi(g.getAktifMi());
        return repo.save(m);
    }
    public void delete(Long id) {
        if (!repo.existsById(id)) throw new ResourceNotFoundException("Öğrenci", "id: " + id);
        repo.deleteById(id);
    }
    public List<Ogrenci> getAktifler() { return repo.findByAktifMi(true); }
}
