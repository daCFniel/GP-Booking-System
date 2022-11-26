import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DoctorInterfaceTest {
    LoginForm testForm1 = new LoginForm("Test1");

    @BeforeEach
    void setUp() {
        Main.main(null);
    }

    @Test
    void testLoggedDoctorId1() throws SQLException {
        testForm1.login("SamDime76", "Orangecat37");
        assertEquals(1, testForm1.getUi().getLoggedDoctorId());
        testForm1.getUi().logout();
    }

    @Test
    void testLoggedDoctorId2() throws SQLException {
        testForm1.login("AngelaHeart901", "Yellowbean");
        assertEquals(2, testForm1.getUi().getLoggedDoctorId());
        testForm1.getUi().logout();
    }

    @Test
    void testLoggedDoctorId3() throws SQLException {
        testForm1.login("DavidHap", "jhsbfmke");
        assertEquals(3, testForm1.getUi().getLoggedDoctorId());
        testForm1.getUi().logout();
    }
}