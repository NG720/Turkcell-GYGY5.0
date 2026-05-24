package com.library.cqrs.handler.command;

import com.library.cqrs.dto.command.OgrenciCommands.*;
import com.library.cqrs.entity.Ogrenci;
import com.library.cqrs.exception.*;
import com.library.cqrs.repository.OgrenciRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OgrenciCommandHandler {

    private final OgrenciRepository repository;

    @Transactional
    public Ogrenci handle(CreateOgrenciCommand cmd) {
        if (repository.findByOgrenciNo(cmd.getOgrenciNo()).isPresent())
            throw new ResourceAlreadyExistsException("Öğrenci no zaten kayıtlı: " + cmd.getOgrenciNo());
        if (repository.findByEmail(cmd.getEmail()).isPresent())
            throw new ResourceAlreadyExistsException("E-posta zaten kayıtlı: " + cmd.getEmail());

        return repository.save(Ogrenci.builder()
                .ogrenciNo(cmd.getOgrenciNo())
                .ad(cmd.getAd())
                .soyad(cmd.getSoyad())
                .email(cmd.getEmail())
                .bolum(cmd.getBolum())
                .kayitTarihi(cmd.getKayitTarihi())
                .build());
    }

    @Transactional
    public Ogrenci handle(UpdateOgrenciCommand cmd) {
        Ogrenci ogrenci = repository.findById(cmd.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Öğrenci bulunamadı – id: " + cmd.getId()));

        ogrenci.setAd(cmd.getAd());
        ogrenci.setSoyad(cmd.getSoyad());
        ogrenci.setEmail(cmd.getEmail());
        ogrenci.setBolum(cmd.getBolum());
        return repository.save(ogrenci);
    }

    @Transactional
    public void handle(DeleteOgrenciCommand cmd) {
        if (!repository.existsById(cmd.getId()))
            throw new ResourceNotFoundException("Öğrenci bulunamadı – id: " + cmd.getId());
        repository.deleteById(cmd.getId());
    }
}
