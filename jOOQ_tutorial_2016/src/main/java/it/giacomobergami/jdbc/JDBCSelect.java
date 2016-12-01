package it.giacomobergami.jdbc;

import java.io.DataInputStream;
import java.sql.*;
import java.util.function.Function;

/**
 * Created by vasistas on 29/11/16.
 */
public class JDBCSelect {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/employees";

    public static void main(String[] args) {
        Parameters.init();
        try (Connection transaction = Parameters.getConnection()) {
            Statement stmt = transaction.createStatement();
            ResultSet rs = stmt.executeQuery("select first_name, last_name from employees;");
            while(rs.next()){
                //Retrieve by column name
                String first = rs.getString("first_name");
                String last = rs.getString("last_name");

                //Display values
                System.out.print("First: " + first);
                System.out.println(", Last: " + last);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
