package com.library.cqrs.dto.query;

import com.library.cqrs.core.mediator.cqrs.Query;
import com.library.cqrs.entity.*;
import lombok.*;
import java.util.List;

public class Queries {

    // Kategori
    public static class GetAllKategoriQuery implements Query<List<Kategori>> {}
    @Getter @AllArgsConstructor
    public static class GetKategoriByIdQuery implements Query<Kategori> { private Integer id; }

    // Kitap
    public static class GetAllKitapQuery implements Query<List<Kitap>> {}
    @Getter @AllArgsConstructor
    public static class GetKitapByIdQuery implements Query<Kitap> { private Integer id; }
    public static class GetMevcutKitaplarQuery implements Query<List<Kitap>> {}

    // Ogrenci
    public static class GetAllOgrenciQuery implements Query<List<Ogrenci>> {}
    @Getter @AllArgsConstructor
    public static class GetOgrenciByIdQuery implements Query<Ogrenci> { private Integer id; }

    // Gorevli
    public static class GetAllGorevliQuery implements Query<List<Gorevli>> {}
    @Getter @AllArgsConstructor
    public static class GetGorevliByIdQuery implements Query<Gorevli> { private Integer id; }

    // OduncAlma
    public static class GetAllOduncAlmaQuery implements Query<List<OduncAlma>> {}
    @Getter @AllArgsConstructor
    public static class GetOduncAlmaByIdQuery implements Query<OduncAlma> { private Integer id; }
    public static class GetAktifOdunclarQuery implements Query<List<OduncAlma>> {}
}
