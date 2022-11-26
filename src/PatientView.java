import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

/**
 * It provides a GUI that allows the user to
 * view their own patients information
 *
 * @author Daniel Bielech ~ db662
 * @version 18.03.2020
 */

public class PatientView extends JFrame {
    private Container container = getContentPane();
    private JLabel titleLabel = new JLabel("Your patients");
    private JPanel mainPanel = new JPanel();
    private int loggedDoctorId; // ID of the doctor that is currently logged in.

    /**
     * Constructor for GUI class
     *
     * @param title Title of the frame.
     */
    public PatientView(String title, int id) throws SQLException {
        super(title); // Set title of the frame.
        this.setSize(650, 500); // Size: horizontally, vertically.
        this.setLocationRelativeTo(null); // Centre the frame on the screen.
        this.loggedDoctorId = id;
        createFrameContent();
    }

    /**
     * Create content of the frame.
     */
    public void createFrameContent() throws SQLException {
        setLayoutManager();
        setSizeAndAlignment();
        addComponentsToPanels();
        addPanelsToContainer();
        addActionEvents();
        viewPatients();
    }

    /**
     * Add interactive events.
     */
    public void addActionEvents() {

    }


    /**
     * Set the size and the location of all the components of the frame.
     */
    public void setSizeAndAlignment() {
        Font f30 = new Font("Arial", Font.PLAIN, 30);
        titleLabel.setFont(f30);
    }

    /**
     * Add panels to the main container.
     */
    public void addPanelsToContainer() {
        container.add(mainPanel);
    }

    /**
     * Add components to corresponding panels.
     */
    public void addComponentsToPanels() {
        mainPanel.add(Box.createRigidArea(new Dimension(100, 0))); // Empty space between components
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(100, 0))); // Empty space between components
    }

    /**
     * Set the layout manager for the container and the panels.
     */
    public void setLayoutManager() {
    }

    /**
     * Retrieve data of all patients from the database.
     * Display it in the table.
     *
     * @throws SQLException connection error.
     */
    public void viewPatients() throws SQLException {
        Vector<String> columnNames = new Vector<>();
        Vector<ArrayList<Object>> data = new Vector<>();

        ResultSet queryResult = Main.db.sendQuery("SELECT * FROM patients WHERE doctorId =" + loggedDoctorId + ";");
        ResultSetMetaData metaData = queryResult.getMetaData();

        int columns = metaData.getColumnCount();

        //  Get column names.
        for (int i = 1; i <= columns; i++) {
            columnNames.add(metaData.getColumnName(i));
        }

        //  Get data.
        while (queryResult.next()) {
            ArrayList<Object> row = new ArrayList<>();

            for (int i = 1; i <= columns; i++) {
                row.add(queryResult.getObject(i));
            }
            data.add(row);
        }

        // Create Vectors and copy over elements to them.
        Vector<Vector<Object>> dataVector = new Vector<>();

        for (ArrayList<Object> subArray : data) {
            Vector<Object> subVector = new Vector<>(subArray);
            dataVector.add(subVector);
        }

        Vector<String> columnNamesVector = new Vector<>(columnNames);

        //  Create a table with patients information.
        JTable table = new JTable(dataVector, columnNamesVector);
        JScrollPane scrollPane = new JScrollPane(table);
        mainPanel.add(scrollPane);
    }
}
