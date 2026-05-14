package com.library.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity @Table(name = "ogrenciler")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Ogrenci {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 20) private String ogrenciNo;
    @Column(nullable = false, length = 100) private String ad;
    @Column(nullable = false, length = 100) private String soyad;
    @Column(nullable = false, unique = true, length = 150) private String email;
    private String telefon;
    private String bolum;
    private Integer sinif;
    private LocalDate kayitTarihi;
    @Column(nullable = false) private Boolean aktifMi = true;
    @OneToMany(mappedBy = "ogrenci", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore private List<OduncAlma> oduncAlmalar;
}
