package com.library.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity @Table(name = "kitaplar")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Kitap {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 20) private String isbn;
    @Column(nullable = false, length = 255) private String baslik;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "yazar_id", nullable = false) private Yazar yazar;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "kategori_id", nullable = false) private Kategori kategori;
    private String yayinevi;
    private Integer yayinYili;
    private Integer sayfaSayisi;
    @Column(nullable = false) private Integer toplamKopya = 1;
    @Column(nullable = false) private Integer mevcutKopya = 1;
    private String dil = "Türkçe";
    private String rafKonum;
    @OneToMany(mappedBy = "kitap", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore private List<OduncAlma> oduncAlmalar;
}
