-- ============================================================
--  KÜTÜPHANE YÖNETİM SİSTEMİ  –  Veritabanı Scripti
--  Tablolar : KATEGORI, YAZAR, KITAP, OGRENCI,
--             GOREVLI, ODUNC_ALMA, IADE, CEZA
--  Bölümler : DDL (CREATE / ALTER / DROP)
--             DML (INSERT / UPDATE / DELETE / SELECT)
-- ============================================================


-- ============================================================
-- BÖLÜM 1 – DDL (Data Definition Language)
-- ============================================================

-- Varsa eski tabloları sıralı olarak sil (FK bağımlılığına göre)
DROP TABLE IF EXISTS CEZA;
DROP TABLE IF EXISTS IADE;
DROP TABLE IF EXISTS ODUNC_ALMA;
DROP TABLE IF EXISTS KITAP;
DROP TABLE IF EXISTS YAZAR;
DROP TABLE IF EXISTS KATEGORI;
DROP TABLE IF EXISTS OGRENCI;
DROP TABLE IF EXISTS GOREVLI;


-- ------------------------------------------------------------
-- 1.1  KATEGORİ
-- ------------------------------------------------------------
CREATE TABLE KATEGORI (
    KategoriID   INT          NOT NULL AUTO_INCREMENT,
    Ad           VARCHAR(100) NOT NULL,
    Aciklama     VARCHAR(255),
    CONSTRAINT PK_KATEGORI PRIMARY KEY (KategoriID),
    CONSTRAINT UQ_KATEGORI_AD UNIQUE (Ad)
);


-- ------------------------------------------------------------
-- 1.2  YAZAR
-- ------------------------------------------------------------
CREATE TABLE YAZAR (
    YazarID   INT          NOT NULL AUTO_INCREMENT,
    Ad        VARCHAR(100) NOT NULL,
    Soyad     VARCHAR(100) NOT NULL,
    Uyruk     VARCHAR(80),
    CONSTRAINT PK_YAZAR PRIMARY KEY (YazarID)
);


-- ------------------------------------------------------------
-- 1.3  KİTAP
-- ------------------------------------------------------------
CREATE TABLE KITAP (
    KitapID       INT          NOT NULL AUTO_INCREMENT,
    ISBN          VARCHAR(20)  NOT NULL,
    Baslik        VARCHAR(255) NOT NULL,
    YazarID       INT          NOT NULL,
    KategoriID    INT          NOT NULL,
    Yayinevi      VARCHAR(150),
    YayinYili     SMALLINT,
    SayfaSayisi   SMALLINT,
    ToplamKopya   TINYINT      NOT NULL DEFAULT 1,
    MevcutKopya   TINYINT      NOT NULL DEFAULT 1,
    Dil           VARCHAR(50)  DEFAULT 'Türkçe',
    RafKonum      VARCHAR(20),
    CONSTRAINT PK_KITAP      PRIMARY KEY (KitapID),
    CONSTRAINT UQ_KITAP_ISBN UNIQUE (ISBN),
    CONSTRAINT FK_KITAP_YAZAR     FOREIGN KEY (YazarID)    REFERENCES YAZAR    (YazarID),
    CONSTRAINT FK_KITAP_KATEGORI  FOREIGN KEY (KategoriID) REFERENCES KATEGORI (KategoriID),
    CONSTRAINT CK_KITAP_KOPYA     CHECK (MevcutKopya >= 0 AND MevcutKopya <= ToplamKopya)
);


