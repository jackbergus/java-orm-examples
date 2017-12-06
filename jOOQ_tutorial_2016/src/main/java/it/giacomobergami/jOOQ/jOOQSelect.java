package it.giacomobergami.jOOQ;

import it.giacomobergami.jOOQ.model.Employees;
import it.giacomobergami.jOOQ.model.Tables;
import it.giacomobergami.jOOQ.model.tables.records.EmployeesRecord;
import it.giacomobergami.jOOQ.model.Tables.*;
import it.giacomobergami.jdbc.Parameters;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Consumer;

/**
 * Created by vasistas on 01/12/16.
 */
public class jOOQSelect {

    public static void main(String args[]) throws SQLException {

        try (Connection transaction = Parameters.getConnection()) {
            DSLContext create = DSL.using(transaction, SQLDialect.MYSQL);

            create.select(Tables.EMPLOYEES_.FIRST_NAME,Tables.EMPLOYEES_.LAST_NAME)
                    .from(Tables.EMPLOYEES_, Tables.DEPARTMENTS)
                    .limit(5)
                    .fetchInto(Tables.EMPLOYEES_).forEach( er -> System.out.println(er.getFirstName()+" "+er.getLastName()));
        }

    }

}
