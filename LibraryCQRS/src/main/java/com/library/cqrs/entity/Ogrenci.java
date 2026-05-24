package com.library.cqrs.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "OGRENCI")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Ogrenci {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 20)
    private String ogrenciNo;

    @Column(nullable = false, length = 100)
    private String ad;

    @Column(nullable = false, length = 100)
    private String soyad;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(length = 150)
    private String bolum;

    private LocalDate kayitTarihi;

    @Column(nullable = false)
    private Boolean aktifMi = true;
}
