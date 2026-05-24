package com.library.cqrs.repository;

import com.library.cqrs.entity.Gorevli;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface GorevliRepository extends JpaRepository<Gorevli, Integer> {
    Optional<Gorevli> findBySicilNo(String sicilNo);
}
