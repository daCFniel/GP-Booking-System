import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LoginFormTest {
    LoginForm testForm1 = new LoginForm("Test1");
    LoginForm testForm2 = new LoginForm("Test2");
    LoginForm testForm3 = new LoginForm("Test3");
    LoginForm testForm4 = new LoginForm("Test4");
    DBManager dbt = new DBManager();

    @BeforeEach
    void setUp() throws SQLException {
        Main.main(null);
    }

    @Test
    void testTestConnection() {
        assertTrue(dbt.testConnection());
    }

    @Test
    void testLoginPositive() throws SQLException {
        assertTrue(testForm1.login("SamDime76", "Orangecat37"));
    }

    @Test
    void testLoginNegative() throws SQLException {
        assertFalse(testForm1.login("LauraBocchi4", "Sdev2"));
    }

    @Test
    void testLoginCaseSensitivity() throws SQLException {
        assertFalse(testForm1.login("samdime76", "orangecat37"));
    }

    @Test
    void testNotLoggedIn() {
        assertFalse(testForm2.checkLoggedIn());
    }

    @Test
    void testIsLoggedIn() throws SQLException {
        testForm3.login("SamDime76", "Orangecat37");
        assertTrue(testForm3.checkLoggedIn());
    }

    @Test
    void testIsLoggedInFails() throws SQLException {
        testForm2.login("SamDime76", "OraneCat37");
        assertFalse(testForm2.checkLoggedIn());
    }

    @Test
    void testIsLoggedInAfterLogOut() throws SQLException

    @Test
    void testGetPrice() {
        Slice s1 = Slice(2);
        double pricePG = 0.7;
        assertTrue(s1.getPrice(pricePG) > 0);
        assertEquals(pricePG*s1.getWeight(), s1.getPrice(pricePG));
    }
}

	