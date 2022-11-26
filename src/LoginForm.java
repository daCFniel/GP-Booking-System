import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This program implements login form
 * for the doctor interface.
 * Using Java Swing library.
 *
 * @author Daniel Bielech ~ db662
 * @version 28.12.2020
 */

public class LoginForm extends JFrame {
    // Components objects
    private Container container = getContentPane();
    private JLabel titleLabel = new JLabel("Doctor Login");
    private JLabel usernameLabel = new JLabel("Username");
    private JTextField usernameField = new JTextField();
    private JLabel passwordLabel = new JLabel("Password");
    private JPasswordField passwordField = new JPasswordField();
    private JButton loginButton = new JButton("Login");
    private boolean loggedIn = false;
    private DoctorInterface ui;  // Store user interface form.
    // Panels
    JPanel leftPanel = new JPanel(); // left panel - box layout
    JPanel middlePanel = new JPanel(); // middle panel - box layout
    JPanel rightPanel = new JPanel(); // right panel - box layout


    /**
     * Constructor for GUI class
     *
     * @param title Title of the frame.
     */
    public LoginForm(String title) {
        super(title); // Set title of the frame.
        this.setSize(700, 500); // Size: horizontally, vertically.
        this.setLocationRelativeTo(null); // Centre the frame on the screen.
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createFrameContent();
        pack(); // Size the frame so that all its contents are at or above their preferred sizes.

    }

    /**
     * Create content of the frame.
     */
    public void createFrameContent() {
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
        loginButton.addActionListener(event -> getInputData());
    }

    public void getInputData() {
        String username = usernameField.getText();
        String password = String.valueOf(passwordField.getPassword());
        try {
            login(username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Validate the credentials and login the user.
     * If the credentials are valid open the doctor interface.
     * Otherwise, show the error message.
     */
    public boolean login(String un, String ps) throws SQLException {
        // Check if given credentials match those in the database.
        ResultSet queryResult =
                Main.db.sendQuery("SELECT doctorid FROM doctorcred WHERE BINARY user= BINARY '" + un + "' AND BINARY pass= BINARY '" + ps + "';");


        if (queryResult.isBeforeFirst()) { // Check if the query returned any rows.

            // Get the id of the doctor that logged in.
            queryResult.next();
            int id = Integer.parseInt(queryResult.getString("doctorid"));

            DoctorInterface userInterface = new DoctorInterface("Doctor Interface", id);
            userInterface.SetLoginForm(this);
            userInterface.setVisible(true);

            setUi(userInterface);
            this.setVisible(false);

            Main.db.sendQueryUpdate("INSERT INTO logs (doctorid, functionality, access_date, time) VALUES ('" + id + "','Successful login', NOW(), CURRENT_TIME());"); // logs
            JOptionPane.showMessageDialog(container, "You have successfully logged in as a Doctor");
            loggedIn = true;
            return true;
        } else {
            JOptionPane.showMessageDialog(container, "Wrong login or password. Please try again.");
            Main.db.sendQueryUpdate("INSERT INTO logs (doctorid, functionality, access_date, time) VALUES (null,'Failed login attempt', NOW(), CURRENT_TIME());"); // logs
            usernameField.setText("");
            passwordField.setText("");
            return false;
        }
    }

    /**
     * Set the size and the location of all the components of the frame.
     */
    public void setSizeAndAlignment() {
        Font f30 = new Font("Arial", Font.PLAIN, 30);
        Font f15 = new Font("Arial", Font.PLAIN, 15);
        titleLabel.setFont(f30);
        usernameLabel.setFont(f15);
        passwordLabel.setFont(f15);
    }

    /**
     * Add panels to the main container.
     */
    public void addPanelsToContainer() {
        container.add(leftPanel);
        container.add(middlePanel);
        container.add(rightPanel);
    }

    /**
     * Add components to corresponding panels.
     */
    public void addComponentsToPanels() {
        leftPanel.add(Box.createRigidArea(new Dimension(250, 0))); // Empty space between components
        middlePanel.add(Box.createRigidArea(new Dimension(0, 80)));
        middlePanel.add(titleLabel);
        middlePanel.add(Box.createRigidArea(new Dimension(0, 50)));
        middlePanel.add(usernameLabel);
        middlePanel.add(usernameField);
        middlePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        middlePanel.add(passwordLabel);
        middlePanel.add(passwordField);
        middlePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        middlePanel.add(loginButton);
        middlePanel.add(Box.createRigidArea(new Dimension(0, 100)));
        rightPanel.add(Box.createRigidArea(new Dimension(250, 0)));
    }

    /**
     * Set the layout manager for the container and the panels.
     */
    public void setLayoutManager() {
        container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS)); //middle panel - x box layout
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS)); //middle panel - x box layout
        middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS)); //middle panel - x box layout
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS)); //right panel - x
    }

    /**
     * Log the user out.
     * Clear the username and password text fields.
     */
    public void loggedOut() {
        loggedIn = false;
        usernameField.setText("");
        passwordField.setText("");
    }


    /**
     * Check if the user is logged in.
     *
     * @return True if the user is logged in, false otherwise.
     */
    public boolean checkLoggedIn() {
        return loggedIn;
    }

    /**
     * Setter for user interface.
     *
     * @param ui user interface.
     */
    public void setUi(DoctorInterface ui) {
        this.ui = ui;
    }

    /**
     * Getter for user interface.
     *
     * @return user interface.
     */
    public DoctorInterface getUi() {
        return this.ui;
    }
}
