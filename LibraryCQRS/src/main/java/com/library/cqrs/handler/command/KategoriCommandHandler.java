package com.library.cqrs.handler.command;

import com.library.cqrs.dto.command.KategoriCommands.*;
import com.library.cqrs.entity.Kategori;
import com.library.cqrs.exception.*;
import com.library.cqrs.repository.KategoriRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class KategoriCommandHandler {

    private final KategoriRepository repository;

    @Transactional
    public Kategori handle(CreateKategoriCommand cmd) {
        if (repository.findByAd(cmd.getAd()).isPresent())
            throw new ResourceAlreadyExistsException("Kategori zaten var: " + cmd.getAd());

        return repository.save(Kategori.builder()
                .ad(cmd.getAd())
                .aciklama(cmd.getAciklama())
                .build());
    }

    @Transactional
    public Kategori handle(UpdateKategoriCommand cmd) {
        Kategori kategori = repository.findById(cmd.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Kategori bulunamadı – id: " + cmd.getId()));

        kategori.setAd(cmd.getAd());
        kategori.setAciklama(cmd.getAciklama());
        return repository.save(kategori);
    }

    @Transactional
    public void handle(DeleteKategoriCommand cmd) {
        if (!repository.existsById(cmd.getId()))
            throw new ResourceNotFoundException("Kategori bulunamadı – id: " + cmd.getId());
        repository.deleteById(cmd.getId());
    }
}
