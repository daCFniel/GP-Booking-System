import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class ViewBookingTest {
    ViewBooking view;
    ViewBooking view2;

    @BeforeEach
    void setUp() throws SQLException {
        Main.main(null);
        view = new ViewBooking(1);
        view2 = new ViewBooking(0);
    }

    @Test
        // Check if the frame for the bookings view is created
    void testFrame() {
        assertNotNull(view.getFrame());
    }

    @Test
        // Check whether results have been retrieved
    void testResultsPositive() {
        assertTrue(view.getHasResults());
    }

    @Test
        // Check whether results have been retrieved
    void testResultsNegative() {
        assertFalse(view2.getHasResults());
    }

}
