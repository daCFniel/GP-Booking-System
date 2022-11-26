import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalendarViewTest {
    CalendarView calendar;
    ViewTable v;

    @BeforeEach
    void setUp() {
        calendar =  new CalendarView(1);
    }

    @Test
        // Check if the calendar components have been created.
    void testCalendarComponents() {
        assertNotNull(CalendarView.month);
        assertNotNull(CalendarView.year);
        assertNotNull(CalendarView.prev);
        assertNotNull(CalendarView.next);
        assertNotNull(CalendarView.model);
        assertNotNull(CalendarView.yearlist);
    }

    @Test
        // Check if the calendar shows correct month
    void testCalendarMonth() {
        assertEquals(2,  CalendarView.cmonth);
    }

    @Test
        // Check if the calendar shows correct year
    void testCalendarYear() {
        assertEquals(2021, CalendarView.cyear);
    }

    @Test
        // Check if correct data (month, year, doctor id) is sent to ViewTable
    void testCalendarView() {
        v = new ViewTable(CalendarView.cmonth, (String) CalendarView.yearlist.getSelectedItem(), CalendarView.loggedDoctorId);
        assertEquals(1, ViewTable.loggedDoctorId);
        assertEquals("2021", ViewTable.year);
        assertEquals(2, ViewTable.month);

        v = new ViewTable(5, "2021", 3);
        assertEquals(3, ViewTable.loggedDoctorId);
        assertEquals("2021", ViewTable.year);
        assertEquals(5, ViewTable.month);
    }
}