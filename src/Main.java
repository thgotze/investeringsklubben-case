import controller.MainController;

public class Main {
    public static void main(String[] args) {
        // TODO: (1) Når man vi købe/sælge en stock så spørger den hvilken stock vil du købe?
        // TODO: (2) indtast ticker på stock. Vi vil have den siger noget i retning af:
        // TODO: (3) "Indtast navn på ønsket aktie du vil købe"

        // TODO: (1) Vi vil gerne have bekræftelses besked efter man har købt/solgt en aktie
        // TODO: (2) så der står, du har købt X antal Y aktier til Z pris, nu er din saldo XXX

        // TODO: (1) Når man printer en brugers portefølje viser den ticker, navn, antal og værdi.
        // TODO: (2) Vi vil gerne have at den også viser aktiekurs, altså prisen for 1 af den enkelte aktie

        // TODO: (1) Tilføj DKK til Saldo for bruger. + den bør formateres bedre

        // TODO: (1) Main menuen er alt for overcrowded, vi skal bestemme konkret hvilke valgmuligheder der skal være i den -
        // TODO: (2) fjerne dem der ikke er nødvendige, og merge dem der skal være sammen

        // TODO: 8 Gennemgå alle klasser og led efter System.out.println. Kun controllers bør have dem, service og repository må ikke

        // TODO: TIL SIDST:
        // TODO: Vi skal huske at have mindst et par testmetoder
        // TODO: Vi skal fjerne metoder der ikke bruges
        // TODO: Tjekke at vi bruger camelCase overalt
        // TODO: Være sikker på at systemet ikke har mulighed for at crashe - tjekke om der mangler throw exception
        // TODO: Tjekke at vi altid går fra controller -> service -> repository og ikke controller -> repository
        // TODO: Er der nogen 'magic numbers'?
        MainController.startProgram();
    }
}