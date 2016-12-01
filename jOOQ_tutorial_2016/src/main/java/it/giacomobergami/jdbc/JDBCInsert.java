package it.giacomobergami.jdbc;

import java.io.DataInputStream;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vasistas on 29/11/16.
 */
public class JDBCInsert {


    public static void main(String[] args) throws ParseException {

        /*
        This variable stores the DB connection and is actually the start of a transaction
         */
        List<JDBCEmployee> employees = new ArrayList<>();
        employees.add(JDBCEmployee.generate(50000000,("21/12/1920"),"Jack","Bergus",true,("21/12/2012")));
        employees.add(JDBCEmployee.generate(50000001,("23/11/1921"),"Gyankos","Begi",true,("21/12/2013")));
        Parameters.init();


        //AutoClosable object. This statement automatically closes the transaction within the database
        try (Connection transaction = DriverManager.getConnection(Parameters.DB_URL,Parameters.USER,Parameters.PASS)) {
            // Defining the SQL Query
            String sql = "insert into employees ( emp_no,  birth_date,  first_name,  last_name,  gender,  hire_date) values (?, ?, ?,?,?,?)";
            // Prepare the statement to insert the elements
            PreparedStatement ps = transaction.prepareStatement(sql);

            final int batchSize = 1000;
            int count = 0;

            for (JDBCEmployee employee : employees) {
                ps.setInt(1,employee.get_no);
                ps.setDate(2,Java2SQLData.toSQLData(employee.birth_date));
                ps.setString(3, employee.first_name);
                ps.setString(4, employee.last_name);
                ps.setString(5, employee.isFemale ? "F" : "M");
                ps.setDate(6, Java2SQLData.toSQLData(employee.hire_date));
                ps.addBatch();

                //Sooner or later, the batch has to be emptied when too data is sent there.
                if(++count % batchSize == 0) {
                    //Send some data to the relational database.
                    ps.executeBatch();
                }
            }

            ps.close(); //close the statement

        }
        //Handling an aborted transaction
        catch (SQLException e) {
            e.printStackTrace();
        }


        System.out.println("Goodbye!");
    }

}
