package com.library.cqrs.handler.query;

import com.library.cqrs.core.mediator.cqrs.QueryHandler;
import com.library.cqrs.dto.query.Queries.*;
import com.library.cqrs.entity.*;
import com.library.cqrs.exception.ResourceNotFoundException;
import com.library.cqrs.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;

public class QueryHandlers {

    @Component
    @RequiredArgsConstructor
    public static class GetAllKategoriHandler implements QueryHandler<GetAllKategoriQuery, List<Kategori>> {
        private final KategoriRepository repo;
        @Override public List<Kategori> handle(GetAllKategoriQuery q) { return repo.findAll(); }
    }

    @Component
    @RequiredArgsConstructor
    public static class GetKategoriByIdHandler implements QueryHandler<GetKategoriByIdQuery, Kategori> {
        private final KategoriRepository repo;
        @Override public Kategori handle(GetKategoriByIdQuery q) {
            return repo.findById(q.getId()).orElseThrow(() -> new ResourceNotFoundException("Kategori bulunamadı – id: " + q.getId()));
        }
    }

    @Component
    @RequiredArgsConstructor
    public static class GetAllKitapHandler implements QueryHandler<GetAllKitapQuery, List<Kitap>> {
        private final KitapRepository repo;
        @Override public List<Kitap> handle(GetAllKitapQuery q) { return repo.findAll(); }
    }

    @Component
    @RequiredArgsConstructor
    public static class GetKitapByIdHandler implements QueryHandler<GetKitapByIdQuery, Kitap> {
        private final KitapRepository repo;
        @Override public Kitap handle(GetKitapByIdQuery q) {
            return repo.findById(q.getId()).orElseThrow(() -> new ResourceNotFoundException("Kitap bulunamadı – id: " + q.getId()));
        }
    }

    @Component
    @RequiredArgsConstructor
    public static class GetMevcutKitaplarHandler implements QueryHandler<GetMevcutKitaplarQuery, List<Kitap>> {
        private final KitapRepository repo;
        @Override public List<Kitap> handle(GetMevcutKitaplarQuery q) { return repo.findMevcutKitaplar(); }
    }

    @Component
    @RequiredArgsConstructor
    public static class GetAllOgrenciHandler implements QueryHandler<GetAllOgrenciQuery, List<Ogrenci>> {
        private final OgrenciRepository repo;
        @Override public List<Ogrenci> handle(GetAllOgrenciQuery q) { return repo.findAll(); }
    }

    @Component
    @RequiredArgsConstructor
    public static class GetOgrenciByIdHandler implements QueryHandler<GetOgrenciByIdQuery, Ogrenci> {
        private final OgrenciRepository repo;
        @Override public Ogrenci handle(GetOgrenciByIdQuery q) {
            return repo.findById(q.getId()).orElseThrow(() -> new ResourceNotFoundException("Öğrenci bulunamadı – id: " + q.getId()));
        }
    }

    @Component
    @RequiredArgsConstructor
    public static class GetAllGorevliHandler implements QueryHandler<GetAllGorevliQuery, List<Gorevli>> {
        private final GorevliRepository repo;
        @Override public List<Gorevli> handle(GetAllGorevliQuery q) { return repo.findAll(); }
    }

    @Component
    @RequiredArgsConstructor
    public static class GetGorevliByIdHandler implements QueryHandler<GetGorevliByIdQuery, Gorevli> {
        private final GorevliRepository repo;
        @Override public Gorevli handle(GetGorevliByIdQuery q) {
            return repo.findById(q.getId()).orElseThrow(() -> new ResourceNotFoundException("Görevli bulunamadı – id: " + q.getId()));
        }
    }

    @Component
    @RequiredArgsConstructor
    public static class GetAllOduncAlmaHandler implements QueryHandler<GetAllOduncAlmaQuery, List<OduncAlma>> {
        private final OduncAlmaRepository repo;
        @Override public List<OduncAlma> handle(GetAllOduncAlmaQuery q) { return repo.findAll(); }
    }

    @Component
    @RequiredArgsConstructor
    public static class GetOduncAlmaByIdHandler implements QueryHandler<GetOduncAlmaByIdQuery, OduncAlma> {
        private final OduncAlmaRepository repo;
        @Override public OduncAlma handle(GetOduncAlmaByIdQuery q) {
            return repo.findById(q.getId()).orElseThrow(() -> new ResourceNotFoundException("Ödünç kaydı bulunamadı – id: " + q.getId()));
        }
    }

    @Component
    @RequiredArgsConstructor
    public static class GetAktifOdunclarHandler implements QueryHandler<GetAktifOdunclarQuery, List<OduncAlma>> {
        private final OduncAlmaRepository repo;
        @Override public List<OduncAlma> handle(GetAktifOdunclarQuery q) { return repo.findAktifOdunclar(); }
    }
}
