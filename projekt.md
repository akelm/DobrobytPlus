#Aplikacja do planowania budżetu
## Wymagania Zembrowskiego
   Strona z formularzem do rejestracji i/lub zalogowania.

   Aplikacja powinna posiadać 3 typy kont: kontoSingiel, gdzie pojedyncza osoba prowadzi swoje wydatki oraz kontoPara, gdzie dwoje ludzi prowadzi swoje wydatki oraz kontoRodzina+, gdzie konto posiadać będzie oboje rodziców + dzieci. Rozważ sposób na łączenie ludzi w pary oraz rodziny. Konto typu rodzina+ powinna automatycznie przyznawać 500 zł na każde dziecko w planowaniu miesięcznego budżetu.
   
W aplikacji możemy zdefiniować:
   -miesięczne stałe przychody
   -miesięczne stałe wydatki
   -jednorazowe przychody
   -jednorazowe wydatki
   Jako okres rozliczeniowy przyjmujemy 1 miesiąc kalendarzowy.
   Aplikacja powinna nam wyświetlać ile w danym miesiącu mamy nadwyżki pieniędzy lub debetu. Przy wprowadzeniu danych z dłuższego okresu niż 1 miesiąc np. w ciągu kwartału powinien być dostępny widok, w którym widzimy saldo z każdego miesiąca.

## Projekt

Typy kont użytkowników:
- dziecko
- dorosły
  
  (weryfikowane datą urodzenia)


Typy rachunków:
- Indywidualny
- Para
- Rodzina+

## Użytkownicy

Dziecko:
- może mieć max 1 rachunek indywidualny
- może należeć do konta Rodzina+, gdzie ma ograniczone uprawnienia

Dorosły:
- może mieć max 1 rachunek indywidualny
- może należeć albo do rachunku Para albo do Rodzina+