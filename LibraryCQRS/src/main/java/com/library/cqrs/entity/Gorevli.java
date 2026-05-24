package com.library.cqrs.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "GOREVLI")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Gorevli {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 20)
    private String sicilNo;

    @Column(nullable = false, length = 100)
    private String ad;

    @Column(nullable = false, length = 100)
    private String soyad;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(length = 100)
    private String pozisyon = "Kütüphaneci";

    @Column(nullable = false)
    private Boolean aktifMi = true;
}
