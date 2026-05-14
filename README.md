# Turkcell GYGY 5.0 – Java Bootcamp Ödevleri

Bu repo, Turkcell Geleceği Yazanlar programı kapsamında yapılan Java ödevlerini içermektedir.

---

## 📁 Proje Yapısı

```
Turkcell-GYGY5.0/
├── BankingApplication/          → Ödev 1: Konsol Banka Uygulaması
├── LibraryApplication/          → Ödev 4-7: Kütüphane Yönetim Sistemi (Spring Boot)
├── library_er_diagram.mermaid   → Ödev 2: Kütüphane ER Diyagramı
└── kutuphane_sistemi.sql        → Ödev 3: Kütüphane Veritabanı (DDL + DML)
```

---

## Ödev 1 – BankingApplication

Konsol üzerinden çalışan, in-memory banka simülasyonu.

**Özellikler:**
- Yeni müşteri kaydı
- Vadesiz / Tasarruf hesabı açma
- Para yatırma ve çekme
- Hesaplar arası transfer
- Hesap özeti ve işlem geçmişi (ekstre)
- Tüm müşteri ve hesapları listeleme (admin görünümü)

**Teknolojiler:** Java 17, In-Memory Repository (LinkedHashMap)

**Çalıştırma:**
```bash
cd BankingApplication
javac -d out src/main/java/com/bankingapp/*.java
java -cp out com.bankingapp.BankingApplication
```

---

## Ödev 2 – Kütüphane ER Diyagramı

`library_er_diagram.mermaid` dosyasında kütüphane sisteminin entity-relationship diyagramı yer almaktadır.

**Tablolar:** KATEGORI, YAZAR, KITAP, OGRENCI, GOREVLI, ODUNC_ALMA, IADE, CEZA

**İlişkiler:**
- Bir yazar → çok kitap (1:N)
- Bir öğrenci → çok ödünç alma (1:N)
- Her ödünç alma → en fazla bir iade (1:0..1)
- Ödünç almadan → ceza doğabilir (1:N)

---

## Ödev 3 – Kütüphane SQL

`kutuphane_sistemi.sql` dosyasında kütüphane veritabanının DDL ve DML komutları bulunmaktadır.

**DDL:** 8 tablo, PRIMARY KEY, FOREIGN KEY, UNIQUE ve CHECK kısıtları

**DML:** Her tabloya minimum 5 INSERT, UPDATE, SELECT, DELETE komutu

---

## Ödev 4-7 – LibraryApplication (Spring Boot)

Kütüphane yönetim sisteminin REST API olarak geliştirilmiş hali.

### Ödev 4 – CRUD & Code First JPA

Spring Boot ile tüm entity'ler için CRUD endpoint'leri.

**Teknolojiler:** Spring Boot 3.2.5, Spring Data JPA, H2 In-Memory Database, Lombok

**Entity'ler:** Kategori, Yazar, Kitap, Ogrenci, Gorevli, OduncAlma, Iade, Ceza

**Endpoint örneği:**
| Method | URL | Açıklama |
|--------|-----|----------|
| GET | `/api/kategoriler` | Tüm kategoriler |
| GET | `/api/kategoriler/{id}` | ID'ye göre kategori |
| POST | `/api/kategoriler` | Yeni kategori ekle |
| PUT | `/api/kategoriler/{id}` | Kategori güncelle |
| DELETE | `/api/kategoriler/{id}` | Kategori sil |

### Ödev 5 – Custom Exception Handling

Genel `RuntimeException` yerine özel exception sınıfları.

**Exception hiyerarşisi:**
```
BusinessException (abstract)
├── ResourceNotFoundException        → 404
├── ResourceAlreadyExistsException   → 409
├── AlreadyReturnedException         → 409
├── InsufficientCopiesException      → 422
├── InvalidOperationException        → 422
├── InactiveStudentException         → 403
├── AuthenticationException          → 401
└── AuthorizationException           → 403
```

**Response formatları:**
```json
// ErrorResponse
{
  "title": "Kayıt Bulunamadı",
  "type": "RESOURCE_NOT_FOUND",
  "message": "Kitap bulunamadı – id: 42",
  "status": 404,
  "timestamp": "2024-11-25T14:30:00",
  "path": "/api/kitaplar/42"
}

// ValidationErrorResponse
{
  "title": "Doğrulama Hatası",
  "type": "VALIDATION_ERROR",
  "status": 400,
  "errors": {
    "email": ["Geçerli bir e-posta giriniz."]
  }
}
```

### Ödev 6 – Pipeline (Filter & AOP)

3 adet pipeline bileşeni:

**1. PerformanceMonitoringFilter** — Her request'in süresini ölçer. 3000ms üzerindeyse `WARN` logu düşer.

**2. LoggingFilter** — Her request ve response'u ayrı ayrı loglar. Authorization header'ı maskelenir.

**3. TransactionBehavior (AOP)** — Service katmanındaki tüm metodları `@Transactional` ile sarar. Hata durumunda otomatik rollback yapılır.

### Ödev 7 – JWT Authentication & Authorization

**Akış:**
```
Request
  → JwtAuthenticationFilter   (token parse → UserContext.set)
  → SecurityInterceptor        (auth + rol kontrolü)
  → Controller
```

**Kullanıcılar:**
| Kullanıcı | Şifre | Rol | GET | POST | DELETE |
|-----------|-------|-----|-----|------|--------|
| admin | admin123 | ADMIN | ✔ | ✔ | ✔ |
| librarian | lib123 | LIBRARIAN | ✔ | ✔ | ✗ |
| student | stu123 | STUDENT | ✔ | ✗ | ✗ |

**Test:**
```bash
# 1. Login → JWT al
POST /api/auth/login
{ "username": "admin", "password": "admin123" }

# 2. JWT'siz istek → 401
GET /api/kategoriler

# 3. JWT ile → 200
GET /api/kategoriler
Authorization: Bearer <token>

# 4. Yetersiz rol → 403
DELETE /api/kategoriler/1
Authorization: Bearer <student_token>
```

---

## Çalıştırma (LibraryApplication)

```bash
cd LibraryApplication
mvn spring-boot:run
```

Uygulama `http://localhost:8080` adresinde çalışır.

H2 Console: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:librarydb`
- Kullanıcı: `sa`
- Şifre: *(boş)*

---

## Teknolojiler

| Teknoloji | Versiyon |
|-----------|----------|
| Java | 17 |
| Spring Boot | 3.2.5 |
| Spring Data JPA | - |
| H2 Database | - |
| JWT (jjwt) | 0.12.3 |
| Lombok | - |
| Maven | 3.8+ |
