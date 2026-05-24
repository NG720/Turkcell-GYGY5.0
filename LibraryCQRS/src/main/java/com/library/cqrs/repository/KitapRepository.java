package com.library.cqrs.repository;

import com.library.cqrs.entity.Kitap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface KitapRepository extends JpaRepository<Kitap, Integer> {
    Optional<Kitap> findByIsbn(String isbn);

    @Query("SELECT k FROM Kitap k WHERE k.mevcutKopya > 0")
    List<Kitap> findMevcutKitaplar();
}
