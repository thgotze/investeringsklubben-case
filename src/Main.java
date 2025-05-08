import controller.Controller;
import objects.Currency;
import objects.Transaction;
import objects.User;
import repository.TransactionRepository;
import repository.UserRepository;
import service.StockService;
import service.TransactionService;
import service.UserService;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        // TODO: Køb Aktie - Skal rykkes videre til transaction history (Oliver)
        // TODO: Sælg Aktie - Skal rykkes videre til transaction history (Oliver)
        // TODO: Lav funktion der viser alle stocks (Sebastian)
        // TODO: Vis min portefølje
        // TODO: Linjer der bliver tilføjet til .csv filerne skal formatteres korrekt 
        // TODO: Admin skal kunne oprette bruger funktion (Nikolaj)
        // TODO: Admin skal kunne
        // TODO: Lave menuen der dukker op i konsollen, som viser brugeren valgmuligheder
        // TODO: Omregne valuta i bruger saldo
        
        Controller.startProgram();





    }
}
//
//    // Kører i ring for evigt. Stoppes først når man afslutter programmet '0'
//    public static void showMainMenu(Scanner scanner) {
//          System.out.println("           -*-*- THORNET -*-*-");
//                 System.out.println("> 1. Se aktiemarkedet & Kurs");
//                 System.out.println("> 2. Se mine investeringer "); // Portefølje
//                 System.out.println("> 3. Køb/Sælg aktier ");
//                 System.out.println("> 4. Se tidligere handler ");

                   // For admin:
//                 System.out.println("> 5. Se brugernes porteføljeværdi");
//                 System.out.println("> 6. Se rangliste");
//                 System.out.println("> 7. Se fordeling af aktier & sektorer");
//                 System.out.println("> 0. Afslut program");
//
//
//        String input = scanner.nextLine();
//        switch (input) {
//            case "0": // Afslut Program
//                System.out.println("Afslutter program...");
//                System.exit(0);
//                return;
//
//            case "1": // Tilføj Ordre
//                ArrayList<OrderLine> orderLineArrayList = new ArrayList<>();
//                orderLineArrayList.add(OrderLine.createOrderLine(scanner));
//
//                while (true) {
//                    System.out.println("Vil du tilføje flere pizzaer?");
//                    System.out.println("> 1. Tilføj flere pizzaer");
//                    System.out.println("> 2. Færdiggør bestilling");
//                    System.out.println("> 0. Annuller ordre");
//
//                    String addPizzaInput = scanner.nextLine();
//
//                    if (addPizzaInput.equals("1")) {
//                        orderLineArrayList.add(OrderLine.createOrderLine(scanner));
//                    } else if (addPizzaInput.equals("2")) {
//                        Order.createOrder(scanner, orderLineArrayList);
//                        returnToMainMenuPrompt(scanner);
//                    } else if (addPizzaInput.equals("0")) {
//                        System.out.println("Ordre annulleret");
//                        returnToMainMenuPrompt(scanner);
//                    } else {
//                        System.out.println("Ugyldigt input! Prøv igen");
//                    }
//                }
//
//            case "2": // Vis Ordreliste (Rediger/Annuller/Færdiggør)
//                OrderArchive.showActiveOrders(scanner);
//                showMainMenu(scanner);
//                break;
//
//            case "3": // Vis Pizzamenu
//                PizzaMenuList.showPizzaMenuList();
//                returnToMainMenuPrompt(scanner);
//                break;
//
//            case "4": // Vis Ordrehistorik
//                OrderArchive.showFinishedOrders();
//                returnToMainMenuPrompt(scanner);
//                break;
//
//            case "5": // Vis Statistik (Omsætning/Mest Populære Pizza)
//                OrderArchive.showStatisticsMenu(scanner);
//                break;
//
//            default:
//                System.out.println("Ugyldigt input! Prøv igen");
//        }
//        showMainMenu(scanner);
//    }
//}