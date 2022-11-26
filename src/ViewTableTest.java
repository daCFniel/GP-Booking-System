import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ViewTableTest {
    ViewTable vTest;

    @BeforeEach
    void setUp() {
        vTest = new ViewTable(3, "2021", 1);
    }

    @Test
        // Test connection with the database
    void testDBConnection() {
        assertNotNull(vTest.getConnection());
    }

    @Test
        // Check whether the data have been retrieved
    void testRetrievedData() {
        assertFalse(vTest.getviewList().isEmpty());
    }

    @Test
        // Test confirmation message
    void testConfirmationMessagePositive() {
        assertTrue(vTest.SQLQueryexecution("INSERT INTO messages (doctorId, message, date) VALUES ('3', 'Visit details and prescriptions regarding a past booking added successfully', NOW());", "Confirmation Message Sent"));
    }

    @Test
        // Test confirmation message
    void testConfirmationMessageNegative() {
        assertFalse(vTest.SQLQueryexecution("INSERT INTO messages (doctorId, message, date) VALUES ('0', 'Visit details and prescriptions regarding a past booking added successfully', NOW());", "Error: incorrect doctor id"));
    }

}
