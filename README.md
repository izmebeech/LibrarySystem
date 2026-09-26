# Bibliotekshanteraren
Detta program låter användaren lägga till böcker i ett bibliotekssystem, via titel, författare och ett ISBN-nummer.
Den har en funktion för boklån, återlämning samt medlemsregistrering. Användaren kan även se statistik om vilken medlem som 
för tillfället har lånat flest böcker - om sådan finns. 
Användaren navigerar mellan de olika valen i en interaktiv huvudmeny.
## Beskrivning
LibrarySystem startar programmet och skapar 5 initiala böcker som finns i biblioteket och hanterar själva 
menyn, både vad användaren ser och vad användaren ger för input.

Library innehåller själva logiken bakom biblioteket, lagrar böcker och medlemmar i arrayer, registrerar nya medlemmar,
lägger till nya böcker, hanterar utlåning och återlämning av böcker, sökfunktionen, sortering och statistik. Den
expanderar även arrayerna när de blir fulla.

Programmet har även felhantering för ogiltig input, så att programmet inte kraschar om användaren till exempel anger
ett medlems-ID som inte är ett nummer, försöker låna en bok som inte finns eller anger ett ISBN som innehåller annat 
än siffror.

Book är en record som lagrar information om varje bok via titel, författare och ISBN. 

Member är en vanlig klass som representerar bibliotekets medlemmar och håller koll på deras medlems-ID, namn och
aktiva lån.


## Design och struktur
### Book
Här använde jag record eftersom att vi endast kommer behöva 3 värden när vi lagrar en bok i systemet (titel, 
författare och ISBN). Dessa värden behöver inte ändras efter att boken har skapats.
Record är därför ett enkelt sätt att representera den här typen av data. 

### Member
Varje medlem har ett unikt ID, ett namn och activeLoans. Eftersom att activeLoans måste kunna förändras när en medlem
lånar eller återlämnar en bok, så behöver det vara en klass som har metoder/getters/setters - så att den kan modifiera
data. Fälten måste vara privata så att inte resten av programmet kan ändra dem direkt. Det är även i denna klass som jag
kollar ifall en medlem tillåts att låna fler böcker.

### Library

Här lagras själva bulken av kod för programmet, själva logiken för biblioteket. Library innehåller bibliotekets 
operativa funktioner. Här lagras arrayerna Book[], Member[] och borrowedBy[]. bookCount och memberCount håller reda på 
hur många objekt som faktiskt finns lagrade. borrowedBy matchar varje boks position i books och håller reda på vem som 
har lånat den. Library hanterar också att lägga till böcker och registrera medlemmar, låna och lämna tillbaka, söka, 
sortera, visa statistik och expandera arrayerna.

Detta gör att LibrarySystem främst kan fokusera på användargränssnittet, menyn och input, medan Library hanterar data 
och programlogik. Jag ville att de skulle finnas på samma plats, så därav blev det en enda monsterklass. Jag försökte 
ändå att göra det så lättläsligt som jag kan (om det faktiskt är det är upp till bevis). Jag lämnar löpande förklarande 
kommentarer i min kod, mest för att jag själv ska komma ihåg vad det är jag har gjort, men också för att förenkla 
läsandet för någon annan.

### LibrarySystem

LibrarySystem är min main, som främst ansvarar för användarinteraktionen. 
Main skapar först vårt bibliotek och lägger in 5 böcker, jag valde detta eftersom jag ville att något skulle finnas i
i biblioteket ifall användaren skulle trycka på menyval 6 (visa alla böcker), innan de lagt in någon bok själv eller 
om de vill låna någon av de befintliga böckerna. Jag hade även initialt en liten medlemslista med fiktiva medlemmar 
också, men jag upplevde att de inte riktigt fyllde någon funktion eftersom vi inte aktivt visar en medlemslista 
någonstans - så jag tog bort dem.

Själva menyn är i en loop och user input bestämmer vilket case i switch-satsen som ska köras. Först skrev jag 
funktionerna för varje case direkt i caset men det blev väldigt bulkigt och jag valde senare att extracta varje case 
funktion till varsin separata metod (addBook(), registerMember() osv), så att det såg snyggare ut och mer 
lättläsligt/nedbrutet. Metoderna hanterar användarens input och kallar på respektive metoder från Library. Programmet
fortsätter köra tills användaren väljer e (exit). Jag la även till validering och felhantering ifall användaren anger
ogiltig input.

## Arrays och dynamisk kapacitet
Jag använder Book[], Member[] och borrowedBy[]. Jag börjar med arrayer som har 5 platser, för jag valde ett fast antal
i början som sedan ska expandera. bookCount och memberCount håller reda på hur många platser som faktiskt används. 
När en array blir full skapas en ny array med dubbelt så stor kapacitet. Programmet kopierar sedan innehållet från den 
gamla arrayen till den nya med en for-loop och ersätter därefter den gamla arrayen med den nya, större arrayen. 
När books expanderas behöver även borrowedBy expanderas samtidigt eftersom dess index motsvarar indexen i books. 
På så sätt kan programmet fortsätta lägga till böcker och medlemmar utan att den befintliga datan förloras.

