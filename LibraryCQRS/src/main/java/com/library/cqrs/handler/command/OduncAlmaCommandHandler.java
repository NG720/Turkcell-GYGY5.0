package com.library.cqrs.handler.command;

import com.library.cqrs.dto.command.OduncAlmaCommands.*;
import com.library.cqrs.entity.OduncAlma;
import com.library.cqrs.exception.*;
import com.library.cqrs.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class OduncAlmaCommandHandler {

    private final OduncAlmaRepository oduncAlmaRepository;
    private final OgrenciRepository ogrenciRepository;
    private final KitapRepository kitapRepository;
    private final GorevliRepository gorevliRepository;

    @Transactional
    public OduncAlma handle(CreateOduncAlmaCommand cmd) {
        var ogrenci = ogrenciRepository.findById(cmd.getOgrenciId())
                .orElseThrow(() -> new ResourceNotFoundException("Öğrenci bulunamadı – id: " + cmd.getOgrenciId()));

        var kitap = kitapRepository.findById(cmd.getKitapId())
                .orElseThrow(() -> new ResourceNotFoundException("Kitap bulunamadı – id: " + cmd.getKitapId()));

        var gorevli = gorevliRepository.findById(cmd.getGorevliId())
                .orElseThrow(() -> new ResourceNotFoundException("Görevli bulunamadı – id: " + cmd.getGorevliId()));

        if (kitap.getMevcutKopya() <= 0)
            throw new InsufficientCopiesException(kitap.getBaslik());

        kitap.setMevcutKopya(kitap.getMevcutKopya() - 1);
        kitapRepository.save(kitap);

        return oduncAlmaRepository.save(OduncAlma.builder()
                .ogrenci(ogrenci)
                .kitap(kitap)
                .gorevli(gorevli)
                .oduncTarihi(LocalDate.now())
                .planlananIadeTarihi(cmd.getPlanlananIadeTarihi())
                .durum("Aktif")
                .build());
    }

    @Transactional
    public OduncAlma handle(IadeEtCommand cmd) {
        var odunc = oduncAlmaRepository.findById(cmd.getOduncId())
                .orElseThrow(() -> new ResourceNotFoundException("Ödünç kaydı bulunamadı – id: " + cmd.getOduncId()));

        odunc.setDurum("Iade Edildi");
        odunc.getKitap().setMevcutKopya(odunc.getKitap().getMevcutKopya() + 1);
        kitapRepository.save(odunc.getKitap());
        return oduncAlmaRepository.save(odunc);
    }

    @Transactional
    public void handle(DeleteOduncAlmaCommand cmd) {
        if (!oduncAlmaRepository.existsById(cmd.getId()))
            throw new ResourceNotFoundException("Ödünç kaydı bulunamadı – id: " + cmd.getId());
        oduncAlmaRepository.deleteById(cmd.getId());
    }
}
