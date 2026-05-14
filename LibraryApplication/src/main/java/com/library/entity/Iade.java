package com.library.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity @Table(name = "iadeler")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Iade {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "odunc_alma_id", nullable = false, unique = true) private OduncAlma oduncAlma;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gorevli_id", nullable = false) private Gorevli gorevli;
    @Column(nullable = false) private LocalDate iadeTarihi;
    @Column(nullable = false, length = 50) private String kitapDurumu = "İyi";
    @Column(nullable = false) private Integer gecGunSayisi = 0;
    private String notlar;
}
