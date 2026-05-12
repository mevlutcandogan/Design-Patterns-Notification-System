## Structural (Yapısal) Örüntüler - Faz 2

**1. Decorator (Yapısal / Structural)**
* **Çözülen Problem:** Sisteme loglama gibi yeni özellikler eklemek istediğimizde, mevcut bildirim sınıflarının (EmailNotification, vb.) kodlarına müdahale etmemiz gerekiyordu. Bu durum hem OCP (Açık/Kapalı Prensibi) ihlaline yol açıyor hem de özellikleri kalıtım (inheritance) ile eklemeye kalktığımızda "Sınıf Patlaması" (Class Explosion) riskini doğuruyordu.
* **Nasıl Uygulandı:** Kalıtım yerine kompozisyon (composition) tercih edildi. Mevcut sınıflara dokunmamak için NotificationDecorator adında soyut bir sarmalayıcı (wrapper) sınıf oluşturuldu. Somut süsleyici olan LoggingDecorator, asıl bildirimin gönderilme aşamasından önce ve sonra araya girerek orijinal nesneyi bozmadan loglama işlemini başarıyla gerçekleştirdi.

**2. Facade Pattern (Yapısal / Structural) - İkinci Örüntü**
* **Çözülen Problem:** Main sınıfındaki nesne üretim kalabalığını gizlemek.
* **Nasıl Uygulandı:** Alt sistemin karmaşıklığını tek bir metoda indirmek ve temiz bir vitrin sunmak için `NotificationFacade` sınıfı eklendi.

**Güncel Faz 2 UML Diyagramı (Decorator + Facade):**
```mermaid
classDiagram
    class NotificationFacade {
        +sendLoggedNotification(message: String)
    }
    class Notification {
        <<interface>>
        +send(message: String)
    }
    class NotificationDecorator {
        <<abstract>>
    }
    class LoggingDecorator {
        +send(message: String)
    }
    
    NotificationFacade --> LoggingDecorator
    Notification <|.. NotificationDecorator
    NotificationDecorator o-- Notification
    NotificationDecorator <|-- LoggingDecorator
