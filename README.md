## DobrobytPlus
Projekt zaliczeniowy z przedmiotu <i>Programowanie w Javie</i> 2020/2021 na WMP.SNŚ UKSW.

<i>DobrobytPlus</i> jest to aplikacja sieciowa umożliwiająca planowanie budżetu jej użytkownikom przy wykorzystaniu oferowanych przez nią funkcjonalności takich jak zarządzanie dochodami oraz wydatkami i ich szczegółowa analiza.

## Autorzy
Autorami projektu są Anna Kelm, Karol Chyliński i Michał Sosnowski.

## Opis działania
Skorzystanie z aplikacji wymaga utworzenia konta przy pomocy formularza rejestracyjnego bądź też zalogowania się przy wykorzystaniu już istniejącego konta w systemie.

Użytkownik podczas rejestracji może utworzyć 2 typy kont:
- konto dla dorosłego,
- konto dla dziecka,

które weryfikowane są datą urodzenia.

Możliwe jest utworzenie 3 typów rachunków:
- Indywidualny - rachunek dla osób indywidualnych, które chcą prowadzić swoje wydatki,
- Para - rachunek dla par, które chcą wspólnie prowadzić swoje wydatki,
- Rodzina+ - rachunek dla rodzin z dziećmi, gdzie rodzice prowadzą wydatki.

Na każde dziecko na rachunku Rodzina+ przyznawane jest automatycznie świadczenie 500+.

Każdy użytkownik może mieć maksymalnie utworzony 1 rachunek Indywidualny. Użytkownik dorosły może należeć także do rachunku Para albo Rodzina+, a użytkownik dziecko do rachunku Rodzina+, gdzie ma ograniczone uprawnienia.

Aplikacja pozwala na zdefiniowanie zarówno miesięcznych stałych, jak i jednorazowych przychodów oraz wydatków.

Okres rozliczeniowy stanowi 1 miesiąc kalendarzowy. Aplikacja wyświetla także nadwyżkę pieniędzy lub debet w danym miesiącu. Ponadto udostępnia widok, w którym prezentowane jest saldo z każdego poprzedniego miesiąca.

## Użyte technologie
- [JDK](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html)
- [Maven](https://maven.apache.org)
- [Spring (Boot, MVC, Data JPA, Security)](https://spring.io)
- [Hibernate](https://hibernate.org)
- [MySQL](https://www.mysql.com)
- [Thymeleaf](https://www.thymeleaf.org)
- [Lombok](https://projectlombok.org)