-- ------------------------------------------------------------
-- 1.4  ÖĞRENCİ
-- ------------------------------------------------------------
CREATE TABLE OGRENCI (
    OgrenciID     INT          NOT NULL AUTO_INCREMENT,
    OgrenciNo     VARCHAR(20)  NOT NULL,
    Ad            VARCHAR(100) NOT NULL,
    Soyad         VARCHAR(100) NOT NULL,
    Email         VARCHAR(150) NOT NULL,
    Telefon       VARCHAR(20),
    Bolum         VARCHAR(150),
    Sinif         TINYINT,
    KayitTarihi   DATE         NOT NULL DEFAULT (CURRENT_DATE),
    AktifMi       BOOLEAN      NOT NULL DEFAULT TRUE,
    CONSTRAINT PK_OGRENCI          PRIMARY KEY (OgrenciID),
    CONSTRAINT UQ_OGRENCI_NO       UNIQUE (OgrenciNo),
    CONSTRAINT UQ_OGRENCI_EMAIL    UNIQUE (Email),
    CONSTRAINT CK_OGRENCI_SINIF    CHECK (Sinif BETWEEN 1 AND 4)
);


-- ------------------------------------------------------------
-- 1.5  GÖREVLİ
-- ------------------------------------------------------------
CREATE TABLE GOREVLI (
    GorevliID       INT          NOT NULL AUTO_INCREMENT,
    SicilNo         VARCHAR(20)  NOT NULL,
    Ad              VARCHAR(100) NOT NULL,
    Soyad           VARCHAR(100) NOT NULL,
    Email           VARCHAR(150) NOT NULL,
    Telefon         VARCHAR(20),
    Pozisyon        VARCHAR(100) DEFAULT 'Kütüphaneci',
    IsBaslamaTarihi DATE,
    AktifMi         BOOLEAN      NOT NULL DEFAULT TRUE,
    CONSTRAINT PK_GOREVLI       PRIMARY KEY (GorevliID),
    CONSTRAINT UQ_GOREVLI_SICIL UNIQUE (SicilNo),
    CONSTRAINT UQ_GOREVLI_EMAIL UNIQUE (Email)
);


-- ------------------------------------------------------------
-- 1.6  ÖDÜNÇ ALMA
-- ------------------------------------------------------------
CREATE TABLE ODUNC_ALMA (
    OduncID              INT  NOT NULL AUTO_INCREMENT,
    OgrenciID            INT  NOT NULL,
    KitapID              INT  NOT NULL,
    GorevliID            INT  NOT NULL,
    OduncTarihi          DATE NOT NULL DEFAULT (CURRENT_DATE),
    PlanlananIadeTarihi  DATE NOT NULL,
    Durum                VARCHAR(30) NOT NULL DEFAULT 'Aktif',
    CONSTRAINT PK_ODUNC         PRIMARY KEY (OduncID),
    CONSTRAINT FK_ODUNC_OGRENCI FOREIGN KEY (OgrenciID) REFERENCES OGRENCI (OgrenciID),
    CONSTRAINT FK_ODUNC_KITAP   FOREIGN KEY (KitapID)   REFERENCES KITAP   (KitapID),
    CONSTRAINT FK_ODUNC_GOREVLI FOREIGN KEY (GorevliID) REFERENCES GOREVLI (GorevliID),
    CONSTRAINT CK_ODUNC_DURUM   CHECK (Durum IN ('Aktif', 'Iade Edildi', 'Gecikti', 'Kayip')),
    CONSTRAINT CK_ODUNC_TARIH   CHECK (PlanlananIadeTarihi > OduncTarihi)
);


-- ------------------------------------------------------------
-- 1.7  İADE
-- ------------------------------------------------------------
CREATE TABLE IADE (
    IadeID        INT          NOT NULL AUTO_INCREMENT,
    OduncID       INT          NOT NULL,
    GorevliID     INT          NOT NULL,
    IadeTarihi    DATE         NOT NULL DEFAULT (CURRENT_DATE),
    KitapDurumu   VARCHAR(50)  NOT NULL DEFAULT 'İyi',
    GecGunSayisi  SMALLINT     NOT NULL DEFAULT 0,
    Notlar        VARCHAR(255),
    CONSTRAINT PK_IADE         PRIMARY KEY (IadeID),
    CONSTRAINT UQ_IADE_ODUNC   UNIQUE (OduncID),     -- Her ödünç yalnızca 1 iade kaydı
    CONSTRAINT FK_IADE_ODUNC   FOREIGN KEY (OduncID)   REFERENCES ODUNC_ALMA (OduncID),
    CONSTRAINT FK_IADE_GOREVLI FOREIGN KEY (GorevliID) REFERENCES GOREVLI    (GorevliID),
    CONSTRAINT CK_IADE_DURUM   CHECK (KitapDurumu IN ('İyi', 'Yıpranmış', 'Hasarlı', 'Kayıp'))
);


