package com.library.cqrs.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "KITAP")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Kitap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 20)
    private String isbn;

    @Column(nullable = false, length = 255)
    private String baslik;

    @Column(length = 150)
    private String yazar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kategori_id", nullable = false)
    private Kategori kategori;

    @Column(length = 150)
    private String yayinevi;

    private Integer yayinYili;

    @Column(nullable = false)
    private Integer toplamKopya = 1;

    @Column(nullable = false)
    private Integer mevcutKopya = 1;
}
