package com.library.cqrs.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "ODUNC_ALMA")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OduncAlma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ogrenci_id", nullable = false)
    private Ogrenci ogrenci;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kitap_id", nullable = false)
    private Kitap kitap;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gorevli_id", nullable = false)
    private Gorevli gorevli;

    @Column(nullable = false)
    private LocalDate oduncTarihi;

    @Column(nullable = false)
    private LocalDate planlananIadeTarihi;

    @Column(nullable = false, length = 30)
    private String durum = "Aktif"; // Aktif, Iade Edildi, Gecikti
}
