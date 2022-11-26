import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * This add's the functionality for the CalendarView GUI.
 * It shows all past, present and future bookings.
 *
 * @author Nathan Abuaku - na501
 */


public class ViewBooking extends JFrame {
    private JFrame frame = new JFrame();
    private Boolean hasResults = false; // Results used for testing.

    public ViewBooking(int id) throws SQLException {

        //Add table to JFRame
        JTable table = new JTable();
        frame.add(new JScrollPane(table));
        frame.setTitle("Booking History");
        frame.setSize(1000, 400);
        frame.setLocationRelativeTo(null); // Centre the frame on the screen.
        frame.setVisible(true);

        // Create columns in table
        String[] columns = {"BookingID", "DoctorID", "PatientID", "Date", "Time", "Prescriptions", "Notes"};
        DefaultTableModel resultTable = new DefaultTableModel();
        resultTable.setColumnIdentifiers(columns);

        //Query
        ResultSet results =
                Main.db.sendQuery("SELECT * FROM booking WHERE doctorId =" + id + ";");
        if (results.isBeforeFirst()) {
            hasResults = true;
        }
        java.sql.ResultSetMetaData metaData = results.getMetaData();

        //Insert results from query into the table
        int column = metaData.getColumnCount();
        while (results.next()) {
            Object[] objects = new Object[column];

            for (int i = 0; i < column; i++) {
                objects[i] = results.getObject(i + 1);
            }
            resultTable.addRow(objects);
        }
        table.setModel(resultTable);

    }

    /**
     * Getter for frame.
     *
     * @return frame.
     */
    public JFrame getFrame() {
        return this.frame;
    }

    /**
     * Getter for hasResults.
     *
     * @return hasResults.
     */
    public boolean getHasResults() {
        return this.hasResults;
    }
}


