package DatabaseHandler;

import java.sql.*;

public class ConnectionDB {
    protected String dbHost = "127.0.0.1";
    protected String dbPort = "3306";
    protected String dbUser = "striker";
    protected String dbPass = "eredivise";
    protected String dbName ="library";
//    protected String timeZone = "?useUnicode=true&serverTimezone=UTC";

    Connection dbConnection;

    public Connection getDbConnection() {
        String connection = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            dbConnection = DriverManager.getConnection(connection, dbUser, dbPass);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return dbConnection;
    }
}
