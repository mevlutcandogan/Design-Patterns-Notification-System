import java.util.ArrayList;
import java.util.List;

interface Notification { void send(String message); }
class EmailNotification implements Notification { public void send(String message) { System.out.println("E-posta gönderiliyor: " + message); } }
class SMSNotification implements Notification { public void send(String message) { System.out.println("SMS gönderiliyor: " + message); } }
class PushNotification implements Notification { public void send(String message) { System.out.println("Push bildirimi gönderiliyor: " + message); } }

class NotificationFactory {
    public Notification createNotification(String type) {
        if (type == null) return null;
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
    public NotificationDecorator(Notification wrappedNotification) { this.wrappedNotification = wrappedNotification; }
    public void send(String message) { wrappedNotification.send(message); }
}

class LoggingDecorator extends NotificationDecorator {
    public LoggingDecorator(Notification wrappedNotification) { super(wrappedNotification); }
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

interface MessageStrategy {
    String formatMessage(String message);
}
class StandardMessageStrategy implements MessageStrategy {
    public String formatMessage(String message) { return "[BİLGİLENDİRME] " + message; }
}
class UrgentMessageStrategy implements MessageStrategy {
    public String formatMessage(String message) { return "[!!! ACİL !!!] " + message.toUpperCase(); }
}

interface Subscriber {
    void update(String message);
}
class UserSubscriber implements Subscriber {
    private String name;
    public UserSubscriber(String name) { this.name = name; }
    public void update(String message) { System.out.println(name + " adlı kullanıcıya ulaştı."); }
}

class NotificationCenter {
    private List<Subscriber> subscribers = new ArrayList<>();
    private MessageStrategy strategy;
    private NotificationFacade facade;

    public NotificationCenter(MessageStrategy strategy) {
        this.strategy = strategy;
        this.facade = new NotificationFacade(); 
    }

    public void setStrategy(MessageStrategy strategy) { this.strategy = strategy; }
    public void subscribe(Subscriber sub) { subscribers.add(sub); }

    public void broadcast(String rawMessage, String type) {
        String finalMessage = strategy.formatMessage(rawMessage);
        System.out.println("\n--- Toplu Bildirim Yayını ---");
        facade.sendLoggedNotification(type, finalMessage);
        for (Subscriber sub : subscribers) { sub.update(finalMessage); }
    }
}

public class NotificationManager {
    public static void main(String[] args) {
        NotificationCenter center = new NotificationCenter(new StandardMessageStrategy());
        
        center.subscribe(new UserSubscriber("Yahya"));
        center.subscribe(new UserSubscriber("Mevlüt"));

        center.broadcast("Sistem bakımı bu gece yapılacaktır.", "sms");

        center.setStrategy(new UrgentMessageStrategy());
        center.broadcast("Sunucu hatası tespit edildi, lütfen yedek alın!", "push");
    }
}
