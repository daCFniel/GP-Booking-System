import java.sql.*;

public class DBManager {
    private Statement statement;

    public boolean testConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://dragon.kent.ac.uk/fkan2?user=fkan2&password=d7isila"
                    + "");
            statement = connection.createStatement();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Send the query to the database.
     * Return the result of the query.
     *
     * @param query Given query.
     * @return result of the given query.
     * @throws SQLException db connection error.
     */
    public ResultSet sendQuery(String query) throws SQLException {
        return statement.executeQuery(query);
    }

    /**
     * Send the query to the database.
     * That updates the state of it.
     * Return the result of the query.
     *
     * @param query Given query.
     * @throws SQLException db connection error.
     */
    public void sendQueryUpdate(String query) throws SQLException {
        statement.executeUpdate(query);
    }
}