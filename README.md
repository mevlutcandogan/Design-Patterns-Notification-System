# Tasarım Örüntüleri - Bildirim Sistemi (Konu A)

**Gerekçe:** Bildirim Sistemi konusunu seçtim çünkü günümüz yazılım projelerinde e-posta, SMS ve push bildirimlerinin aynı anda yönetilmesi çok yaygın bir ihtiyaç. Bu sistemin tasarım örüntüleriyle nasıl daha esnek ve genişletilebilir hale getirilebileceğini görmek istedim.

## Projenin Amacı
Bu proje, başlangıçta karmaşık `if-else` bloklarıyla yönetilen "spagetti" kodlu bir bildirim sisteminin, 3 faz boyunca çeşitli yazılım tasarım örüntüleri (Design Patterns) kullanılarak nasıl esnek, genişletilebilir ve Açık/Kapalı Prensibine (OCP) uygun profesyonel bir mimariye evrildiğini göstermektedir.

## Kullanılan Tasarım Örüntüleri
Sistem evrimleşirken aşağıdaki örüntüler entegre edilmiştir:

1. **Factory Method (Creational):** Bildirim nesnelerinin (Email, SMS, Push) üretilme sorumluluğunu tek bir merkeze toplayarak istemci kodunu rahatlattı.
2. **Decorator (Structural):** Mevcut bildirim sınıflarını değiştirmeden (OCP'ye uygun olarak) sistem loglama özelliğini çalışma zamanında koda entegre etti.
3. **Facade (Structural):** Nesne yaratma ve loglama sarmalama işlemlerinin karmaşıklığını gizleyerek ana sisteme temiz bir arayüz sundu.
4. **Strategy (Behavioral):** Mesajların formatlanma (Standart, Acil vb.) davranışını dinamik hale getirdi ve `if-else` bağımlılığını ortadan kaldırdı.
5. **Observer (Behavioral):** Merkezi bir yayıncı üzerinden, sisteme kayıtlı olan birden fazla aboneye (kullanıcıya) aynı anda bildirim iletilmesini sağladı.

## Mimari Diyagram
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
    
    NotificationFactory ..> Notification
    Notification <|.. NotificationDecorator
    NotificationDecorator o-- Notification
    NotificationDecorator <|-- LoggingDecorator
    
    NotificationCenter o-- Subscriber
    NotificationCenter o-- MessageStrategy
    NotificationCenter *-- NotificationFacade
    
    MessageStrategy <|.. StandardMessageStrategy
    MessageStrategy <|.. UrgentMessageStrategy
    Subscriber <|.. UserSubscriber
    NotificationFacade --> NotificationFactory
