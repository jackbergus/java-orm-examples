package it.giacomobergami.jOOQ;

import it.giacomobergami.jOOQ.model.Tables;
import it.giacomobergami.jOOQ.model.enums.EmployeesGender;
import it.giacomobergami.jOOQ.model.tables.daos.EmployeesDao;
import it.giacomobergami.jOOQ.model.tables.pojos.Employees;
import it.giacomobergami.jdbc.Java2SQLData;
import it.giacomobergami.jdbc.Parameters;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by vasistas on 01/12/16.
 */

public class jOOQInsert {

    public static void main(String args[]) throws SQLException {

        try (Connection transaction = Parameters.getConnection()) {
            DSLContext create = DSL.using(transaction, SQLDialect.MYSQL);

            //Using DAOs
            EmployeesDao dao = new EmployeesDao(create.configuration());

            //Creating a new employee
            Employees e = new Employees();
            e.setBirthDate(Java2SQLData.sqlData("02/10/1990"));
            e.setEmpNo(555555);
            e.setFirstName("Giacomo");
            e.setLastName("Bergami");
            e.setGender(EmployeesGender.M);
            e.setHireDate(Java2SQLData.sqlData("01/11/2014"));
            dao.insert(e);

            //Retrieving one single element
            e = dao.findById(555555);
            e.setHireDate(Java2SQLData.sqlData("02/11/2014"));
            dao.update(e);

            //Retrieving element after the update
            e = dao.findById(555555);
            //Printing it
            System.out.println(e.toString());
        }

    }

}
