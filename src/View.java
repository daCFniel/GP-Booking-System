import java.sql.Date;

/**
 * View of the booking update interface
 *
 * @author Faizah
 */
public class View {
    private int BookingID;
    private int DoctorID;
    private int PatientID;
    Date Date;
    java.sql.Time Time;
    private String Prescriptions;
    private String Notes;


    public View(int BookingID, int DoctorID, int PatientID, Date Date, java.sql.Time time2, String Prescriptions, String Notes) {
        this.BookingID = BookingID;
        this.DoctorID = DoctorID;
        this.PatientID = PatientID;
        this.Date = Date;
        this.Time = time2;
        this.Prescriptions = Prescriptions;
        this.Notes = Notes;
    }

    public int getBookingID() {
        return BookingID;
    }

    public int getDoctorID() {
        return DoctorID;
    }

    public int getPatientID() {
        return PatientID;
    }

    public Date getDate() {
        return Date;
    }

    public java.sql.Time getTime() {
        return Time;
    }

    public String getPrescriptions() {
        return Prescriptions;
    }

    public String getNotes() {
        return Notes;
    }
}
