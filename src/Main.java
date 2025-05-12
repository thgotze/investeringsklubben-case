import controller.MainController;

public class Main {
    public static void main(String[] args) {
        // TODO: 1. Køb Aktie - Skal rykkes videre til transaction history (Thor, Sebas)
        // TODO: 2. Sælg Aktie - Skal rykkes videre til transaction history (Thor, Sebas)
        // TODO: 3. Confirm beskeder når man har solgt en aktie (Isak)
        // TODO: 4. Skal forbedre beskeden der fortæller at man ikke kan købe en aktie fordi man ikke har nok i saldoen
        // TODO: 5. Admin skal kunne oprette bruger funktion (Nikolaj)
        // TODO: 6. Tilføj en funktion der redigerer en users oplysninger (Nikolaj)
        // TODO: 7. Lav klasse diagram 2.0, med objekter, instansvariabler og metoder

        // TODO: 8. (1) Main menuen er alt for overcrowded, vi skal bestemme konkret hvilke valgmuligheder der skal være i den -
        // TODO: (2) fjerne dem der ikke er nødvendige, og merge dem der skal være sammen

        // TODO: 9. (1) Getters og setters i object klasserne skal formatteres ordentligt, i en af dem er det
        // TODO: (2) getter, setter, getter, setter men i en anden er det getter, getter, getter, setter, setter, setter...

        // TODO: 10. (1) Seperer showPortfolio op i to dele: 1. getPortfolioForUser og 2. displayPortfolio. Vi skal nemlig bruge -
        // TODO: (2) porteføljet både til at displaye det, men også når man skal vælge hvilke aktie man vil sælge

        // TODO: 11. Gennemgå alle klasser og led efter System.out.println. Kun controllers bør have dem, service og repository må ikke

        // TODO: TIL SIDST:
        // TODO: Vi skal huske at have mindst et par testmetoder
        // TODO: Vi skal fjerne metoder der ikke bruges
        // TODO: Tjekke at vi bruger camelCase overalt
        // TODO: Være sikker på at systemet ikke har mulighed for at crashe - tjekke om der mangler throw exception
        // TODO: Tjekke at vi altid går fra controller -> service -> repository og ikke controller -> repository
        // TODO: Fjerne evt. overflødelige getters/setters i objekt klasserne (f.x. User)
        // TODO: Gennemkigge om der er grammatiske stavefejl
        // TODO: Er der nogen 'magic numbers'?
        MainController.startProgram();
    }
}