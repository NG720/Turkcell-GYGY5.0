package com.library.service;
import com.library.entity.*;
import com.library.exception.*;
import com.library.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service @RequiredArgsConstructor
public class OduncAlmaService {
    private final OduncAlmaRepository repo;
    private final KitapRepository kitapRepo;
    private final OgrenciRepository ogrenciRepo;
    public List<OduncAlma> getAll() { return repo.findAll(); }
    public OduncAlma getById(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("OduncAlma", "id: " + id));
    }
    public OduncAlma add(OduncAlma o) {
        Ogrenci ogrenci = ogrenciRepo.findById(o.getOgrenci().getId())
            .orElseThrow(() -> new ResourceNotFoundException("Öğrenci", o.getOgrenci().getId()));
        if (!ogrenci.getAktifMi()) throw new InactiveStudentException(ogrenci.getOgrenciNo());
        Kitap kitap = kitapRepo.findById(o.getKitap().getId())
            .orElseThrow(() -> new ResourceNotFoundException("Kitap", o.getKitap().getId()));
        if (kitap.getMevcutKopya() < 1) throw new InsufficientCopiesException(kitap.getBaslik());
        kitap.setMevcutKopya(kitap.getMevcutKopya() - 1); kitapRepo.save(kitap);
        o.setDurum("Aktif"); return repo.save(o);
    }
    public OduncAlma update(Long id, OduncAlma g) {
        OduncAlma m = getById(id); m.setPlanlananIadeTarihi(g.getPlanlananIadeTarihi()); m.setDurum(g.getDurum()); return repo.save(m);
    }
    public void delete(Long id) {
        OduncAlma o = getById(id);
        Kitap k = o.getKitap(); k.setMevcutKopya(k.getMevcutKopya() + 1); kitapRepo.save(k);
        repo.deleteById(id);
    }
    public List<OduncAlma> getAktifler() { return repo.findByDurum("Aktif"); }
}
