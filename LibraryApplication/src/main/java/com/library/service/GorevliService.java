package com.library.service;
import com.library.entity.Gorevli;
import com.library.exception.*;
import com.library.repository.GorevliRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service @RequiredArgsConstructor
public class GorevliService {
    private final GorevliRepository repo;
    public List<Gorevli> getAll() { return repo.findAll(); }
    public Gorevli getById(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Görevli", "id: " + id));
    }
    public Gorevli add(Gorevli g) {
        if (repo.findBySicilNo(g.getSicilNo()).isPresent()) throw new ResourceAlreadyExistsException("Görevli", "sicilNo", g.getSicilNo());
        return repo.save(g);
    }
    public Gorevli update(Long id, Gorevli g) {
        Gorevli m = getById(id);
        m.setAd(g.getAd()); m.setSoyad(g.getSoyad()); m.setEmail(g.getEmail());
        m.setTelefon(g.getTelefon()); m.setPozisyon(g.getPozisyon()); m.setAktifMi(g.getAktifMi());
        return repo.save(m);
    }
    public void delete(Long id) {
        if (!repo.existsById(id)) throw new ResourceNotFoundException("Görevli", "id: " + id);
        repo.deleteById(id);
    }
}
