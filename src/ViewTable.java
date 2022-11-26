import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.sql.*;
import java.util.ArrayList;

/**
 * This class should allow the doctor to view the visit details and
 * prescriptions regarding a past booking as well as edit the visit details and
 * prescriptions regarding a past booking.
 * The system should then send confirmation messages to the patient and the doctor.
 *
 * @author Faizah Niniola - fkan2 & Nathan Abuaku - na501
 */


public class ViewTable extends JFrame {
    public static int month;
    public static String year;
    public static int loggedDoctorId;


    // ViewTable form 
    public ViewTable(int theMonth, String theYear, int id) {
        loggedDoctorId = id;
        month = theMonth;
        year = theYear;
        initComponents();
        Display_View_In_JTable();
    }


    // Creating a connection with the sql server
    public Connection getConnection() {
        Connection connect;
        try {
            connect = DriverManager.getConnection("jdbc:mysql://dragon.kent.ac.uk/fkan2?user=fkan2&password=d7isila"
                    + "");
            return connect;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    //Creating a select query from the booking table and
//also making sure the cursor points to its current row of data using the result set
    public ArrayList<View> getviewList() {
        ArrayList<View> viewList = new ArrayList<>();
        Connection connection = getConnection();
        String query = ("SELECT * FROM booking WHERE MONTH(date) = " + month + " AND YEAR(date) = " + year);
        Statement state;
        ResultSet resultset;

        try {
            state = connection.createStatement();
            resultset = state.executeQuery(query);

            View view;

            while (resultset.next()) {
                view = new View(resultset.getInt("BookingID"), resultset.getInt("DoctorID"), resultset.getInt("PatientID"), resultset.getDate("Date"), resultset.getTime("Time"), resultset.getString("Prescriptions"), resultset.getString("Notes"));
                viewList.add(view);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return viewList;
    }

    //Displaying the view in the JTable using arraylist using array list rows
    public void Display_View_In_JTable() {
        ArrayList<View> arrlist = getviewList();
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        Object[] row = new Object[7];
        for (View view : arrlist) {
            row[0] = view.getBookingID();
            row[1] = view.getDoctorID();
            row[2] = view.getPatientID();
            row[3] = view.getDate();
            row[4] = view.getTime();
            row[5] = view.getPrescriptions();
            row[6] = view.getNotes();

            model.addRow(row);
        }
    }

    //Making sure the query executes and produces a successful or unsuccessful statement depending
    // on the query execution
    public boolean SQLQueryexecution(String query, String message) {
        Connection connect = getConnection();
        Statement state;
        try {
            state = connect.createStatement();
            if ((state.executeUpdate(query)) == 1) {
                DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                model.setRowCount(0);
                Display_View_In_JTable();


                JOptionPane.showMessageDialog(null, message + " " + "Successfully");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, message + " " + "Unsuccessful");
                return false;

            }

        } catch (Exception x) {
            x.printStackTrace();
        }
        return false;
    }


    //Initialising the components, setting action listeners and buttons
    private void initComponents() {

        JLabel bookinglabel = new JLabel();
        JLabel doctorlabel = new JLabel();
        JLabel patientlabel = new JLabel();
        JLabel datelabel = new JLabel();
        JLabel timelabel = new JLabel();
        JLabel prescriptionlabel = new JLabel();
        JLabel noteslabel = new JLabel();
        jTextField_BookingID = new JTextField();
        jTextField_DoctorID = new JTextField();
        jTextField_PatientID = new JTextField();
        jTextField_Date = new JTextField();
        jTextField_Time = new JTextField();
        jTextField_Prescriptions = new JTextField();
        jTextField_Notes = new JTextField();
        JButton jButton_Add = new JButton();
        // Variable
        JButton jButton_Delete = new JButton();
        JButton jButton_Update = new JButton();
        JScrollPane jScrollPane1 = new JScrollPane();
        jTable1 = new JTable();


        bookinglabel.setText("BookingID");

        doctorlabel.setText("DoctorID");

        patientlabel.setText("PatientID");

        datelabel.setText("Date");

        timelabel.setText("Time");

        prescriptionlabel.setText("Prescriptions");

        noteslabel.setText("Notes");

        jButton_Add.addActionListener(this::jButton_InsertActionPerformed);

        jButton_Update.addActionListener(this::jButton_UpdateActionPerformed);


        jButton_Delete.addActionListener(this::jButton_DeleteActionPerformed);

        jButton_Add.setText("Add");

        jButton_Delete.setText("Delete");

        jButton_Update.setText("Update");

        jTable1.setModel(new DefaultTableModel(
                new Object[][]{

                },
                new String[]{
                        "BookingID", "DoctorID", "PatientID", "Date", "Time", "Prescriptions", "Notes"
                }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent event) {
                jTable1MouseClicked();
            }
        });
        jScrollPane1.setViewportView(jTable1);
        jTextField_Time.setToolTipText("Do not use colon ':' for time");

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(19, 19, 19)
                                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                                .addGroup(GroupLayout.Alignment.TRAILING, layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                                                .addGroup(GroupLayout.Alignment.TRAILING, layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                                                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                                                                                .addComponent(bookinglabel)
                                                                                                                .addGap(30, 30, 30))
                                                                                                        .addGroup(layout.createSequentialGroup()
                                                                                                                .addComponent(doctorlabel)
                                                                                                                .addGap(38, 38, 38)))
                                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                        .addComponent(patientlabel)
                                                                                                        .addGap(38, 38, 38)))
                                                                                        .addGroup(layout.createSequentialGroup()
                                                                                                .addComponent(datelabel)
                                                                                                .addGap(66, 66, 66)))
                                                                                .addGroup(layout.createSequentialGroup()
                                                                                        .addComponent(timelabel)
                                                                                        .addGap(64, 64, 64)))
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addComponent(prescriptionlabel)
                                                                                .addGap(58, 58, 58))))
                                                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(noteslabel)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)))
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                                        .addComponent(jTextField_Notes, GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                                                        .addComponent(jTextField_Prescriptions, GroupLayout.Alignment.LEADING)
                                                        .addComponent(jTextField_Time, GroupLayout.Alignment.LEADING)
                                                        .addComponent(jTextField_Date, GroupLayout.Alignment.LEADING)
                                                        .addComponent(jTextField_PatientID, GroupLayout.Alignment.LEADING)
                                                        .addComponent(jTextField_DoctorID, GroupLayout.Alignment.LEADING)
                                                        .addComponent(jTextField_BookingID, GroupLayout.Alignment.LEADING))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 630, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(jButton_Add)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jButton_Delete)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jButton_Update)))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(20, 20, 20)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(bookinglabel)
                                                        .addComponent(jTextField_BookingID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(doctorlabel)
                                                        .addComponent(jTextField_DoctorID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(patientlabel)
                                                        .addComponent(jTextField_PatientID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(datelabel)
                                                        .addComponent(jTextField_Date, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(timelabel)
                                                        .addComponent(jTextField_Time, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(prescriptionlabel)
                                                        .addComponent(jTextField_Prescriptions, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                .addGap(17, 17, 17)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(noteslabel)
                                                        .addComponent(jTextField_Notes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                        .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 333, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton_Add)
                                        .addComponent(jButton_Delete)
                                        .addComponent(jButton_Update))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();

    }

    private void jButton_InsertActionPerformed(java.awt.event.ActionEvent event) {
        String query = "INSERT INTO booking (bookingid, doctorid, patientid, date, time, prescriptions, notes) VALUES (" + jTextField_BookingID.getText() + "," + jTextField_DoctorID.getText() + "," + jTextField_PatientID.getText() + ",'" + jTextField_Date.getText() + "','" + jTextField_Time.getText() + "','" + jTextField_Prescriptions.getText() + "','" + jTextField_Notes.getText() + "')";
        SQLQueryexecution(query, "Added");
        String confirmationMessage = "INSERT INTO messages (doctorId, message, date) VALUES (" + loggedDoctorId + ", 'Booking ID:" + jTextField_BookingID.getText() + " added successfully', NOW());";
        SQLQueryexecution(confirmationMessage, "Confirmation Message Sent");
        try {
            Main.db.sendQueryUpdate("INSERT INTO logs (doctorid, functionality, access_date, time) VALUES ('" + loggedDoctorId + "','Entering new booking: "
                    + jTextField_BookingID.getText() + "', NOW(), CURRENT_TIME());"); // logs
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void jButton_UpdateActionPerformed(java.awt.event.ActionEvent event) {
        String unformattedTime = jTextField_Time.getText();
        formattedTime = "";
        for (int i = 0; i < unformattedTime.length(); i++){
            if (unformattedTime.charAt(i) == ':') {
                formattedTime += "";
            } else {
                formattedTime += unformattedTime.charAt(i);
            }
        }
        String query = "UPDATE booking SET doctorid = " + jTextField_DoctorID.getText() + ", patientid = " + jTextField_PatientID.getText() + ", date = '" + jTextField_Date.getText() + "', time = " + formattedTime + ", prescriptions = '" + jTextField_Prescriptions.getText() + "', notes = '" + jTextField_Notes.getText() + "' WHERE bookingid = " + jTextField_BookingID.getText();
        SQLQueryexecution(query, "Updated");
        String confirmationMessage = "INSERT INTO messages (doctorId, message, date) VALUES (" + loggedDoctorId + ", 'Booking ID:" + jTextField_BookingID.getText() + " updated successfully', NOW());";
        SQLQueryexecution(confirmationMessage, "Confirmation Message Sent");
        try {
            Main.db.sendQueryUpdate("INSERT INTO logs (doctorid, functionality, access_date, time) VALUES ('" + loggedDoctorId + "','Updating booking: "
                    + jTextField_BookingID.getText() + "', NOW(), CURRENT_TIME());"); // logs
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void jButton_DeleteActionPerformed(java.awt.event.ActionEvent event) {
        String query = "DELETE FROM `booking` WHERE BookingID = " + jTextField_BookingID.getText();
        SQLQueryexecution(query, "Deleted");
        String confirmationMessage = "INSERT INTO messages (doctorId, message, date) VALUES (" + loggedDoctorId + ", 'Booking ID:" + jTextField_BookingID.getText() + " deleted successfully', NOW());";
        SQLQueryexecution(confirmationMessage, "Confirmation Message Sent");
        try {
            Main.db.sendQueryUpdate("INSERT INTO logs (doctorid, functionality, access_date, time) VALUES ('" + loggedDoctorId + "','Deleting booking: "
                    + jTextField_BookingID.getText() + "', NOW(), CURRENT_TIME());"); // logs
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void jTable1MouseClicked() {
        int i = jTable1.getSelectedRow();
        TableModel model = jTable1.getModel();
        jTextField_BookingID.setText(model.getValueAt(i, 0).toString());
        jTextField_DoctorID.setText(model.getValueAt(i, 1).toString());
        jTextField_PatientID.setText(model.getValueAt(i, 2).toString());
        jTextField_Date.setText(model.getValueAt(i, 3).toString());
        jTextField_Time.setText(model.getValueAt(i, 4).toString());
        jTextField_Prescriptions.setText(model.getValueAt(i, 5).toString());
        jTextField_Notes.setText(model.getValueAt(i, 6).toString());
    }


    //Setting visible the view table  
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new ViewTable(month, year, loggedDoctorId).setVisible(true));
    }

    private JTable jTable1;
    private JTextField jTextField_BookingID;
    private JTextField jTextField_Date;
    private JTextField jTextField_DoctorID;
    private JTextField jTextField_Notes;
    private JTextField jTextField_PatientID;
    private JTextField jTextField_Prescriptions;
    private JTextField jTextField_Time;
    private String formattedTime;


}
