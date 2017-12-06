package it.giacomobergami.jOOQ;

import it.giacomobergami.jOOQ.model.Tables;
import it.giacomobergami.jOOQ.model.enums.EmployeesGender;
import it.giacomobergami.jdbc.Parameters;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by vasistas on 01/12/16.
 */
public class jOOQJoin {

    public static class ResultClass  {
        public final String MrMrs;
        public final String name;
        public final String surname;
        public final String title;

        public ResultClass(EmployeesGender mrMrs, String name, String surname, String title) {
            MrMrs = mrMrs.getLiteral().equals("M") ? "Mr." : "Mrs.";
            this.name = name;
            this.surname = surname;
            this.title = title;
        }

        // Serialization method
        @Override
        public String toString() {
            return "Hello, "+ MrMrs +" "+name+" "+ surname+","+title+"!\n";
        }
    }

    public static void main(String args[]) throws SQLException {

        try (Connection transaction = Parameters.getConnection()) {
            DSLContext create = DSL.using(transaction, SQLDialect.MYSQL);

            create.select(Tables.EMPLOYEES_.GENDER,Tables.EMPLOYEES_.FIRST_NAME,Tables.EMPLOYEES_.LAST_NAME,Tables.TITLES.TITLE)
                    .from(Tables.EMPLOYEES_)
                    .join(Tables.TITLES)
                    .onKey()
                    .limit(10)
                    .fetchInto(ResultClass.class)
                    .forEach(System.out::print); // Using Java 8 Streams alongside with lambdas
        }

    }

}
