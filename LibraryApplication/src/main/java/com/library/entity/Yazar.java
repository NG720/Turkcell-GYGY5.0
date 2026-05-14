package com.library.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity @Table(name = "yazarlar")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Yazar {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100) private String ad;
    @Column(nullable = false, length = 100) private String soyad;
    @Column(length = 100) private String uyruk;
    @OneToMany(mappedBy = "yazar", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore private List<Kitap> kitaplar;
}