## Sökning
bookSearch() går igenom books från början till slut med en for-loop, där både titel och författare jämförs med 
användarens sökterm. Eftersom den skulle vara case-insensitive ville jag omvandla all input till lowercase, via 
toLowerCase(). Sen ville jag även göra att användaren skulle få upp resultat även om de bara skrev en del av titeln 
eller författaren, därav la jag in contains(). Om en bok matchar skrivs den ut men den fortsätter genom hela arrayen 
istället för att sluta efter första träffen, eftersom flera böcker skulle kunna matcha samma sökning.

## Sortering
Programmet skapar en tillfällig kopia av böckerna Den sorterar sedan böckerna i alfabetisk ordning efter titel genom att 
jämföra två böcker åt gången och byta plats på dem om de ligger i fel ordning. Sen flyttas samtidigt borrowedBy, 
så att rätt låntagare fortsätter att höra ihop med rätt bok. 
Jag använde metoden Bubble Sort, ingen speciell anledning förutom att den stod som första exempel i uppgiftsbeskrivningen.


## Utlåningsstatistik
memberWithMostLoans() går igenom alla medlemmar med en for-loop. Den håller reda på vilken medlem som hittills har flest
aktiva lån. Om den hittar en medlem med fler lån uppdateras den. Medlemmar med 0 lån ignoreras, eftersom jag inte ville
att det skulle bli en bug där även medlemmar utan lån visas. Om ingen har några aktiva lån för tillfället, så returneras
null och LibrarySystem meddelar för användaren att det inte finns några aktiva lån.

## Felhantering och validering
Felhantering hanterar följande: 
ISBN kontrolleras så att det bara innehåller siffror.
Dubbletter av ISBN stoppas.
Programmet kontrollerar att en bok faktiskt finns innan lån/återlämning.
Programmet kontrollerar att en bok inte redan är utlånad.
Medlems-ID kontrolleras så att det är ett nummer med try/catch.
Programmet kontrollerar att medlemmen faktiskt finns.
Felaktiga menyval hanteras utan att programmet kraschar.
Återlämning kontrolleras så att rätt medlem lämnar tillbaka boken.
Medlem kan inte låna fler än 5 böcker.

## Reflektion
Jag inser nu i efterhand att jag redan reflekterat ganska mycket i min README, så jag ska inte återupprepa mig, utan
hoppas bara att du la märke till det. Något som jag däremot tänkt på är om jag skulle ha delat upp library mer, eller 
sorterat det på ett mer läsvänligt sätt. Jag hade kanske lagt lite mer krut på estetiken av menyn och mina printlines.
Gjort det lite mer roligt, utseendemässigt helt enkelt. Sen hade jag nog gjort menyn och all user interface på engelska
istället, bara för hålla kodandet enhetligt, ibland glömde jag att jag använde svenska.

Jag kanske direkt hade skapat metoder för mina cases, men samtidigt kändes det lärorikt att först skriva dem för att 
sedan låta intellij extracta dem till en metod.

Jag fick även där passa på att "korrekturläsa", så att ingen logik förändrades när jag gjorde extract. 
Uppgiften blev väldigt intensiv för jag stötte löpande på problem med gitHub och intellij, så nästa gång hade jag
nog trippelkollat så att allt verkligen funkar innan jag börjar. 

Jag har använt AI som ett verktyg när jag själv behövt hjälp att förstå vilka vägar jag kan ta, men jag har använt den
som en "lärare" - alltså skrivit tydliga promptar som ber den att INTE bara ge mig svaret utan guida mig så jag hittar 
svaret själv.


### Collections
Om jag hade fått använda arrayList<Book> istället för Book[], så hade jag inte ens behövt ha en bookCount, eller en
expandBooks() metod, eftersom arraylist redan håller koll på hur många objekt som finns samt expanderas automatiskt
vid behov. Det hade blivit enklare att bara lägga till en bok, utan att behöva skriva en massa extra steg av kod.
Likaså om Member hade fått vara en arraylist, så hade jag inte behövt en memberCount eller expandMembers().

Om jag hade fått använda List.sort eller Collections.sort, så hade jag inte behövt skapa en temporär sort och
sortedBorrowedBy bara för att sortera. Dessa sorteringsmetoder hade skött det automatiskt -> mindre kod.

Eventuellt kunde jag ha använt en HashMap till att förenkla relationen mellan en bok och den som har lånat den. Att ett
ISBN hade kunnat knytas direkt till den medlem som lånat den via en HashMap<String,Member>


