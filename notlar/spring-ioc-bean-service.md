# Spring IoC, Bean ve Service Nedir?

> Ön araştırma notu — ödev değil

---

## 🔄 IoC (Inversion of Control) — Kontrolün Tersine Çevrilmesi

Normalde sen nesne oluşturursun:
```java
UserService service = new UserService(); // sen kontrol ediyorsun
```

IoC ile Spring oluşturur, sen sadece kullanırsın:
```java
@Autowired
UserService service; // Spring kontrol ediyor
```

> Kısaca: **"Sen çağırma, biz veririz."**

Spring'in bu işi yaptığı yapıya **IoC Container** denir.

---

## 🫘 Bean Nedir?

Spring IoC Container'ın **yönettiği her Java nesnesi** bir Bean'dir.

```java
@Component
public class UserService {
    // Spring bu sınıftan otomatik bir Bean oluşturur
}
```

Bean'ler Spring tarafından:
- Oluşturulur
- Hafızada tutulur
- Gerektiğinde inject edilir
- Uygulama kapanınca yok edilir

---

## 🏷️ @Service, @Component, @Repository Farkı

Hepsi aslında Bean oluşturur, fark **anlamsaldır** (semantik):

| Annotation | Kullanım Yeri |
|------------|---------------|
| `@Component` | Genel amaçlı bean |
| `@Service` | İş mantığı (business logic) katmanı |
| `@Repository` | Veritabanı işlemleri katmanı |
| `@Controller` | HTTP isteklerini karşılayan katman |

```java
@Service
public class KitapService {
    // İş mantığı burada
}

@Repository
public class KitapRepository {
    // DB işlemleri burada
}
```

---

## 🔗 Dependency Injection (DI)

IoC'nin uygulanma biçimidir. Bean'leri birbirine **otomatik bağlar**.

```java
@Service
public class KitapService {

    @Autowired  // Spring, KitapRepository bean'ini buraya inject eder
    private KitapRepository kitapRepository;
}
```

---

## Özet

```
IoC Container
    │
    ├── Bean (UserService)
    ├── Bean (KitapRepository)  ──→ birbirine inject eder
    └── Bean (KitapController)
```

> IoC = "kontrol Spring'de"  
> Bean = "Spring'in yönettiği nesne"  
> @Service = "bu bir bean, iş mantığı katmanında"
