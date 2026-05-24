package com.library.cqrs.dto.query;

import lombok.*;

public class Queries {

    // Kategori
    public static class GetAllKategoriQuery {}
    @Getter @AllArgsConstructor
    public static class GetKategoriByIdQuery { private Integer id; }

    // Kitap
    public static class GetAllKitapQuery {}
    @Getter @AllArgsConstructor
    public static class GetKitapByIdQuery { private Integer id; }
    public static class GetMevcutKitaplarQuery {}

    // Ogrenci
    public static class GetAllOgrenciQuery {}
    @Getter @AllArgsConstructor
    public static class GetOgrenciByIdQuery { private Integer id; }

    // Gorevli
    public static class GetAllGorevliQuery {}
    @Getter @AllArgsConstructor
    public static class GetGorevliByIdQuery { private Integer id; }

    // OduncAlma
    public static class GetAllOduncAlmaQuery {}
    @Getter @AllArgsConstructor
    public static class GetOduncAlmaByIdQuery { private Integer id; }
    public static class GetAktifOdunclarQuery {}
}
