import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.GregorianCalendar;

/**
 * This program implements user interface
 * for the doctor interface.
 * Using Java Swing library.
 *
 * @author Faizah Niniola ~ fkan2 & Nathan
 * @version 05.03.2021
 */

//The system should allow a doctor to view their bookings by 
//entering a month and year.

public class CalendarView {
    static JTable Calendar; // table required for calendar
    private JFrame frame; // panel or frame
    static int loggedDoctorId; // ID of the doctor that is currently logged in.
    static JLabel month, year;
    static JButton prev, next, viewButton; // button event click
    static JComboBox<String> yearlist;// for selecting date
    static DefaultTableModel model; // default table column for calendar
    static int cyear, cmonth, yyear, mmonth, dday;

    public CalendarView(int id) {
        loggedDoctorId = id;
        //Creating frame
        frame = new JFrame("Calendar"); //creating calendar frame
        frame.setSize(445, 400); //set size of frame to 200 x 250
        frame.setLocationRelativeTo(null); // Centre the frame on the screen.
        Container container = frame.getContentPane(); //creating container for frame

        //Control
        prev = new JButton("<"); // prev button
        next = new JButton(">"); // next button
        viewButton = new JButton("View Bookings");
        // panel
        JPanel mainPanel = new JPanel(null);
        month = new JLabel("Months");
        year = new JLabel("Year:"); // label for changing year
        yearlist = new JComboBox<>(); // year drop down list

        model = new DefaultTableModel();
        Calendar = new JTable(model);
        // for selecting people from database to put on event calendar
        JScrollPane scroll = new JScrollPane(Calendar);

        //Creating action listeners
        yearlist.addActionListener(new yearlist_button());
        prev.addActionListener(new prev_button());
        next.addActionListener(new next_button());
        viewButton.addActionListener(new view_button());
        //Adding controls to panels  
        container.add(mainPanel);
        mainPanel.add(prev);
        mainPanel.add(next);
        mainPanel.add(viewButton);
        mainPanel.add(month);
        mainPanel.add(year);
        mainPanel.add(yearlist);
        mainPanel.add(scroll);

        //Setting bounds
        mainPanel.setBounds(600, 200, 330, 345);
        month.setBounds(70 - month.getPreferredSize().width / 4, 100, 400, 25);
        year.setBounds(15, 300, 80, 20);
        yearlist.setBounds(315, 300, 100, 34);
        prev.setBounds(10, 55, 60, 30);
        next.setBounds(355, 58, 60, 30);
        viewButton.setBounds(15, 330, 250, 20);
        scroll.setBounds(15, 100, 400, 200);

        //Creating border
        mainPanel.setBorder(BorderFactory.createTitledBorder("Calendar"));

        //Frame visibility
        frame.setResizable(false);
        frame.setVisible(true);

        //Weekday/ends labels
        String[] weekdays = {"Mon", "Tue", "Wed", "Thurs", "Fri", "Sat", "Sun"};
        for (int j = 0; j < 7; j++) {
            model.addColumn(weekdays[j]);
        }

        //Creating a real gregorian calendar
        GregorianCalendar calen = new GregorianCalendar();
        dday = calen.get(GregorianCalendar.DAY_OF_MONTH);
        mmonth = calen.get(GregorianCalendar.MONTH);
        yyear = calen.get(GregorianCalendar.YEAR);
        cmonth = mmonth;
        cyear = yyear;

        //Settings for Calendar
        Calendar.getParent().setBackground(Calendar.getBackground());

        Calendar.getTableHeader().setResizingAllowed(true);
        Calendar.getTableHeader().setReorderingAllowed(true);

        Calendar.setColumnSelectionAllowed(true);
        Calendar.setRowSelectionAllowed(true);
        Calendar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        Calendar.setRowHeight(40);
        model.setColumnCount(7);
        model.setRowCount(6);

        //Population of year drop down
        for (int j = yyear - 10; j <= yyear + 60; j++) {
            yearlist.addItem(String.valueOf(j));
        }

        Calendarmain(mmonth, yyear);
    }

    static void Calendarmain(int themonth, int theyear) {
        int num, start;
        String[] themonths = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

        // next and previous buttons enabled to ten years back and 60 years forward
        if (themonth == 0 && theyear <= yyear - 10)
            prev.setEnabled(false);
        if (themonth == 11 && theyear >= yyear + 100)
            next.setEnabled(false);
        month.setText(themonths[themonth]);
        month.setBounds(170 - month.getPreferredSize().width / 4, 30, 160, 15);
        yearlist.setSelectedItem(String.valueOf(theyear));

        //importing real calendar
        GregorianCalendar calen = new GregorianCalendar(theyear, themonth, 1);
        num = calen.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        start = calen.get(GregorianCalendar.DAY_OF_WEEK);

        if (start == GregorianCalendar.SUNDAY) {// making sure the week starts on the right day
            start = 7;
        } else {
            start--;
        }

        for (int j = 0; j < 6; j++) {
            for (int k = 0; k < 7; k++) {
                model.setValueAt(null, j, k);
            }
        }

        for (int j = 1; j <= num; j++) {
            int row = ((j + start - 2) / 7);
            int col = (j + start - 2) % 7;
            model.setValueAt(j, row, col);
        }
        Calendar.setDefaultRenderer(Calendar.getColumnClass(0), new calendarRen());
    }

    //This method sets the background colours and borders for the calendar table(shaded colour for current day)
    static class calendarRen extends DefaultTableCellRenderer {
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
            setBackground(new Color(204, 204, 204)); //grey box

            if (value != null) {
                if (Integer.parseInt(value.toString()) == dday && cmonth == mmonth && cyear == yyear) {
                    setBackground(new Color(249, 249, 255)); // blue box
                }
            }
            setBorder(null);
            setForeground(Color.black);// black font 
            return this;
        }
    }


    //This action listeners enables the years jan-dec drop down list
    public static class yearlist_button implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (yearlist.getSelectedItem() != null) {
                String s = yearlist.getSelectedItem().toString();
                cyear = Integer.parseInt(s);
                Calendarmain(cmonth, cyear);
            }
        }
    }

    //This action listeners allows previous button to go back through the years and back through the months
    public static class prev_button implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (cmonth == 0) {
                cmonth = 11;
                cyear -= 1;
            }//
            else {
                cmonth -= 1;
            }
            Calendarmain(cmonth, cyear);
        }
    }

    //This action listeners allows next button to go forward through the years and forward through the months
    public static class next_button implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (cmonth == 11) {
                cmonth = 0;
                cyear += 1;
            } else {
                cmonth += 1;
            }
            Calendarmain(cmonth, cyear);
        }
    }


    public static class view_button implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            int month = cmonth + 1;
            String year = (String) yearlist.getSelectedItem();
            ViewTable v = new ViewTable(month, year, loggedDoctorId);
            v.setLocationRelativeTo(null);
            ViewTable.main(null);
            try {
                Main.db.sendQueryUpdate("INSERT INTO logs (doctorid, functionality, access_date, time) VALUES ('" + loggedDoctorId + "','Viewing bookings on: "
                        + month + "." + year + "', NOW(), CURRENT_TIME());"); // logs
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public JFrame getFrame() {
        return this.frame;
    }
}

