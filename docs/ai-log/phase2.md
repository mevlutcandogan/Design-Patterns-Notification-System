# Faz 2: Yapay Zeka ile Eşli Çalışma Günlüğü (Structural Örüntüler)

## 1. Kullanılan Promptlar
- "Bildirim sınıflarıma (Email, SMS) mevcut kodu değiştirmeden loglama özelliği eklemek istiyorum. Decorator mantığı sence uygun mu?"
- "Main metodumdaki nesne üretim (Factory + Decorator) kalabalığını gizlemek için Adapter mi kullanmalıyım yoksa Facade mı? İkisinin benim kodumdaki farkı ne olur?"

## 2. Yapay Zekanın Yönlendirmesi ve Karar Sürecim (Kritik Bölüm)
Hayır, yapay zekanın önerilerinde bariz bir yanlış veya eksik bulmadım. AI, Decorator konusunda OCP'yi koruyacağı için doğru bir yönlendirme yaptı ve kalıtım yerine kompozisyon kullanmamı destekledi.

Adapter ve Facade arasındaki farkı sorduğumda da teorik olarak doğru bilgiler verdi. AI bana doğrudan "kesinlikle şunu kullan" diye bir dayatmada bulunmadı, sadece iki örüntünün de niyetini açıkladı. Ben de kendi kodumu incelediğimde ortada bir "arayüz uyumsuzluğu" olmadığını (Adapter), asıl amacımın sadece karmaşık alt sistemleri tek bir basit metoda indirmek (Facade) olduğunu fark ettim. Kararı kendi inisiyatifimle verip Facade'ı uyguladım. Yani AI hata yapmadı, teorik destek sağladı, mimari kararı ben verdim.

## 3. Ne Öğrendim? (Refleksiyon)
Decorator sayesinde "Class Explosion" (sınıf patlaması) yaşamadan koda yeni özellik eklemeyi öğrendim. Facade'ı da uygulayınca, ana test sınıfımın arka plandaki karmaşık işleri bilmesine gerek kalmadığını gördüm. AI'ı sadece bir dokümantasyon gibi kullanıp kendi mimarimi kurmayı pratik etmiş oldum.
