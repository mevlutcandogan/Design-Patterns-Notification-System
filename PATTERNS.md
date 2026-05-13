# Projede Kullanılan Tasarım Örüntüleri

## 1. Factory Method (Yaratımsal / Creational) - Faz 1

**Çözülen Problem:**
Projenin başlangıcında tüm bildirim tipleri (`Email`, `SMS`, `Push`) tek bir sınıf içinde karmaşık `if-else` bloklarıyla yönetiliyordu. Bu durum yeni bir bildirim tipi eklemeyi zorlaştırıyor ve kodu bakımı imkansız bir "spagetti" yapıya dönüştürüyordu.

**Nasıl Uygulandı:**
Nesne yaratma sorumluluğu, istemci sınıftan alınarak `NotificationFactory` sınıfına devredildi. 
- **Notification (Interface):** Tüm bildirimlerin uyması gereken standart bir sözleşme oluşturuldu.
- **Somut Sınıflar:** Her bildirim tipi (`EmailNotification`, `SMSNotification` vb.) bu arayüzü implemente ederek kendi gönderim mantığını kurdu.
- **NotificationFactory:** Gelen isteğe göre doğru nesneyi üretip döndüren merkezi fabrika birimi oluşturuldu.

---

## 2. Structural (Yapısal) Örüntüler - Faz 2

**2.1. Decorator Pattern**
* **Çözülen Problem:** Sisteme loglama gibi yeni özellikler eklemek istediğimizde, mevcut bildirim sınıflarının (`EmailNotification`, vb.) kodlarına müdahale etmemiz gerekiyordu. Bu durum hem OCP (Açık/Kapalı Prensibi) ihlaline yol açıyor hem de özellikleri kalıtım (inheritance) ile eklemeye kalktığımızda "Sınıf Patlaması" (Class Explosion) riskini doğuruyordu.
* **Nasıl Uygulandı:** Kalıtım yerine kompozisyon (composition) tercih edildi. Mevcut sınıflara dokunmamak için `NotificationDecorator` adında soyut bir sarmalayıcı (wrapper) sınıf oluşturuldu. Somut süsleyici olan `LoggingDecorator`, asıl bildirimin gönderilme aşamasından önce ve sonra araya girerek orijinal nesneyi bozmadan loglama işlemini başarıyla gerçekleştirdi.

**2.2. Facade Pattern**
* **Çözülen Problem:** Nesne üretim ve loglama sarmalama işlemlerinin istemci tarafında (main metodu) yarattığı kod kirliliği ve karmaşıklık.
* **Nasıl Uygulandı:** `NotificationFacade` sınıfı bir vitrin görevi görerek karmaşık alt sistemleri tek bir metodun arkasına gizledi ve istemciye (ana test sınıfına) tertemiz bir arayüz sundu.

---

## 3. Behavioral (Davranışsal) Örüntüler - Faz 3

**3.1. Strategy Pattern**
* **Çözülen Problem:** Mesajların formatlanma biçimini (Standart, Acil vb.) mevcut kodu değiştirmeden dinamik olarak değiştirebilme ihtiyacı.
* **Nasıl Uygulandı:** `MessageStrategy` arayüzü tanımlanarak farklı formatlama stratejileri (`StandardMessageStrategy`, `UrgentMessageStrategy`) geliştirildi. Bu sayede sistem, Açık/Kapalı Prensibine (OCP) tam uyumlu hale getirildi.

**3.2. Observer Pattern**
* **Çözülen Problem:** Merkezi bir sistemden birden fazla aboneye (kullanıcıya) aynı anda bildirim iletme ve abonelik yönetimi gereksinimi.
* **Nasıl Uygulandı:** `Subscriber` arayüzü ve `NotificationCenter` (Yayıncı) sınıfı oluşturuldu. Kullanıcılar merkeze abone olduktan sonra, tek bir yayın (broadcast) ile tüm abonelere otomatik olarak mesaj iletildi.

---

## Güncel ve Nihai UML Sınıf Diyagramı (Sistemin Son Hali)

```mermaid
classDiagram
    class Notification { <<interface>> +send(message: String) }
    class EmailNotification { +send(message: String) }
    class SMSNotification { +send(message: String) }
    class PushNotification { +send(message: String) }
    
    class NotificationFactory { +createNotification(type: String): Notification }
    class NotificationFacade { +sendLoggedNotification(type: String, message: String) }
    
    class NotificationDecorator { <<abstract>> #wrappedNotification: Notification +send(message: String) }
    class LoggingDecorator { +send(message: String) }
    
    class MessageStrategy { <<interface>> +formatMessage(message: String): String }
    class StandardMessageStrategy { +formatMessage(message: String): String }
    class UrgentMessageStrategy { +formatMessage(message: String): String }
    
    class Subscriber { <<interface>> +update(message: String) }
    class UserSubscriber { -name: String +update(message: String) }
    
    class NotificationCenter {
        -subscribers: List~Subscriber~
        -strategy: MessageStrategy
        -facade: NotificationFacade
        +setStrategy(strategy: MessageStrategy)
        +subscribe(sub: Subscriber)
        +broadcast(rawMessage: String, type: String)
    }
    
    Notification <|.. EmailNotification
    Notification <|.. SMSNotification
    Notification <|.. PushNotification
    
    NotificationFactory ..> Notification : Üretir
    Notification <|.. NotificationDecorator
    NotificationDecorator o-- Notification : sarmalar
    NotificationDecorator <|-- LoggingDecorator
    
    NotificationCenter o-- Subscriber
    NotificationCenter o-- MessageStrategy
    NotificationCenter *-- NotificationFacade
    
    MessageStrategy <|.. StandardMessageStrategy
    MessageStrategy <|.. UrgentMessageStrategy
    Subscriber <|.. UserSubscriber
    NotificationFacade --> NotificationFactory
