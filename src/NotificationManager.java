interface Notification {
    void send(String message);
}

class EmailNotification implements Notification {
    public void send(String message) {
        System.out.println("E-posta gönderiliyor: " + message);
    }
}

class SMSNotification implements Notification {
    public void send(String message) {
        System.out.println("SMS gönderiliyor: " + message);
    }
}

class PushNotification implements Notification {
    public void send(String message) {
        System.out.println("Push bildirimi gönderiliyor: " + message);
    }
}

class NotificationFactory {
    public Notification createNotification(String type) {
        if (type == null) {
            return null;
        }
        switch (type.toLowerCase()) {
            case "email": return new EmailNotification();
            case "sms": return new SMSNotification();
            case "push": return new PushNotification();
            default: throw new IllegalArgumentException("Bilinmeyen bildirim tipi: " + type);
        }
    }
}
abstract class NotificationDecorator implements Notification {
    protected Notification wrappedNotification;

    public NotificationDecorator(Notification wrappedNotification) {
        this.wrappedNotification = wrappedNotification;
    }

    public void send(String message) {
        wrappedNotification.send(message);
    }
}

class LoggingDecorator extends NotificationDecorator {
    public LoggingDecorator(Notification wrappedNotification) {
        super(wrappedNotification);
    }

    public void send(String message) {
        System.out.println("[SİSTEM LOGU] Bildirim süreci başlatıldı...");
        super.send(message); 
    }
}
class NotificationFacade {
    private NotificationFactory factory = new NotificationFactory();

    public void sendLoggedNotification(String type, String message) {
        
        Notification notif = factory.createNotification(type);
        if (notif != null) {
            Notification loggedNotif = new LoggingDecorator(notif);
            loggedNotif.send(message);
        }
    }
}
public class NotificationManager {
    public static void main(String[] args) {
        NotificationFacade facade = new NotificationFacade();

        System.out.println("--- Test 1: E-posta Gönderimi ---");
        facade.sendLoggedNotification("email", "Sistem başarıyla güncellendi.");

        System.out.println("\n--- Test 2: SMS Gönderimi ---");
        facade.sendLoggedNotification("sms", "Bakiye yetersiz.");
    }
}
