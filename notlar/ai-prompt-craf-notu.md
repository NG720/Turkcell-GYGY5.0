# AI Dersi — Prompt Engineering (CRAF Formatı)

---

## Prompt Engineering Nedir?

Yapay zeka modellerine doğru, tutarlı ve kullanışlı yanıtlar aldırmak için
girdi metnini (prompt'u) bilinçli ve yapısal olarak tasarlama sanatıdır.

Aynı soruyu farklı şekillerde sorduğunda farklı kalitede yanıtlar alırsın.
Prompt engineering bu farkı sistematik hale getirir.

---

## Neden Önemli?

AI modelleri bağlama duyarlıdır. Ne kadar fazla ve doğru bağlam verirsen,
o kadar odaklı ve işe yarar yanıt alırsın.

Kötü prompt:
```
JWT nasıl yapılır?
```

İyi prompt (CRAF ile):
```
Spring Boot kursundayım, JWT konusu anlamadım.
Sen deneyimli bir Java eğitmensin.
Mevcut bir proje üzerinde adım adım JWT kur, her adımı açıkla.
Önce kavramsal anlat, sonra kod ver, en altta sık yapılan hataları listele.
Junior geliştiricilere hitap eden samimi ama teknik bir ton kullan.
```

---

## CRAF Formatı

---

### C — Context (Bağlam)

AI'ya kim olduğunu, ne bildiğini ve nerede olduğunu anlat.
Bağlam ne kadar net olursa yanıt o kadar odaklı olur.

Zayıf bağlam:
```
Spring öğreniyorum.
```

Güçlü bağlam:
```
Bir Spring Boot geliştirme kursundayım.
Temel Java ve Spring Boot bilgisine sahibim.
Ancak son derste anlatılan Auth ve JWT konusu tam oturmadı.
Bu konuyu anlamak için kaynak oluşturmaya çalışıyorum.
```

Bağlam şunları kapsamalı:
- Deneyim seviyesi (junior, mid, senior)
- Hangi teknoloji / framework kullanıldığı
- Ne bilinip ne bilinmediği
- Amacın ne olduğu

---

### R — Role (Rol)

AI'ya hangi rolde davranmasını istediğini söyle.
Rol ne kadar somut tanımlanırsa yanıt o kadar karaktere bürünür.

Zayıf rol:
```
Sen bir yazılımcısın.
```

Güçlü rol:
```
Sen 10 yıllık bir Java Spring geliştiricisi ve danışmanısın.
Aktif olarak bu konuda eğitim veriyorsun.
Gerçek hayattan örnekler vermeyi seven bir eğitmensin.
Önce teoriyi sonra pratiği anlatıyorsun.
```

Rol tanımlarken şunları ekle:
- Kaç yıllık deneyim
- Uzmanlık alanı
- Çalışma tarzı (örnekler seven, adım adım anlatan vs.)
- Pedagojik yaklaşım (önce teori mi, pratik mi)

---

### A — Action (Aksiyon)

Ne yapmasını istediğini net, sınırlı ve ölçülebilir şekilde belirt.
"İstenilenin dışına çıkma" ifadesi burada kritik — AI'nın konu dışına
sapmasını engellemek için action'ı kısıtlayabilirsin.

Zayıf aksiyon:
```
JWT anlat.
```

Güçlü aksiyon:
```
Var olan bir Spring projesi üzerinde sıfırdan açıklamalar yaparak
JWT ile Auth sistemi kur. Kurarken her aksiyonda kullanıcıya nedeni açıkla.
Bu bağlamda action'da istenilenin dışına çıkmak yasak.
```

Aksiyon şunları içermeli:
- Ne yapılacağı (kur, açıkla, listele, karşılaştır)
- Kısıtlar (neyi yapmaması gerektiği)
- Çıktının kapsamı (sadece bu konu, bu proje, bu adım)

---

### F — Format (Çıktı Formatı)

Cevabın nasıl görünmesini istediğini önceden tanımla.
Format belirtilmezse AI kendi yapısını seçer — bu her zaman işe yaramaz.

Zayıf format:
```
(format belirtilmedi)
```

Güçlü format:
```
- Her alt konuyu önce 2-3 paragraf kavramsal açıkla
- Her implementasyonda 1.adım 2.adım diye sırayla git
- En altta bu konuda sık yapılan 5 hata ve çözümünü ver
- Emoji kullanma, resmi ol
```

Format seçenekleri:
- Madde listesi / numaralı liste
- Adım adım (step-by-step)
- Tablo
- Kod bloğu ile açıklama
- Özet + detay yapısı
- Hata listesi / anti-pattern bölümü

---

### T — Tone (Ton / Hedef Kitle)

Kime hitap ettiğini ve nasıl bir dil kullanılmasını istediğini belirt.
Aynı içerik farklı kitlelere farklı şekilde anlatılır.

Zayıf ton:
```
(ton belirtilmedi)
```

Güçlü ton:
```
Hedef Kitle: Junior Spring Geliştiriciler

Ton: Samimi ama teknik, resmiyetten ödün vermeyen bir ton kullan.
Örnekleri net somut gerçek hayat örnekleri olarak ver.
```

Ton belirlerken düşün:
- Kitle kim? (öğrenci, junior, senior, müşteri)
- Ne kadar teknik olsun?
- Resmi mi, samimi mi?
- Örnekler soyut mu, somut mu olsun?

---

## CRAF Şablonu

```
[C - Context]
<Kimsin, ne biliyorsun, nerede olduğunu anlat>

[R - Role]
<AI'ya hangi rolü oynamasını istediğini söyle>

[A - Action]
<Ne yapmasını istediğini net olarak belirt, kısıtları ekle>

[F - Format]
<Çıktının yapısını ve biçimini tanımla>

[T - Tone]
<Hedef kitle ve ton>
```

---

## İyi ve Kötü Prompt Karşılaştırması

| | Kötü Prompt | İyi Prompt (CRAF) |
|--|-------------|-------------------|
| Bağlam | Yok | Kim olduğun, ne bildiğin |
| Rol | "Uzman ol" | 10 yıllık eğitmen, örneksever |
| Aksiyon | "Anlat" | "Adım adım kur, nedenini açıkla" |
| Format | Belirtilmemiş | Paragraf + adım + hata listesi |
| Ton | Belirtilmemiş | Junior kitleye teknik ama samimi |

---

## Kaynaklar

- https://www.kaggle.com/whitepaper-prompt-engineering
