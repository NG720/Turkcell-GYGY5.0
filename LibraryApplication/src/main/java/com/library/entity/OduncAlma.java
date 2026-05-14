package com.library.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity @Table(name = "odunc_almalar")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OduncAlma {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ogrenci_id", nullable = false) private Ogrenci ogrenci;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "kitap_id", nullable = false) private Kitap kitap;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gorevli_id", nullable = false) private Gorevli gorevli;
    @Column(nullable = false) private LocalDate oduncTarihi;
    @Column(nullable = false) private LocalDate planlananIadeTarihi;
    @Column(nullable = false, length = 30) private String durum = "Aktif";
    @OneToOne(mappedBy = "oduncAlma", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore private Iade iade;
    @OneToMany(mappedBy = "oduncAlma", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore private List<Ceza> cezalar;
}
