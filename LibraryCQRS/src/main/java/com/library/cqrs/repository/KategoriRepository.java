package com.library.cqrs.repository;

import com.library.cqrs.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface KategoriRepository extends JpaRepository<Kategori, Integer> {
    Optional<Kategori> findByAd(String ad);
}
