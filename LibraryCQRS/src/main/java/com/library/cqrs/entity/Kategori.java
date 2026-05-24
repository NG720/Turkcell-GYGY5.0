package com.library.cqrs.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "KATEGORI")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Kategori {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 100)
    private String ad;

    @Column(length = 255)
    private String aciklama;
}
