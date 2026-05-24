package com.library.cqrs.repository;

import com.library.cqrs.entity.Ogrenci;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface OgrenciRepository extends JpaRepository<Ogrenci, Integer> {
    Optional<Ogrenci> findByOgrenciNo(String ogrenciNo);
    Optional<Ogrenci> findByEmail(String email);
}
