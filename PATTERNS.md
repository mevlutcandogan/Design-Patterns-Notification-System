## 2. Decorator (Yapısal / Structural) - Faz 2

**Çözülen Problem:**
Sisteme loglama gibi yeni özellikler eklemek istediğimizde, mevcut bildirim sınıflarının (`EmailNotification`, vb.) kodlarına müdahale etmemiz gerekiyordu. Bu durum hem OCP (Açık/Kapalı Prensibi) ihlaline yol açıyor hem de özellikleri kalıtım (inheritance) ile eklemeye kalktığımızda "Sınıf Patlaması" (Class Explosion) riskini doğuruyordu.

**Nasıl Uygulandı:**
Kalıtım yerine kompozisyon (composition) tercih edildi. Mevcut sınıflara dokunmamak için `NotificationDecorator` adında soyut bir sarmalayıcı (wrapper) sınıf oluşturuldu. Somut süsleyici olan `LoggingDecorator`, asıl bildirimin gönderilme aşamasından önce ve sonra araya girerek orijinal nesneyi bozmadan loglama işlemini başarıyla gerçekleştirdi.

**UML Sınıf Diyagramı:**
```mermaid
classDiagram
    class Notification {
        <<interface>>
        +send(message: String)
    }
    class EmailNotification {
        +send(message: String)
    }
    class NotificationDecorator {
        <<abstract>>
        #wrappedNotification: Notification
        +send(message: String)
    }
    class LoggingDecorator {
        +send(message: String)
    }
    
    Notification <|.. EmailNotification
    Notification <|.. NotificationDecorator
    NotificationDecorator o-- Notification : sarmalar
    NotificationDecorator <|-- LoggingDecorator
