package it.giacomobergami.jdbc;

import java.io.DataInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

/**
 * Created by vasistas on 01/12/16.
 */
public class Parameters {
    public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://localhost/employees";
    public static String USER = null, PASS = null;
    public static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    //Utility for reading a string from terminal
    static final   DataInputStream in = new DataInputStream(System.in);

    public static void init() {
        System.out.println("Connecting to database...");
        System.out.println("Please provide the username:");
        try {
            USER = in.readLine();
        } catch (IOException e) {
            USER = null;
        }
        System.out.println("Please provide the password:");
        try {
            PASS = in.readLine();
        } catch (IOException e) {
            PASS = null;
        }
    }


    public static Connection getConnection() throws SQLException {
        if (USER == null || PASS == null) {
            init();
        }
        return DriverManager.getConnection(Parameters.DB_URL,Parameters.USER,Parameters.PASS);
    }

}