-- ------------------------------------------------------------
-- 1.8  CEZA
-- ------------------------------------------------------------
CREATE TABLE CEZA (
    CezaID        INT            NOT NULL AUTO_INCREMENT,
    OduncID       INT            NOT NULL,
    OgrenciID     INT            NOT NULL,
    Tip           VARCHAR(50)    NOT NULL,
    Tutar         DECIMAL(8,2)   NOT NULL DEFAULT 0.00,
    OdemeDurumu   VARCHAR(20)    NOT NULL DEFAULT 'Ödenmedi',
    OlusumTarihi  DATE           NOT NULL DEFAULT (CURRENT_DATE),
    OdemeTarihi   DATE,
    Aciklama      VARCHAR(255),
    CONSTRAINT PK_CEZA          PRIMARY KEY (CezaID),
    CONSTRAINT FK_CEZA_ODUNC    FOREIGN KEY (OduncID)   REFERENCES ODUNC_ALMA (OduncID),
    CONSTRAINT FK_CEZA_OGRENCI  FOREIGN KEY (OgrenciID) REFERENCES OGRENCI    (OgrenciID),
    CONSTRAINT CK_CEZA_TIP      CHECK (Tip IN ('Gecikme', 'Hasar', 'Kayıp')),
    CONSTRAINT CK_CEZA_ODEME    CHECK (OdemeDurumu IN ('Ödendi', 'Ödenmedi', 'Muaf')),
    CONSTRAINT CK_CEZA_TUTAR    CHECK (Tutar >= 0)
);


-- ============================================================
-- BÖLÜM 2 – DML (Data Manipulation Language)
-- ============================================================


-- ============================================================
-- 2.1  KATEGORİ – DML
-- ============================================================

-- INSERT (5 kayıt)
INSERT INTO KATEGORI (Ad, Aciklama) VALUES
    ('Roman',        'Kurgusal uzun anlatı edebi türü'),
    ('Bilim',        'Doğa ve uygulamalı bilimler'),
    ('Tarih',        'Tarihsel olaylar ve biyografiler'),
    ('Felsefe',      'Düşünce, etik ve metafizik'),
    ('Bilgisayar',   'Yazılım, donanım ve algoritma kitapları'),
    ('Psikoloji',    'Davranış bilimleri ve kişisel gelişim');

-- UPDATE: Bilgisayar kategorisi açıklamasını güncelle
UPDATE KATEGORI
SET    Aciklama = 'Yazılım geliştirme, yapay zeka ve sistem tasarımı'
WHERE  Ad = 'Bilgisayar';

-- SELECT: Tüm kategorileri listele
SELECT KategoriID, Ad, Aciklama
FROM   KATEGORI
ORDER  BY Ad;

-- SELECT: Adında 'i' geçen kategoriler
SELECT * FROM KATEGORI WHERE LOWER(Ad) LIKE '%i%';

-- DELETE: Test amaçlı eklenen geçici kategoriyi sil
INSERT INTO KATEGORI (Ad) VALUES ('GeciGiKategori');
DELETE FROM KATEGORI WHERE Ad = 'GeciGiKategori';


-- ============================================================
-- 2.2  YAZAR – DML
-- ============================================================

