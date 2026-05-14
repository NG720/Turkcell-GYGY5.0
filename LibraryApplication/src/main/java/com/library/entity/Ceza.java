package com.library.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity @Table(name = "cezalar")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Ceza {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "odunc_alma_id", nullable = false) private OduncAlma oduncAlma;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ogrenci_id", nullable = false) private Ogrenci ogrenci;
    @Column(nullable = false, length = 50) private String tip;
    @Column(nullable = false, precision = 8, scale = 2) private BigDecimal tutar = BigDecimal.ZERO;
    @Column(nullable = false, length = 20) private String odemeDurumu = "Ödenmedi";
    @Column(nullable = false) private LocalDate olusumTarihi;
    private LocalDate odemeTarihi;
    private String aciklama;
}
