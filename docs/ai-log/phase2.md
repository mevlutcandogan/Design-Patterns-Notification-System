# Faz 2: Yapay Zeka ile Eşli Çalışma Günlüğü (Decorator Pattern)

## 1. Kullanılan Promptlar
- "Elimdeki bildirim sınıflarını (Email, SMS) değiştirmeden onlara loglama özelliğini nasıl ekleyebilirim?"
- "Bana Decorator örüntüsüyle bir örnek yazar mısın? İçinde sarmalayıcı (wrapper) mantığı olsun."
- "Neden doğrudan kalıtım (inheritance) kullanarak `LogluEmailNotification` veya `LogluSMSNotification` diye yeni alt sınıflar üretmiyoruz da, Decorator gibi araya kılıf giren bir yapı kullanıyoruz?"

## 2. Yapay Zekanın Katkısı ve Kod Üzerindeki Değişikliklerim
Yapay zeka, OCP (Açık/Kapalı Prensibi) ihlalini önlemek için bana Decorator örüntüsünü önerdi ve temel `NotificationDecorator` soyut sınıfının iskeletini oluşturdu. Kodu projeme entegre ederken isimlendirmeleri Faz 1'deki `Notification` arayüzüne uyumlu hale getirdim ve loglama sistemimi projeme özel `[SİSTEM LOGU]` ibareleriyle özelleştirdim. 

## 3. Ne Öğrendim? (Refleksiyon)
Bu aşamada en net kavradığım konu "Sınıf Patlaması" (Class Explosion) sorunu oldu. Yapay zeka ile tartışmamızda anladım ki; eğer özellikleri kalıtım (inheritance) ile ekleseydik, her yeni özellik ve bildirim tipi kombinasyonu için (örneğin LogluŞifreliEmail, LogluSMS vb.) onlarca yeni alt sınıf yazmamız gerekecekti. Decorator örüntüsünün, nesneleri çalışma zamanında (runtime) birbirinin içine koyarak sarmalaması sayesinde, "Kalıtım yerine Kompozisyon kullan" (Favor composition over inheritance) prensibinin gerçek hayatta ne kadar hayat kurtarıcı olduğunu pratik etmiş oldum.