-- INSERT (6 kayıt)
INSERT INTO YAZAR (Ad, Soyad, Uyruk) VALUES
    ('Sabahattin',  'Ali',         'Türk'),
    ('Orhan',       'Pamuk',       'Türk'),
    ('Yuval Noah',  'Harari',      'İsrailli'),
    ('George',      'Orwell',      'İngiliz'),
    ('Fyodor',      'Dostoyevski', 'Rus'),
    ('Donald',      'Knuth',       'Amerikalı');

-- UPDATE: Yazar uyruğunu güncelle
UPDATE YAZAR SET Uyruk = 'Britanyalı' WHERE Soyad = 'Orwell';

-- SELECT: Türk yazarları listele
SELECT Ad, Soyad FROM YAZAR WHERE Uyruk = 'Türk';

-- SELECT: Tüm yazarları soyadına göre sırala
SELECT YazarID, Ad, Soyad, Uyruk FROM YAZAR ORDER BY Soyad;

-- DELETE: Test kaydı ekle ve sil
INSERT INTO YAZAR (Ad, Soyad) VALUES ('Test', 'Yazar');
DELETE FROM YAZAR WHERE Ad = 'Test' AND Soyad = 'Yazar';


-- ============================================================
-- 2.3  KİTAP – DML
-- ============================================================

-- INSERT (8 kayıt)
INSERT INTO KITAP (ISBN, Baslik, YazarID, KategoriID, Yayinevi, YayinYili, SayfaSayisi, ToplamKopya, MevcutKopya, RafKonum) VALUES
    ('978-975-07-0001', 'Kuyucaklı Yusuf',            1, 1, 'Yapı Kredi', 1937, 272, 3, 3, 'A1'),
    ('978-975-07-0002', 'Benim Adım Kırmızı',          2, 1, 'İletişim',  1998, 418, 2, 2, 'A2'),
    ('978-975-08-0003', 'Sapiens',                     3, 3, 'Kolektif',  2011, 464, 4, 4, 'B1'),
    ('978-975-08-0004', 'Hayvan Çiftliği',             4, 1, 'Can',       1945, 152, 5, 5, 'A3'),
    ('978-975-08-0005', '1984',                        4, 1, 'Can',       1949, 352, 4, 4, 'A4'),
    ('978-975-09-0006', 'Suç ve Ceza',                 5, 1, 'İş Bankası',1866, 671, 3, 3, 'A5'),
    ('978-975-09-0007', 'The Art of Computer Prog.',   6, 5, 'Addison',   1968, 896, 2, 2, 'C1'),
    ('978-975-09-0008', 'Karamazov Kardeşler',         5, 1, 'İş Bankası',1880, 824, 2, 2, 'A6');

-- UPDATE: Kopya sayısını güncelle
UPDATE KITAP SET ToplamKopya = 5, MevcutKopya = 5 WHERE ISBN = '978-975-07-0002';

-- UPDATE: Raf konumunu değiştir
UPDATE KITAP SET RafKonum = 'A7' WHERE ISBN = '978-975-09-0008';

-- SELECT: Mevcut kopyası olan kitapları listele
SELECT KitapID, Baslik, MevcutKopya, ToplamKopya, RafKonum
FROM   KITAP
WHERE  MevcutKopya > 0
ORDER  BY Baslik;

-- SELECT: Kategori adıyla birlikte kitap listesi (JOIN)
SELECT k.KitapID, k.Baslik, y.Ad || ' ' || y.Soyad AS Yazar,
       kat.Ad AS Kategori, k.YayinYili
FROM   KITAP k
JOIN   YAZAR    y   ON k.YazarID    = y.YazarID
JOIN   KATEGORI kat ON k.KategoriID = kat.KategoriID
ORDER  BY k.Baslik;

-- SELECT: 400 sayfadan uzun kitaplar
SELECT Baslik, SayfaSayisi FROM KITAP WHERE SayfaSayisi > 400 ORDER BY SayfaSayisi DESC;

