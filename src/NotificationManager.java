public interface Notification {
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
            case "email":
                return new EmailNotification();
            case "sms":
                return new SMSNotification();
            case "push":
                return new PushNotification();
            default:
                throw new IllegalArgumentException("Bilinmeyen bildirim tipi: " + type);
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
        System.out.println("[SİSTEM LOGU] Bildirim işlemi başlatıldı...");
        super.send(message);
        System.out.println("[SİSTEM LOGU] Bildirim başarıyla tamamlandı.\n");
    }
}

public class NotificationManager {
    public static void main(String[] args) {
        NotificationFactory factory = new NotificationFactory();
        
        System.out.println("--- NORMAL BİLDİRİM ---");
        Notification email = factory.createNotification("email");
        email.send("email mesaj");
        System.out.println("--- LOGLANMIŞ BİLDİRİM ---");
        Notification sms = factory.createNotification("sms");
        Notification logluSms = new LoggingDecorator(sms); 
        logluSms.send("sms mesaj");
    }
}
