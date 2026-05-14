package com.library.repository;
import com.library.entity.Ogrenci;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
@Repository
public interface OgrenciRepository extends JpaRepository<Ogrenci, Long> {
    Optional<Ogrenci> findByOgrenciNo(String ogrenciNo);
    Optional<Ogrenci> findByEmail(String email);
    List<Ogrenci> findByAktifMi(Boolean aktifMi);
}
