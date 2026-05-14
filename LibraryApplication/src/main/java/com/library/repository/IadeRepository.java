package com.library.repository;
import com.library.entity.Iade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface IadeRepository extends JpaRepository<Iade, Long> {
    Optional<Iade> findByOduncAlmaId(Long oduncAlmaId);
}
