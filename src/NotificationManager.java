public interface Notification {
    void send(String message);
}
public class EmailNotification implements Notification {
    public void send(String message) {
        System.out.println("E-posta gönderiliyor: " + message);
    }
}

public class SMSNotification implements Notification {
    public void send(String message) {
        System.out.println("SMS gönderiliyor: " + message);
    }
}

public class PushNotification implements Notification {
    public void send(String message) {
        System.out.println("Push bildirimi gönderiliyor: " + message);
    }
}
public class NotificationFactory {
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

public class NotificationManager {
    public static void main(String[] args) {
        NotificationFactory factory = new NotificationFactory();
        
        Notification email = factory.createNotification("email");
        email.send("email mesaj");

        Notification sms = factory.createNotification("sms");
        sms.send("SMS mesaj");
    }
}
