import models.Stock;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

public class StockTest {

    @Test
    void testStockGetPrice() {
        Stock stock = new Stock("AAPL", "Apple Inc.", "Technology", 150.0, "AA", "DKK", 1.5, "Nasdaq Copenhagen", LocalDate.now());
        assertEquals(155.0, stock.getPrice(), 0.001);
    }
}