-- DELETE: Test kaydı
INSERT INTO KITAP (ISBN, Baslik, YazarID, KategoriID, ToplamKopya, MevcutKopya)
VALUES ('000-000-00000', 'Test Kitap', 1, 1, 1, 1);
DELETE FROM KITAP WHERE ISBN = '000-000-00000';


-- ============================================================
-- 2.4  ÖĞRENCİ – DML
-- ============================================================

-- INSERT (7 kayıt)
INSERT INTO OGRENCI (OgrenciNo, Ad, Soyad, Email, Telefon, Bolum, Sinif, KayitTarihi) VALUES
    ('20210001', 'Ahmet',    'Yılmaz',  'ahmet@uni.edu.tr',   '0532 100 0001', 'Bilgisayar Müh.', 3, '2021-09-15'),
    ('20210002', 'Zeynep',   'Kara',    'zeynep@uni.edu.tr',  '0533 100 0002', 'Matematik',       2, '2021-09-15'),
    ('20220003', 'Mert',     'Çelik',   'mert@uni.edu.tr',    '0534 100 0003', 'Türk Dili',       2, '2022-09-12'),
    ('20220004', 'Elif',     'Şahin',   'elif@uni.edu.tr',    '0535 100 0004', 'Tarih',           1, '2022-09-12'),
    ('20230005', 'Burak',    'Arslan',  'burak@uni.edu.tr',   '0536 100 0005', 'Felsefe',         1, '2023-09-11'),
    ('20190006', 'Selin',    'Doğan',   'selin@uni.edu.tr',   '0537 100 0006', 'Psikoloji',       4, '2019-09-16'),
    ('20200007', 'Kerem',    'Aydın',   'kerem@uni.edu.tr',   '0538 100 0007', 'Bilgisayar Müh.', 4, '2020-09-14');

-- UPDATE: Sınıf yükseltme
UPDATE OGRENCI SET Sinif = 4 WHERE OgrenciNo = '20210001';

-- UPDATE: Öğrenciyi pasife al (mezun / bıraktı)
UPDATE OGRENCI SET AktifMi = FALSE WHERE OgrenciNo = '20190006';

-- SELECT: Aktif öğrencileri listele
SELECT OgrenciNo, Ad, Soyad, Bolum, Sinif
FROM   OGRENCI
WHERE  AktifMi = TRUE
ORDER  BY Soyad, Ad;

-- SELECT: Bilgisayar Mühendisliği öğrencileri
SELECT OgrenciNo, Ad, Soyad FROM OGRENCI WHERE Bolum = 'Bilgisayar Müh.';

-- SELECT: Toplam öğrenci sayısını bölüme göre say
SELECT Bolum, COUNT(*) AS OgrenciSayisi
FROM   OGRENCI
GROUP  BY Bolum
ORDER  BY OgrenciSayisi DESC;

-- DELETE: Test öğrencisi
INSERT INTO OGRENCI (OgrenciNo, Ad, Soyad, Email, Sinif)
VALUES ('99999999', 'Test', 'Test', 'test@test.com', 1);
DELETE FROM OGRENCI WHERE OgrenciNo = '99999999';


-- ============================================================
-- 2.5  GÖREVLİ – DML
-- ============================================================

-- INSERT (5 kayıt)
INSERT INTO GOREVLI (SicilNo, Ad, Soyad, Email, Telefon, Pozisyon, IsBaslamaTarihi) VALUES
    ('G001', 'Hüseyin', 'Çevik',   'huseyin@kutuphane.gov.tr', '0312 200 0001', 'Müdür',          '2015-03-01'),
    ('G002', 'Fatma',   'Yıldız',  'fatma@kutuphane.gov.tr',   '0312 200 0002', 'Kütüphaneci',    '2018-06-15'),
    ('G003', 'Osman',   'Kurt',    'osman@kutuphane.gov.tr',   '0312 200 0003', 'Kütüphaneci',    '2019-01-10'),
    ('G004', 'Hatice',  'Parlak',  'hatice@kutuphane.gov.tr',  '0312 200 0004', 'Ödünç Görevlisi','2020-09-01'),
    ('G005', 'Emre',    'Tuncer',  'emre@kutuphane.gov.tr',    '0312 200 0005', 'Ödünç Görevlisi','2021-04-20');

