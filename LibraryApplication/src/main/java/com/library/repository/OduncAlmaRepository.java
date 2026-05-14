package com.library.repository;
import com.library.entity.OduncAlma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface OduncAlmaRepository extends JpaRepository<OduncAlma, Long> {
    List<OduncAlma> findByDurum(String durum);
}
