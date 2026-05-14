package com.library.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity @Table(name = "gorevliler")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Gorevli {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 20) private String sicilNo;
    @Column(nullable = false, length = 100) private String ad;
    @Column(nullable = false, length = 100) private String soyad;
    @Column(nullable = false, unique = true, length = 150) private String email;
    private String telefon;
    private String pozisyon = "Kütüphaneci";
    private LocalDate isBaslamaTarihi;
    @Column(nullable = false) private Boolean aktifMi = true;
    @OneToMany(mappedBy = "gorevli", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore private List<OduncAlma> oduncAlmalar;
}
