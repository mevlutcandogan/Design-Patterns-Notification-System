# Faz 3: Yapay Zeka ile Eşli Çalışma Günlüğü (Behavioral Örüntüler)

## 1. Ne Tartıştık?
Bu fazda sistemin davranışını dinamik hale getirmeyi tartıştık. AI ile yaptığımız pair programming oturumunda, sisteme yeni mesaj tipleri (Acil, Standart vb.) eklendiğinde mevcut kodun bozulmaması için (OCP) Strategy örüntüsünü kurduk. Ardından abonelere toplu mesaj atabilmek için Observer örüntüsünü entegre ettik.

## 2. Hocanın Sorusu: AI olmadan bu faz ne kadar sürerdi? AI sizi nerede yanılttı?
Eğer yapay zeka olmasaydı, Strategy ve Observer örüntülerini aynı projede birbirini ezmeden entegre etmek ve OCP kuralını ihlal etmeden kurgulamak benim için muhtemelen 3-4 saat fazla mesai demekti. AI bana bu iki örüntünün nasıl haberleşeceği konusunda çok hızlı bir taslak sundu.

Ancak AI beni şurada yanılttı: Observer örüntüsünü kurarken, abonelere mesaj gönderme döngüsünün içine doğrudan Factory ve Decorator çağrılarını koymaya çalıştı. Bu durum "Single Responsibility" (Tek Sorumluluk) prensibini fena halde bozuyordu. AI'ın bu hatasını fark ettim ve Faz 2'de yazdığım `NotificationFacade` sınıfını kullanarak döngünün içini temizledim. AI hızlı kod yazıyor ama mimari katmanları birbirine karıştırmaya  çok meyilli.
