import org.junit.jupiter.api.Test;
import models.User;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class MedlemTest {

    private static final int TEST_USER_ID = 1;
    private static final String TEST_FULL_NAME = "Thor Gøtze";
    private static final String TEST_EMAIL = "greve.kriger@gmail.com";
    private static final LocalDate TEST_BIRTH_DATE = LocalDate.of(2003, 1, 1);
    private static final double TEST_INITIAL_CASH = 100000.0;
    private static final LocalDate TEST_CREATED_DATE = LocalDate.now();
    private static final LocalDate TEST_LAST_UPDATED = LocalDate.now();
    private static final boolean TEST_IS_ADMIN = false;
    private static final String TEST_PASSWORD = "hemmeligt";

    @Test
    void testConstructorsAndGetters() {

        User user = new User(
                TEST_USER_ID,
                TEST_FULL_NAME,
                TEST_EMAIL,
                TEST_BIRTH_DATE,
                TEST_INITIAL_CASH,
                TEST_CREATED_DATE,
                TEST_LAST_UPDATED,
                TEST_IS_ADMIN,
                TEST_PASSWORD
        );

        assertEquals(TEST_USER_ID, user.getUserId());
        assertEquals(TEST_FULL_NAME, user.getFullName());
        assertEquals(TEST_EMAIL, user.getEmail());
        assertEquals(TEST_BIRTH_DATE, user.getBirthDate());
        assertEquals(TEST_CREATED_DATE, user.getCreatedDate());
        assertEquals(TEST_LAST_UPDATED, user.getLastUpdated());
        assertEquals(TEST_IS_ADMIN, user.isAdmin());
        assertEquals(TEST_PASSWORD, user.getPassword());
    }

    @Test
    void testSetters() {
        //Opretter en tom user, da vi skal demonstrere setters
        User user = new User(0, "", "", null, 0, null, null, false, "");

        user.setFullName(TEST_FULL_NAME);
        user.setEmail(TEST_EMAIL);
        user.setInitialCashDKK(TEST_INITIAL_CASH);
        user.setAdmin(TEST_IS_ADMIN);

        assertEquals(TEST_FULL_NAME, user.getFullName());
        assertEquals(TEST_EMAIL, user.getEmail());
        assertEquals(TEST_INITIAL_CASH, user.getInitialCashDKK());
        assertEquals(TEST_IS_ADMIN, user.isAdmin());
    }

    @Test
    void testToString() {
        User user = new User(
                TEST_USER_ID,
                TEST_FULL_NAME,
                TEST_EMAIL,
                TEST_BIRTH_DATE,
                TEST_INITIAL_CASH,
                TEST_CREATED_DATE,
                TEST_LAST_UPDATED,
                TEST_IS_ADMIN,
                TEST_PASSWORD
        );

        String expectedString = "Fulde Navn: " + TEST_FULL_NAME +
                                ", Email: " + TEST_EMAIL +
                                ", Saldo: " + TEST_INITIAL_CASH +
                                ", Admin: " + TEST_IS_ADMIN;

        assertEquals(expectedString, user.toString());
    }
}
