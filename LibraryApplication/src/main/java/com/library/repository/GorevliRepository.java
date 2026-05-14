package com.library.repository;
import com.library.entity.Gorevli;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface GorevliRepository extends JpaRepository<Gorevli, Long> {
    Optional<Gorevli> findBySicilNo(String sicilNo);
}
