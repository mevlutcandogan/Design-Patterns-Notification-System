public class NotificationManager {
    public void sendNotification(String type, String message, String recipient) {
        if (type.equalsIgnoreCase("EMAIL")) {
            System.out.println("Mail sunucusuna baglaniliyor...");
            System.out.println("Mail gonderildi - Kime: " + recipient + " Icerik: " + message);
        } else if (type.equalsIgnoreCase("SMS")) {
            System.out.println("SMS servisine istek atiliyor...");
            System.out.println("SMS iletildi -> Tel: " + recipient + " Mesaj: " + message);
        } else if (type.equalsIgnoreCase("PUSH")) {
            System.out.println("Push sunucusuna baglanildi...");
            System.out.println("Telefona bildirim dustu -> " + recipient + " : " + message);
        } else {
            System.out.println("HATA: Boyle bir bildirim tipi yok!");
        }
    }
}
