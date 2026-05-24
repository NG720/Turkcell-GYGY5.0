# Ders 4 — ORM & GROUP BY / HAVING

---

## GROUP BY + HAVING

GROUP BY kullanılıyorsa WHERE yerine HAVING kullanılır.

- `WHERE` → satırları filtreler (gruplama öncesi)
- `HAVING` → grupları filtreler (gruplama sonrası)

```sql
SELECT departman, COUNT(*) AS Sayi
FROM Calisanlar
GROUP BY departman
HAVING COUNT(*) > 5;  -- WHERE değil, HAVING!
```

---

## ORM (Object Relational Mapping)

Veritabanı tablosunu kod içindeki nesneye eşler.

```
Customers tablosu          customer.java
──────────────────         ─────────────────────
id      (serial)    →      private int id;
name    (varchar)   →      private String name;
city    (varchar)   →      private String city;
```

ORM ile manuel SQL yazmak yerine nesne üzerinden işlem yapılır.

---

## ORM ile Yapılabilecek İşlemler

- Tüm listeyi al
- ID'ye göre tekil al
- Ekle
- Sil
- Güncelle
- Sayfalama

---

## Sonraki Konular

- ORM — DB Bağlantısı (Spring Data JPA)
- Encryption & Hashing
