package com.library.service;
import com.library.entity.*;
import com.library.exception.*;
import com.library.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service @RequiredArgsConstructor
public class IadeService {
    private final IadeRepository repo;
    private final OduncAlmaRepository oduncRepo;
    private final KitapRepository kitapRepo;
    public List<Iade> getAll() { return repo.findAll(); }
    public Iade getById(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Iade", "id: " + id));
    }
    public Iade add(Iade iade) {
        OduncAlma odunc = oduncRepo.findById(iade.getOduncAlma().getId())
            .orElseThrow(() -> new ResourceNotFoundException("OduncAlma", iade.getOduncAlma().getId()));
        if (repo.findByOduncAlmaId(odunc.getId()).isPresent()) throw new AlreadyReturnedException(odunc.getId());
        Kitap k = odunc.getKitap(); k.setMevcutKopya(k.getMevcutKopya() + 1); kitapRepo.save(k);
        odunc.setDurum("Iade Edildi"); oduncRepo.save(odunc);
        return repo.save(iade);
    }
    public Iade update(Long id, Iade g) {
        Iade m = getById(id); m.setKitapDurumu(g.getKitapDurumu()); m.setGecGunSayisi(g.getGecGunSayisi()); m.setNotlar(g.getNotlar()); return repo.save(m);
    }
    public void delete(Long id) {
        if (!repo.existsById(id)) throw new ResourceNotFoundException("Iade", "id: " + id);
        repo.deleteById(id);
    }
}
