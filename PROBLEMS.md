# Faz 0: Başlangıç Kodunun Analizi

Bu projede "Konu A - Bildirim Sistemi" seçilmiştir. Mevcut `NotificationManager.java` kodunda tespit edilen temel tasarım sorunları şunlardır:

## Tespit Ettiğim 5 Temel Tasarım Sorunu

1. **Single Responsibility Principle (SRP) İhlali:** `NotificationManager` sınıfı çok fazla sorumluluk üstlenmiş. Hem hangi tipte gönderim yapılacağına karar veriyor, hem sunucu bağlantı süreçlerini yürütüyor, hem de mesajı iletiyor.
2. **Open/Closed Principle (OCP) İhlali:** Sisteme örneğin "WhatsApp" gibi yeni bir bildirim kanalı eklemek istediğimizde, mevcut kodu açıp yeni bir `else if` bloğu yazmak zorundayız. Mevcut kod gelişime açık, değişime kapalı kuralını çiğniyor.
3. **Yüksek Bağımlılık (Tight Coupling):** Bütün bildirim alt yapı mantıkları (SMTP, SMS Gateway) tek bir metodun içine gömülü (hardcoded) durumda.
4. **Kod Tekrarı Potansiyeli:** Tüm bildirimlere örneğin loglama veya güvenlik kontrolü eklenmek istense, her `if-else` bloğuna aynı kodun tekrar tekrar yazılması gerekecek.
5. **Test Edilebilirlik Sorunu:** Kodu birim testine (unit test) sokmak çok zor çünkü tüm kanallar ve altyapılar birbirine dolanmış durumda. Bağımsız test yapılamaz.

---

## AI Değerlendirmesi ve Karşılaştırma

**Kullanılan AI Aracı:** Gemini
**Prompt:** "Bu kodda hangi tasarım sorunlarını görüyorsun? Hangi tasarım örüntüleri bu sorunları çözebilir? Her sorun için kısa bir açıklama yaz."

**AI'ın Yanıtı (Özet):**
*   *Sorumlulukların Birikmesi (SRP İhlali):* Kod nesne yönelimli değil, prosedürel yazılmış. Tüm mantık tek bir fonksiyonda toplanmış.
*   *Genişletilebilirlik Sorunu (OCP İhlali):* Yeni bildirim tipleri kodun değiştirilmesini gerektirir.
*   *Önerilen Tasarım Örüntüleri:* 
    *   **Factory Method:** Nesne yaratma süreçlerini soyutlamak için.
    *   **Strategy Pattern:** Farklı gönderim mantıklarını (SMS, Email) ayrı sınıflara bölüp çalışma zamanında değiştirebilmek için.
    *   **Decorator Pattern:** Bildirimlerin üzerine sonradan özellik (loglama vs.) eklemek için.

**Karşılaştırma (Benim Gördüklerim vs. AI'ın Gördükleri):**
AI'ın tespitleri benim tespit ettiğim SRP ve OCP ihlalleriyle birebir örtüşüyor. Ben ek olarak test edilebilirlik ve bağımlılık sorunlarına odaklanmıştım. AI ise sorun tespitinin yanı sıra çözüm olarak Factory, Strategy ve Decorator örüntülerini vurguladı. Sonraki fazlarda bu önerileri dikkate alacağım.
