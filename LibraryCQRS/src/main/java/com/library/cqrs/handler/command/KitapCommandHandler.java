package com.library.cqrs.handler.command;

import com.library.cqrs.dto.command.KitapCommands.*;
import com.library.cqrs.entity.Kitap;
import com.library.cqrs.exception.*;
import com.library.cqrs.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class KitapCommandHandler {

    private final KitapRepository kitapRepository;
    private final KategoriRepository kategoriRepository;

    @Transactional
    public Kitap handle(CreateKitapCommand cmd) {
        if (kitapRepository.findByIsbn(cmd.getIsbn()).isPresent())
            throw new ResourceAlreadyExistsException("ISBN zaten kayıtlı: " + cmd.getIsbn());

        var kategori = kategoriRepository.findById(cmd.getKategoriId())
                .orElseThrow(() -> new ResourceNotFoundException("Kategori bulunamadı – id: " + cmd.getKategoriId()));

        return kitapRepository.save(Kitap.builder()
                .isbn(cmd.getIsbn())
                .baslik(cmd.getBaslik())
                .yazar(cmd.getYazar())
                .kategori(kategori)
                .yayinevi(cmd.getYayinevi())
                .yayinYili(cmd.getYayinYili())
                .toplamKopya(cmd.getToplamKopya())
                .mevcutKopya(cmd.getToplamKopya())
                .build());
    }

    @Transactional
    public Kitap handle(UpdateKitapCommand cmd) {
        Kitap kitap = kitapRepository.findById(cmd.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Kitap bulunamadı – id: " + cmd.getId()));

        var kategori = kategoriRepository.findById(cmd.getKategoriId())
                .orElseThrow(() -> new ResourceNotFoundException("Kategori bulunamadı – id: " + cmd.getKategoriId()));

        kitap.setBaslik(cmd.getBaslik());
        kitap.setYazar(cmd.getYazar());
        kitap.setKategori(kategori);
        kitap.setYayinevi(cmd.getYayinevi());
        kitap.setYayinYili(cmd.getYayinYili());
        kitap.setToplamKopya(cmd.getToplamKopya());
        return kitapRepository.save(kitap);
    }

    @Transactional
    public void handle(DeleteKitapCommand cmd) {
        if (!kitapRepository.existsById(cmd.getId()))
            throw new ResourceNotFoundException("Kitap bulunamadı – id: " + cmd.getId());
        kitapRepository.deleteById(cmd.getId());
    }
}
