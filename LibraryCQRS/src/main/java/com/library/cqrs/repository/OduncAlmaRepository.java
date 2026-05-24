package com.library.cqrs.repository;

import com.library.cqrs.entity.OduncAlma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface OduncAlmaRepository extends JpaRepository<OduncAlma, Integer> {

    @Query("SELECT o FROM OduncAlma o WHERE o.durum = 'Aktif'")
    List<OduncAlma> findAktifOdunclar();
}
