package com.library.repository;
import com.library.entity.Ceza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface CezaRepository extends JpaRepository<Ceza, Long> {
    List<Ceza> findByOgrenciId(Long ogrenciId);
    List<Ceza> findByOdemeDurumu(String odemeDurumu);
}
