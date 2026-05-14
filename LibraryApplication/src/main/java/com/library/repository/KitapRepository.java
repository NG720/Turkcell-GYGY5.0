package com.library.repository;
import com.library.entity.Kitap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
@Repository
public interface KitapRepository extends JpaRepository<Kitap, Long> {
    Optional<Kitap> findByIsbn(String isbn);
    List<Kitap> findByMevcutKopyaGreaterThan(Integer sayi);
}
