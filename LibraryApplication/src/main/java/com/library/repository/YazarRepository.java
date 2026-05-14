package com.library.repository;
import com.library.entity.Yazar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface YazarRepository extends JpaRepository<Yazar, Long> {
    List<Yazar> findByUyruk(String uyruk);
}
