# Projede Kullanılan Tasarım Örüntüleri

## 1. Factory Method (Yaratımsal / Creational) - Faz 1

**Çözülen Problem:**
Projenin başlangıcında tüm bildirim tipleri (`Email`, `SMS`, `Push`) tek bir sınıf içinde karmaşık `if-else` bloklarıyla yönetiliyordu. Bu durum yeni bir bildirim tipi eklemeyi zorlaştırıyor ve kodu bakımı imkansız bir "spagetti" yapıya dönüştürüyordu.

**Nasıl Uygulandı:**
Nesne yaratma sorumluluğu, istemci sınıftan alınarak `NotificationFactory` sınıfına devredildi. 
- **Notification (Interface):** Tüm bildirimlerin uyması gereken standart bir sözleşme oluşturuldu.
- **Somut Sınıflar:** Her bildirim tipi (`EmailNotification`, `SMSNotification` vb.) bu arayüzü implemente ederek kendi gönderim mantığını kurdu.
- **NotificationFactory:** Gelen isteğe göre doğru nesneyi üretip döndüren merkezi fabrika birimi oluşturuldu.

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
    class SMSNotification {
        +send(message: String)
    }
    class PushNotification {
        +send(message: String)
    }
    class NotificationFactory {
        +createNotification(type: String) Notification
    }
    
    Notification <|.. EmailNotification
    Notification <|.. SMSNotification
    Notification <|.. PushNotification
    NotificationFactory ..> Notification : Üretir