-- UPDATE: Pozisyon güncelle
UPDATE GOREVLI SET Pozisyon = 'Kıdemli Kütüphaneci' WHERE SicilNo = 'G002';

-- UPDATE: Telefon güncelle
UPDATE GOREVLI SET Telefon = '0312 200 0099' WHERE SicilNo = 'G001';

-- SELECT: Tüm görevlileri listele
SELECT SicilNo, Ad, Soyad, Pozisyon, IsBaslamaTarihi
FROM   GOREVLI
WHERE  AktifMi = TRUE
ORDER  BY IsBaslamaTarihi;

-- SELECT: Pozisyona göre görevli sayısı
SELECT Pozisyon, COUNT(*) AS Sayi FROM GOREVLI GROUP BY Pozisyon;

-- DELETE: Test görevlisi
INSERT INTO GOREVLI (SicilNo, Ad, Soyad, Email) VALUES ('G999', 'Test', 'Test', 'testg@test.com');
DELETE FROM GOREVLI WHERE SicilNo = 'G999';


-- ============================================================
-- 2.6  ÖDÜNÇ ALMA – DML
-- ============================================================

-- INSERT (8 ödünç kaydı)
INSERT INTO ODUNC_ALMA (OgrenciID, KitapID, GorevliID, OduncTarihi, PlanlananIadeTarihi, Durum) VALUES
    (1, 1, 4, '2024-10-01', '2024-10-15', 'Iade Edildi'),
    (2, 3, 4, '2024-10-05', '2024-10-19', 'Iade Edildi'),
    (3, 5, 5, '2024-10-10', '2024-10-24', 'Iade Edildi'),
    (4, 4, 5, '2024-11-01', '2024-11-15', 'Gecikti'),
    (5, 2, 4, '2024-11-10', '2024-11-24', 'Aktif'),
    (6, 6, 5, '2024-11-12', '2024-11-26', 'Aktif'),
    (7, 7, 4, '2024-11-14', '2024-11-28', 'Aktif'),
    (1, 8, 5, '2024-11-15', '2024-11-29', 'Aktif');

-- Kitap mevcut kopya sayısını düş (ödünç verilen kitaplar için)
UPDATE KITAP SET MevcutKopya = MevcutKopya - 1 WHERE KitapID IN (2, 6, 7, 8);

-- UPDATE: Geciken ödüncü işaretle
UPDATE ODUNC_ALMA SET Durum = 'Gecikti'
WHERE  OduncID = 4 AND PlanlananIadeTarihi < CURRENT_DATE AND Durum = 'Aktif';

-- SELECT: Aktif ödünç kayıtlarını öğrenci ve kitap adıyla göster
SELECT oa.OduncID,
       o.Ad || ' ' || o.Soyad      AS Ogrenci,
       k.Baslik                    AS Kitap,
       oa.OduncTarihi,
       oa.PlanlananIadeTarihi,
       oa.Durum
FROM   ODUNC_ALMA oa
JOIN   OGRENCI o ON oa.OgrenciID = o.OgrenciID
JOIN   KITAP   k ON oa.KitapID   = k.KitapID
WHERE  oa.Durum = 'Aktif'
ORDER  BY oa.PlanlananIadeTarihi;

-- SELECT: Geciken ödünçler
SELECT oa.OduncID, o.Ad || ' ' || o.Soyad AS Ogrenci,
       k.Baslik, oa.PlanlananIadeTarihi,
       (CURRENT_DATE - oa.PlanlananIadeTarihi) AS GecGun
