package com.library.cqrs.handler.query;

import com.library.cqrs.dto.query.Queries.*;
import com.library.cqrs.entity.*;
import com.library.cqrs.exception.ResourceNotFoundException;
import com.library.cqrs.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QueryHandler {

    private final KategoriRepository kategoriRepository;
    private final KitapRepository kitapRepository;
    private final OgrenciRepository ogrenciRepository;
    private final GorevliRepository gorevliRepository;
    private final OduncAlmaRepository oduncAlmaRepository;

    // ── Kategori ─────────────────────────────────────────────────────────────

    public List<Kategori> handle(GetAllKategoriQuery q) {
        return kategoriRepository.findAll();
    }

    public Kategori handle(GetKategoriByIdQuery q) {
        return kategoriRepository.findById(q.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Kategori bulunamadı – id: " + q.getId()));
    }

    // ── Kitap ─────────────────────────────────────────────────────────────────

    public List<Kitap> handle(GetAllKitapQuery q) {
        return kitapRepository.findAll();
    }

    public Kitap handle(GetKitapByIdQuery q) {
        return kitapRepository.findById(q.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Kitap bulunamadı – id: " + q.getId()));
    }

    public List<Kitap> handle(GetMevcutKitaplarQuery q) {
        return kitapRepository.findMevcutKitaplar();
    }

    // ── Öğrenci ───────────────────────────────────────────────────────────────

    public List<Ogrenci> handle(GetAllOgrenciQuery q) {
        return ogrenciRepository.findAll();
    }

    public Ogrenci handle(GetOgrenciByIdQuery q) {
        return ogrenciRepository.findById(q.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Öğrenci bulunamadı – id: " + q.getId()));
    }

    // ── Görevli ───────────────────────────────────────────────────────────────

    public List<Gorevli> handle(GetAllGorevliQuery q) {
        return gorevliRepository.findAll();
    }

    public Gorevli handle(GetGorevliByIdQuery q) {
        return gorevliRepository.findById(q.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Görevli bulunamadı – id: " + q.getId()));
    }

    // ── Ödünç Alma ────────────────────────────────────────────────────────────

    public List<OduncAlma> handle(GetAllOduncAlmaQuery q) {
        return oduncAlmaRepository.findAll();
    }

    public OduncAlma handle(GetOduncAlmaByIdQuery q) {
        return oduncAlmaRepository.findById(q.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Ödünç kaydı bulunamadı – id: " + q.getId()));
    }

    public List<OduncAlma> handle(GetAktifOdunclarQuery q) {
        return oduncAlmaRepository.findAktifOdunclar();
    }
}
