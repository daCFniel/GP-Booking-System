import javax.swing.*;
import java.awt.*;
import java.sql.*;

import java.util.ArrayList;


public class ChangeDoctor extends JFrame {

    private Container container = getContentPane();
    private JLabel titleLabel = new JLabel("Change Patients Doctor");
    private JLabel pLabel = new JLabel("Patient:");
    private JLabel dLabel = new JLabel("Doctor:");
    private JButton makeChangesButton = new JButton("Make Changes");
    private JPanel mainPanel = new JPanel();
    private JPanel centrePanel = new JPanel();
    //Add string arrays using sql statements to populate the two combo boxes
    private JComboBox<String> patientList;
    private JComboBox<String> doctorList;
    private ArrayList<String> pIds = new ArrayList<>();
    private ArrayList<String> dIds = new ArrayList<>();
    public int loggedDoctorId;


    public ChangeDoctor(int id) {

        //Add table to JFRame

        super.setTitle("Change Patients Doctor");
        this.setSize(700, 550);
        this.setLocationRelativeTo(null); // Centre the frame on the screen.
        this.setVisible(true);
        createFrameContent();
        loggedDoctorId = id;

    }

    public void createFrameContent() {
        populateCombos();
        setLayoutManager();
        setSizeAndAlignment();
        addComponentsToPanels();
        addPanelsToContainer();
        addActionEvents();

    }

    public void populateCombos() {
        patientList = new JComboBox<>();
        doctorList = new JComboBox<>();
        try {
            ResultSet queryResult = Main.db.sendQuery("SELECT firstname, surname, patientsid FROM patients;");
            while (queryResult.next()) {
                String tpid = queryResult.getString("patientsid");
                String fName = queryResult.getString("firstname");
                String sName = queryResult.getString("surname");
                String entry = (fName + " " + sName + " " + tpid);
                pIds.add(tpid);
                patientList.addItem(entry);
            }

            ResultSet queryResult2 = Main.db.sendQuery("SELECT firstname, surname, doctorid FROM doctor;");
            while (queryResult2.next()) {
                String tdid = queryResult2.getString("doctorid");
                String fName = queryResult2.getString("firstname");
                String sName = queryResult2.getString("surname");
                String entry = (fName + " " + sName + " " + tdid);
                dIds.add(tdid);
                doctorList.addItem(entry);
            }

        } catch (Exception ignored) {

        }
    }


    public void setSizeAndAlignment() {
        Font f30 = new Font("Arial", Font.PLAIN, 30);
        titleLabel.setFont(f30);
    }

    /**
     * Add panels to the main container.
     */
    public void addPanelsToContainer() {
        container.add(mainPanel, BorderLayout.NORTH);
        container.add(centrePanel, BorderLayout.CENTER);

    }

    /**
     * Add components to corresponding panels.
     */
    public void addComponentsToPanels() {
        mainPanel.add(Box.createRigidArea(new Dimension(100, 0))); // Empty space between components
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(100, 100))); // Empty space between components
        centrePanel.add(Box.createRigidArea(new Dimension(0, 200))); // Empty space between components
        centrePanel.add(pLabel);
        centrePanel.add(patientList);
        centrePanel.add(Box.createRigidArea(new Dimension(50, 0))); // Empty space between components
        centrePanel.add(dLabel);
        centrePanel.add(doctorList);
        centrePanel.add(Box.createRigidArea(new Dimension(50, 0))); // Empty space between components
        centrePanel.add(makeChangesButton);

    }

    public void addActionEvents() {
        makeChangesButton.addActionListener(event -> {
            try {
                updateDoctor();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Set the layout manager for the container and the panels.
     */
    public void setLayoutManager() {

    }

    public JFrame getFrame() {
        return this;
    }

    private void updateDoctor() throws SQLException {
        int pIndex = patientList.getSelectedIndex();
        int dIndex = doctorList.getSelectedIndex();
        String pId = pIds.get(pIndex);
        String dId = dIds.get(dIndex);

        String query = "UPDATE patients SET doctorID = " + dId + " WHERE patientsid = " + pId + " ;";
        Main.db.sendQueryUpdate(query);
        Main.db.sendQueryUpdate("INSERT INTO logs (doctorid, functionality, access_date, time) VALUES ('" + loggedDoctorId + "','Changed doctor for patient " + pId + "', NOW(), CURRENT_TIME());"); // logs
        JOptionPane.showMessageDialog(container, "You have changed a patients doctor.");
        String confirmationMessage = "INSERT INTO messages (doctorId, message, date) VALUES (" + loggedDoctorId + ", 'Doctor Changed for patient: " + pId + "', NOW());";
        Main.db.sendQueryUpdate(confirmationMessage);
        JOptionPane.showMessageDialog(container, "Confirmation Message Sent.");
    }
}