FROM   ODUNC_ALMA oa
JOIN   OGRENCI o ON oa.OgrenciID = o.OgrenciID
JOIN   KITAP   k ON oa.KitapID   = k.KitapID
WHERE  oa.Durum = 'Gecikti';

-- SELECT: Öğrencinin tüm ödünç geçmişi
SELECT k.Baslik, oa.OduncTarihi, oa.PlanlananIadeTarihi, oa.Durum
FROM   ODUNC_ALMA oa
JOIN   KITAP k ON oa.KitapID = k.KitapID
WHERE  oa.OgrenciID = 1
ORDER  BY oa.OduncTarihi DESC;


-- ============================================================
-- 2.7  İADE – DML
-- ============================================================

-- INSERT (3 iade kaydı — iade edilmiş ödünçler için)
INSERT INTO IADE (OduncID, GorevliID, IadeTarihi, KitapDurumu, GecGunSayisi, Notlar) VALUES
    (1, 4, '2024-10-14', 'İyi',       0,  NULL),
    (2, 5, '2024-10-20', 'İyi',       1,  'Bir gün gecikme'),
    (3, 4, '2024-10-25', 'Yıpranmış', 1,  'Kapak hafif yıpranmış');

-- Kitap mevcut kopya sayısını geri artır
UPDATE KITAP SET MevcutKopya = MevcutKopya + 1
WHERE  KitapID IN (
    SELECT KitapID FROM ODUNC_ALMA WHERE OduncID IN (1, 2, 3)
);

-- Ödünç durumunu güncelle
UPDATE ODUNC_ALMA SET Durum = 'Iade Edildi' WHERE OduncID IN (1, 2, 3);

-- UPDATE: İade notunu düzenle
UPDATE IADE SET Notlar = 'Kapak yıpranmış, içerik temiz' WHERE IadeID = 3;

-- SELECT: İade kayıtlarını detaylı listele
SELECT ia.IadeID,
       o.Ad || ' ' || o.Soyad   AS Ogrenci,
       k.Baslik                  AS Kitap,
       ia.IadeTarihi,
       ia.KitapDurumu,
       ia.GecGunSayisi,
       g.Ad || ' ' || g.Soyad   AS KabulEdenGorevli
FROM   IADE ia
JOIN   ODUNC_ALMA oa ON ia.OduncID   = oa.OduncID
JOIN   OGRENCI    o  ON oa.OgrenciID = o.OgrenciID
JOIN   KITAP      k  ON oa.KitapID   = k.KitapID
JOIN   GOREVLI    g  ON ia.GorevliID = g.GorevliID
ORDER  BY ia.IadeTarihi DESC;

-- SELECT: Hasarlı veya yıpranmış iade edilen kitaplar
SELECT k.Baslik, ia.KitapDurumu, ia.IadeTarihi, ia.Notlar
FROM   IADE ia
JOIN   ODUNC_ALMA oa ON ia.OduncID = oa.OduncID
JOIN   KITAP      k  ON oa.KitapID = k.KitapID
WHERE  ia.KitapDurumu IN ('Yıpranmış', 'Hasarlı');

-- SELECT: Gecikme sayısı > 0 olan iadeler
SELECT ia.IadeID, ia.GecGunSayisi, ia.IadeTarihi
FROM   IADE ia WHERE ia.GecGunSayisi > 0;


-- ============================================================
-- 2.8  CEZA – DML
-- ============================================================

-- INSERT (5 ceza kaydı)
-- Gecikme: Gün başına 2 TL
-- Hasar: 50 TL sabit
INSERT INTO CEZA (OduncID, OgrenciID, Tip, Tutar, OdemeDurumu, OlusumTarihi, Aciklama) VALUES
    (2, 2, 'Gecikme', 2.00,  'Ödendi',   '2024-10-21', '1 gün gecikme x 2 TL'),
    (3, 3, 'Gecikme', 2.00,  'Ödendi',   '2024-10-26', '1 gün gecikme x 2 TL'),
    (3, 3, 'Hasar',   50.00, 'Ödenmedi', '2024-10-26', 'Kapak yıpranması'),
    (4, 4, 'Gecikme', 30.00, 'Ödenmedi', '2024-11-20', '15 gün gecikme x 2 TL'),
    (4, 4, 'Hasar',   75.00, 'Ödenmedi', '2024-11-20', 'Kitap sayfaları yırtılmış');

