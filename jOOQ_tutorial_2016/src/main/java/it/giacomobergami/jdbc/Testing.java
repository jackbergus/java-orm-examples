package it.giacomobergami.jdbc;

import it.giacomobergami.jOOQ.withReflection.dbms.rdbms.MySQL;

import java.sql.*;

public class Testing {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //Class<?> l = Class.forName("com.mysql.jdbc.Driver");
        try (Connection connection =
                     DriverManager.getConnection(new MySQL().connectToDatabaseOnLocalhost("employees"), "root", "password")) {


        }catch (SQLException e) {
            e.printStackTrace();
            System.out.println("ERROR");
        }
    }

}
