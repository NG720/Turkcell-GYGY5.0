# SQL Egzersizleri

> Ders notu — ödev değil
> Northwind veritabanı şeması üzerinden yazılmıştır.

---

## Temel Kural

- `WHERE` → satır filtreler (aggregate öncesi)
- `HAVING` → grup filtreler (aggregate sonrası)
- GROUP BY varsa → WHERE yerine HAVING kullan

---

## Egzersiz 1 — Toplam Cirosu 50.000'den Büyük Müşteriler

```sql
SELECT
    c.CustomerID,
    c.CompanyName,
    SUM(od.UnitPrice * od.Quantity) AS ToplamCiro
FROM Customers    c
JOIN Orders       o  ON c.CustomerID = o.CustomerID
JOIN OrderDetails od ON o.OrderID    = od.OrderID
GROUP BY c.CustomerID, c.CompanyName
HAVING SUM(od.UnitPrice * od.Quantity) > 50000
ORDER BY ToplamCiro DESC;
```

---

## Egzersiz 2 — En Az 5 Farklı Ürün Satan Kategoriler

```sql
SELECT
    c.CategoryID,
    c.CategoryName,
    COUNT(DISTINCT p.ProductID) AS UrunSayisi
FROM Categories  c
JOIN Products     p  ON c.CategoryID = p.CategoryID
JOIN OrderDetails od ON p.ProductID  = od.ProductID
GROUP BY c.CategoryID, c.CategoryName
HAVING COUNT(DISTINCT p.ProductID) >= 5
ORDER BY UrunSayisi DESC;
```

---

## Egzersiz 3 — Çalışan Bazlı Toplam Satış Tutarı

```sql
SELECT
    e.EmployeeID,
    e.FirstName || ' ' || e.LastName AS Calisan,
    SUM(od.UnitPrice * od.Quantity)  AS ToplamSatis
FROM Employees   e
JOIN Orders       o  ON e.EmployeeID = o.EmployeeID
JOIN OrderDetails od ON o.OrderID    = od.OrderID
GROUP BY e.EmployeeID, e.FirstName, e.LastName
ORDER BY ToplamSatis DESC;
```

---

## Özet

```
JOIN     → tabloları birleştir
GROUP BY → grupla
HAVING   → gruba filtre uygula (WHERE değil!)
```
