public class NotificationManager {
    public void sendNotification(String type, String message, String recipient) {
        if (type.equalsIgnoreCase("EMAIL")) {
            System.out.println("SMTP sunucusuna bağlanılıyor...");
            System.out.println("E-posta gönderiliyor -> Alıcı: " + recipient + " | Mesaj: " + message);
        } else if (type.equalsIgnoreCase("SMS")) {
            System.out.println("Operatör SMS Gateway'ine bağlanılıyor...");
            System.out.println("SMS gönderiliyor -> Alıcı: " + recipient + " | Mesaj: " + message);
        } else if (type.equalsIgnoreCase("PUSH")) {
            System.out.println("Apple/Google bildirim sunucularına bağlanılıyor...");
            System.out.println("Push Notification gönderiliyor -> Cihaz: " + recipient + " | Mesaj: " + message);
        } else {
            System.out.println("HATA: Bilinmeyen bildirim tipi!");
        }
    }
}
