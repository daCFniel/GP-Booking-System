import java.sql.SQLException;

import javax.swing.*;

/**
 * This program implements an application that
 * is doctor interface in GP booking system.
 * It provides a GUI that allows the user to
 * view their own patients information and
 * perform various operations on the data.
 *
 * @author Daniel Bielech ~ db662
 * @version 28.12.2020
 */

public class Main {

    static DBManager db; // Store the database connection.

    public static void main(String[] args) {
        // Connect to the database
        db = new DBManager();
        db.testConnection();

        // Create a new frame - login form
        JFrame loginForm = new LoginForm("Account Login");
        loginForm.setVisible(true);
    }
}