-- UPDATE: Ceza ödeme durumunu güncelle
UPDATE CEZA
SET    OdemeDurumu = 'Ödendi',
       OdemeTarihi = '2024-11-25'
WHERE  CezaID = 3;

-- UPDATE: Ceza muaf tut (özel durum)
UPDATE CEZA SET OdemeDurumu = 'Muaf', Aciklama = 'İlk ceza muafiyeti uygulandı'
WHERE  CezaID = 4;

-- SELECT: Ödenmeyen cezaları listele
SELECT c.CezaID,
       o.Ad || ' ' || o.Soyad AS Ogrenci,
       c.Tip,
       c.Tutar,
       c.OlusumTarihi,
       c.Aciklama
FROM   CEZA c
JOIN   OGRENCI o ON c.OgrenciID = o.OgrenciID
WHERE  c.OdemeDurumu = 'Ödenmedi'
ORDER  BY c.Tutar DESC;

-- SELECT: Öğrenci bazında toplam ceza tutarı
SELECT o.Ad || ' ' || o.Soyad AS Ogrenci,
       SUM(c.Tutar)            AS ToplamCeza,
       SUM(CASE WHEN c.OdemeDurumu = 'Ödenmedi' THEN c.Tutar ELSE 0 END) AS BekleyenCeza
FROM   CEZA c
JOIN   OGRENCI o ON c.OgrenciID = o.OgrenciID
GROUP  BY o.OgrenciID, o.Ad, o.Soyad
ORDER  BY ToplamCeza DESC;

-- SELECT: Ceza türü istatistikleri
SELECT Tip,
       COUNT(*)     AS Sayi,
       SUM(Tutar)   AS ToplamTutar,
       AVG(Tutar)   AS OrtTutar
FROM   CEZA
GROUP  BY Tip;

-- DELETE: Test cezası
INSERT INTO CEZA (OduncID, OgrenciID, Tip, Tutar) VALUES (1, 1, 'Gecikme', 0.00);
DELETE FROM CEZA WHERE Tutar = 0.00 AND Tip = 'Gecikme';


-- ============================================================
-- BÖLÜM 3 – Faydalı Sorgu Örnekleri (Bonus)
-- ============================================================

-- En çok ödünç alınan kitaplar
SELECT k.Baslik, COUNT(oa.OduncID) AS OduncSayisi
FROM   KITAP k
LEFT   JOIN ODUNC_ALMA oa ON k.KitapID = oa.KitapID
GROUP  BY k.KitapID, k.Baslik
ORDER  BY OduncSayisi DESC;

-- Hiç ödünç alınmamış kitaplar
SELECT k.Baslik, k.RafKonum
FROM   KITAP k
WHERE  k.KitapID NOT IN (SELECT DISTINCT KitapID FROM ODUNC_ALMA);

-- Cezası olan aktif öğrenciler
SELECT DISTINCT o.OgrenciNo, o.Ad, o.Soyad, o.Bolum
FROM   OGRENCI o
JOIN   CEZA c ON o.OgrenciID = c.OgrenciID
WHERE  o.AktifMi = TRUE AND c.OdemeDurumu = 'Ödenmedi';

-- Aylık ödünç alma istatistiği
SELECT STRFTIME('%Y-%m', OduncTarihi) AS Ay,
       COUNT(*) AS ToplamOdunc
FROM   ODUNC_ALMA
GROUP  BY Ay
ORDER  BY Ay;

-- ============================================================
-- Sorgu bitti.
-- ============================================================
