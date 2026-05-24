package com.library.cqrs.handler.command;

import com.library.cqrs.core.mediator.cqrs.CommandHandler;
import com.library.cqrs.dto.command.KategoriCommands.*;
import com.library.cqrs.entity.Kategori;
import com.library.cqrs.exception.*;
import com.library.cqrs.repository.KategoriRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KategoriCommandHandler {

    private final KategoriRepository repository;

    @Component
    @RequiredArgsConstructor
    public static class CreateHandler implements CommandHandler<CreateKategoriCommand, Kategori> {
        private final KategoriRepository repository;

        @Override
        public Kategori handle(CreateKategoriCommand cmd) {
            if (repository.findByAd(cmd.getAd()).isPresent())
                throw new ResourceAlreadyExistsException("Kategori zaten var: " + cmd.getAd());
            return repository.save(Kategori.builder().ad(cmd.getAd()).aciklama(cmd.getAciklama()).build());
        }
    }

    @Component
    @RequiredArgsConstructor
    public static class UpdateHandler implements CommandHandler<UpdateKategoriCommand, Kategori> {
        private final KategoriRepository repository;

        @Override
        public Kategori handle(UpdateKategoriCommand cmd) {
            Kategori k = repository.findById(cmd.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Kategori bulunamadı – id: " + cmd.getId()));
            k.setAd(cmd.getAd());
            k.setAciklama(cmd.getAciklama());
            return repository.save(k);
        }
    }

    @Component
    @RequiredArgsConstructor
    public static class DeleteHandler implements CommandHandler<DeleteKategoriCommand, Void> {
        private final KategoriRepository repository;

        @Override
        public Void handle(DeleteKategoriCommand cmd) {
            if (!repository.existsById(cmd.getId()))
                throw new ResourceNotFoundException("Kategori bulunamadı – id: " + cmd.getId());
            repository.deleteById(cmd.getId());
            return null;
        }
    }
}
