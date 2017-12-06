package it.giacomobergami.jOOQ.withReflection.example;

import it.giacomobergami.jOOQ.withReflection.dbms.Database;
import it.giacomobergami.jOOQ.withReflection.dbms.rdbms.PostgreSQL;
import it.giacomobergami.jOOQ.withReflection.example.model.Employee;

import java.util.Optional;

public class AddRecord04 {

    public static void main(String args[]) {
        Optional<Database> db = Database.openOrCreate(new PostgreSQL(), "firma", "postgres", "password");
        if (db.isPresent()) {
            // Using an autoclosable object
            try (Database d = db.get()) {
                if (!d.tableExists(Employee.class)) {
                    d.createTableFromClass(Employee.class);
                    d.createUniqueIndex(Employee.class, "E_INDEX");
                }

                Employee e = new Employee();
                e.id = 0L;
                e.name = "John";
                e.surname = "Dale";
                e.office = "Baroque";
                d.insertInto(e).execute();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
