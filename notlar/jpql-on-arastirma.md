# JPQL (Java Persistence Query Language) — Ön Araştırma

> Ders notu — ödev değil

---

## JPQL Nedir?

SQL'e benzer, fakat **tablo ve kolon adları yerine Java sınıf ve alan adları** kullanılır.

```sql
-- SQL (tablo adı)
SELECT * FROM kitap WHERE yayın_yili > 2000;

-- JPQL (entity sınıfı adı)
SELECT k FROM Kitap k WHERE k.yayinYili > 2000;
```

---

## SQL vs JPQL

| | SQL | JPQL |
|--|-----|------|
| Hedef | Tablo / kolon | Entity sınıfı / alan |
| Bağımsız mı? | Veritabanına bağlı | Veritabanından bağımsız |
| Döndürdüğü | Satır | Java nesnesi |

---

## Temel Kullanım

```java
@Repository
public interface KitapRepository extends JpaRepository<Kitap, Integer> {

    // 1. Basit JPQL sorgusu
    @Query("SELECT k FROM Kitap k WHERE k.yayinYili > :yil")
    List<Kitap> findByYayinYiliGreaterThan(@Param("yil") int yil);

    // 2. JOIN ile yazar adına göre ara
    @Query("SELECT k FROM Kitap k JOIN k.yazar y WHERE y.soyad = :soyad")
    List<Kitap> findByYazarSoyad(@Param("soyad") String soyad);

    // 3. Mevcut kopyası olan kitaplar
    @Query("SELECT k FROM Kitap k WHERE k.mevcutKopya > 0")
    List<Kitap> findMevcutKitaplar();

    // 4. Sayım
    @Query("SELECT COUNT(k) FROM Kitap k WHERE k.kategori.id = :kategoriId")
    long countByKategori(@Param("kategoriId") int kategoriId);
}
```

---

## Method Adıyla Otomatik Sorgu (Spring Data)

JPQL yazmadan, **method adından** sorgu üretilir:

```java
// Spring otomatik JPQL oluşturur:
List<Kitap> findByDil(String dil);
List<Kitap> findByYayinYiliGreaterThan(int yil);
List<Kitap> findByBaslikContaining(String kelime);
Optional<Kitap> findByIsbn(String isbn);
```

---

## Native SQL (Gerekirse)

```java
@Query(value = "SELECT * FROM KITAP WHERE mevcut_kopya > 0", nativeQuery = true)
List<Kitap> findMevcutKitaplarNative();
```

> `nativeQuery = true` → JPQL değil, düz SQL çalışır.

---

## Özet

```
SQL       → tablolara sorgular
JPQL      → Java nesnelerine sorgular
@Query    → özel JPQL yazımı
Method    → otomatik sorgu üretimi (findBy...)
```
