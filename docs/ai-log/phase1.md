# Faz 1: Yapay Zeka ile Eşli Çalışma Günlüğü (Factory Method)

## 1. Kullanılan Promptlar
- "Spagettiye dönmüş if-else dolu bir bildirim sistemi kodum var. Bunu Creational (Yaratımsal) bir örüntü ile nasıl daha esnek hale getirebilirim?"
- "Bana bunu Factory Method ile yazar mısın? Ancak çok karmaşık kütüphanelere girmeden basit bir switch-case mantığı olsun."
- "Factory sınıfında kullandığımız bu switch-case yapısı, yarın WhatsApp gibi yeni bir bildirim tipi eklemek istediğimizde Factory sınıfını değiştirmemizi gerektiriyor. Bu durum OCP'yi (Açık/Kapalı Prensibini) ihlal etmiyor mu? Bunu aşmanın yolları nelerdir?"

## 2. Yapay Zekanın Katkısı ve Kod Üzerindeki Değişikliklerim
Yapay zeka nesne yaratma işini merkezileştirmek için bana Interface ve Factory sınıfı içeren bir yapı sundu. Kodun fazla karmaşık olmaması için `isEmpty()` gibi ekstra string kontrollerini koddan çıkardım ve sadece temel bir `null` kontrolü bıraktım. Ayrıca isimlendirme standartlarına uymak adına Türkçe terimler yerine yazılım dünyasında daha profesyonel duran `Notification`, `NotificationFactory` gibi İngilizce isimlendirmeleri tercih ettim.

## 3. Ne Öğrendim? (Refleksiyon)
En çok aydınlandığım nokta, "Simple Factory" yapısındaki `switch-case` bloklarının aslında Açık/Kapalı Prensibini (OCP) katı bir şekilde sağlamadığı oldu. Yapay zeka ile yaptığım tartışmada, yeni bir tip eklendiğinde fabrika sınıfının mecburen değiştirileceğini fark ettim. Ancak yapay zeka bana, bu sorunu tamamen çözmek için Java Reflection API kullanılabileceğini veya metodun alt sınıflara ezdirilebileceğini (override), fakat şu anki sistemin boyutu için bizim kurduğumuz yapının okunabilirlik açısından en ideal "trade-off" (ödünleşim) olduğunu anlattı. Tasarım örüntülerinde her zaman %100 teorik kusursuzluk yerine projenin ihtiyacına göre esneklik sağlanması gerektiğini pratik etmiş oldum.
