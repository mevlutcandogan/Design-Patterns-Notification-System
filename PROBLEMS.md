# Faz 0: Başlangıç Kodunun Analizi

Bu projede "Konu A - Bildirim Sistemi" seçildi. Kodu hiçbir mimari düşünmeden yazdım (`NotificationManager.java`) ve beklediğim gibi tam bir spagetti koda dönüştü. Tespit ettiğim temel tasarım sorunları şunlar:

## Gördüğüm 5 Temel Tasarım Sorunu

1. **SRP (Tek Sorumluluk) İhlali:** Sınıf resmen her işi yapıyor. Hem e-posta sunucusuna bağlanıyor, hem SMS gateway'e istek atıyor, hem de karar mekanizmasını yönetiyor. İleride bu dosya binlerce satır olur.
2. **OCP (Açık/Kapalı) İhlali:** Sisteme yarın öbür gün "WhatsApp" veya "Telegram" eklemek istesem, mecburen kodu açıp yeni bir `else if` bloğu daha yazmam gerekecek. Kod değişime açık, bu da hata yapma riskini artırıyor.
3. **Tight Coupling (Sıkı Bağımlılık):** Bütün SMTP veya SMS API bağlantı mantıkları tek bir metodun içine gömülmüş (hardcoded). Herhangi bir servisin altyapısı değişse komple bu sınıfı güncellemek gerekecek.
4. **Kod Tekrarı (Duplication) Riski:** Tüm bildirimlere örneğin bir loglama veya hata yakalama (try-catch) eklemek istesem, gidip her if-else bloğunun içine aynı kodu kopyala-yapıştır yapmam gerekecek.
5. **Test Etmesi Çok Zor:** Bu koda Unit Test yazmak tam bir işkence olur. Sadece SMS kısmını test etmek istesem bile bütün bağımlılıklarla beraber ana sınıfı ayağa kaldırmam lazım. Parça parça test edilemiyor.

---

## AI Değerlendirmesi ve Karşılaştırma

**Kullanılan AI Aracı:** Gemini
**Prompt:** "Bu kodda hangi tasarım sorunlarını görüyorsun? Hangi tasarım örüntüleri bu sorunları çözebilir? Her sorun için kısa bir açıklama yaz."

**AI'ın Resmi Yanıtı (Özetlenmiştir):**
> **Tasarım Sorunları:**
> * **SRP (Single Responsibility Principle) İhlali:** Mevcut sınıf, "God Object" (Her Şeyi Bilen Nesne) anti-örüntüsüne (anti-pattern) doğru evrilmektedir. Karar mekanizması (hangi tipin seçileceği), yönlendirme mantığı ve altyapı bağlantısı gibi birbirinden bağımsız sorumlulukları tek bir gövdede barındırmaktadır.
> * **OCP (Open/Closed Principle) İhlali:** Sisteme yeni bir bildirim kanalı entegre edilmek istendiğinde, mevcut iş mantığının (if-else zincirleri) doğrudan modifiye edilmesi gerekmektedir. Bu durum, sistemin genişlemeye kapalı olduğunu gösterir ve yeni eklentilerin sistemde regresyon (regression) hataları yaratmasına zemin hazırlar.
> * **Yüksek Bağımlılık ve Soyutlama Eksikliği:** Somut (concrete) implementasyonlar ana iş mantığına sıkı sıkıya bağlıdır. İstemci (client) kod, soyut arayüzler (interfaces) yerine doğrudan alt seviye detaylarla çalışmaktadır.
> 
> **Önerilen Tasarım Örüntüleri:**
> * **Factory Method:** Nesne yaratım (creational) süreçlerini merkezileştirmek, karmaşık if-else bloklarını ortadan kaldırmak ve istemciyi somut sınıflardan soyutlamak için en uygun başlangıç adımıdır.
> * **Strategy Pattern:** Çalışma zamanında (runtime) farklı bildirim algoritmalarının dinamik olarak değiştirilebilmesini sağlamak, böylece koşullu ifadeleri polimorfik davranışlarla değiştirmek için önerilir.
> * **Decorator Pattern:** Mevcut bildirim sınıflarının ana yapısını değiştirmeden; loglama, metin şifreleme veya yeniden deneme (retry) mekanizması gibi ek davranışların nesnelere dinamik olarak sarılması (wrapping) amacıyla kullanılabilir.

**Karşılaştırma (Benim Gördüklerim vs. AI'ın Gördükleri):**
Açıkçası AI, benim "if-else yığını" ve "hardcoded" diyerek yakındığım yerleri çok daha akademik bir dille "God Object anti-örüntüsü" ve "Soyutlama Eksikliği" olarak tanımladı. İkimiz de sistemin yeni eklentilere kapalı (OCP) olduğu konusunda hemfikiriz. Fakat benim asıl korkum bu koda Unit Test yazmanın veya her yere try-catch eklemenin yaratacağı amelelik ve "kod hamallığı" idi; AI ise test edilebilirlikten ziyade projenin genel bakım maliyetine ve regresyon hatalarına odaklandı. Sonuç olarak önerdiği reçete son derece mantıklı. İlk adımda Factory Method ile o spagetti if-else zincirini parçalayacağım, daha sonra ekstra özellikler (loglama vb.) eklemek için Decorator (sarmalama) fikrini kesinlikle deneyeceğim.
