import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

/**
 * This program implements user interface
 * for the doctor interface.
 * Using Java Swing library.
 *
 * @author Daniel Bielech ~ db662
 * @version 28.12.2020
 */

public class DoctorInterface extends JFrame {
    private Container container = getContentPane();
    private JLabel titleLabel;
    private JPanel mainPanel = new JPanel();
    private JButton logoutButton = new JButton("Logout");
    private LoginForm recentLoginForm; // Store account login form.
    private int loggedDoctorId; // ID of the doctor that is currently logged in.
    private JButton calendarButton = new JButton("Calendar");
    private JButton bookingButton = new JButton("View Past Booking");
    private JButton patientButton = new JButton("View Patients");
    private JButton cPatientDoctorButton = new JButton("Change Patients Doctor");
    private JScrollPane scrollPane;
    private ViewBooking recentViewBooking;
    private CalendarView recentCalendarView;
    private PatientView recentPetientView;
    private ChangeDoctor recentChangeDoctor;

    /**
     * Constructor for GUI class
     *
     * @param title Title of the frame.
     */
    public DoctorInterface(String title, int id) throws SQLException {
        super(title); // Set title of the frame.
        this.setSize(700, 550); // Size: horizontally, vertically.
        this.setLocationRelativeTo(null); // Centre the frame on the screen.
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.loggedDoctorId = id;
        this.setResizable(false);

        // Get logged doctor name
        ResultSet queryResult =
                Main.db.sendQuery("SELECT firstname, surname FROM doctor WHERE doctorId =" + loggedDoctorId + ";");
        // forename of the doctor that is currently logged in.
        String loggedDoctorForename = "Anonymous";
        // surname of the doctor that is currently logged in.
        String loggedDoctorSurname = "";
        if (queryResult.isBeforeFirst()) {
            queryResult.next();
            loggedDoctorForename = queryResult.getString("firstname");
            loggedDoctorSurname = queryResult.getString("surname");
        }
        titleLabel = new JLabel("Welcome " + loggedDoctorForename + " " + loggedDoctorSurname + "!");
        createFrameContent();

    }

    /**
     * Create content of the frame.
     */
    public void createFrameContent() throws SQLException {
        viewMessages();
        setLayoutManager();
        setSizeAndAlignment();
        addComponentsToPanels();
        addPanelsToContainer();
        addActionEvents();
    }

    /**
     * Add interactive events.
     */
    public void addActionEvents() {
        logoutButton.addActionListener(event -> logout());

        patientButton.addActionListener(event -> {
            try {
                Main.db.sendQueryUpdate("INSERT INTO logs (doctorid, functionality, access_date, time) VALUES ('" + loggedDoctorId + "','Viewing patients information', NOW(), CURRENT_TIME());"); // logs
                recentPetientView = new PatientView("Patients information", loggedDoctorId);
                recentPetientView.setVisible(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        calendarButton.addActionListener(event -> recentCalendarView = new CalendarView(loggedDoctorId));

        bookingButton.addActionListener(event -> {
            try {
                Main.db.sendQueryUpdate("INSERT INTO logs (doctorid, functionality, access_date, time) VALUES ('" + loggedDoctorId + "','Viewing bookings information', NOW(), CURRENT_TIME());"); // logs
                recentViewBooking = new ViewBooking(loggedDoctorId);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        cPatientDoctorButton.addActionListener(event -> {
            recentChangeDoctor = new ChangeDoctor(loggedDoctorId);
            recentChangeDoctor.setVisible(true);
        });
    }

    /**
     * Logout the current user.
     */
    public void logout() {
        // close all opened frames
        if (recentPetientView != null) {
            recentPetientView.dispose();
        }
        if (recentViewBooking != null) {
            recentViewBooking.getFrame().dispose();
        }
        if (recentCalendarView != null) {
            recentCalendarView.getFrame().dispose();
        }
        if (recentChangeDoctor != null) {
            recentChangeDoctor.getFrame().dispose();
        }

        recentLoginForm.setVisible(true);
        recentLoginForm.loggedOut();
        this.setVisible(false);
        JOptionPane.showMessageDialog(container, "You have been successfully logged out");
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
        mainPanel.add(Box.createRigidArea(new Dimension(50, 0))); // Empty space between components
        mainPanel.add(logoutButton);
        mainPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        mainPanel.add(calendarButton);
        mainPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        mainPanel.add(cPatientDoctorButton);
        mainPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        mainPanel.add(bookingButton);
        mainPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        mainPanel.add(patientButton);
        mainPanel.add(scrollPane);
    }

    /**
     * Set the layout manager for the container and the panels.
     */
    public void setLayoutManager() {
    }

    /**
     * Getter for loggedDoctorId.
     *
     * @return loggedDoctorId.
     */
    public int getLoggedDoctorId() {
        return this.loggedDoctorId;
    }

    /**
     * Setter for recentLoginForm
     *
     * @param lForm login form.
     */
    public void SetLoginForm(LoginForm lForm) {
        recentLoginForm = lForm;
    }

    /**
     * Retrieve data of all patients from the database.
     * Display it in the table.
     *
     * @throws SQLException connection error.
     */
    public void viewMessages() throws SQLException {
        Vector<String> columnNames = new Vector<>();
        Vector<ArrayList<Object>> data = new Vector<>();

        ResultSet queryResult = Main.db.sendQuery("SELECT message, date FROM messages WHERE doctorId =" + loggedDoctorId + ";");
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
        scrollPane = new JScrollPane(table);
    }
}
