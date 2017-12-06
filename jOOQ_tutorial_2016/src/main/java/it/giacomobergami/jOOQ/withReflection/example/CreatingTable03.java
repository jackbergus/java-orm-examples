package it.giacomobergami.jOOQ.withReflection.example;

import it.giacomobergami.jOOQ.withReflection.dbms.Database;
import it.giacomobergami.jOOQ.withReflection.dbms.rdbms.PostgreSQL;
import it.giacomobergami.jOOQ.withReflection.example.model.Employee;

import java.util.Optional;

public class CreatingTable03 {

    public static void main(String[] args) {
        // Creating a database if it does not exists
        Optional<Database> db = Database.openOrCreate(new PostgreSQL(), "firma", "giacomo", "password");
        if (db.isPresent()) {
            // Using an autoclosable object
            try (Database d = db.get()) {
                if (!d.tableExists(Employee.class)) {
                    System.out.println("Employee creating: " + d.createTableFromClass(Employee.class));
                    System.out.println("Employee's index creating: " + d.createUniqueIndex(Employee.class, "E_INDEX"));
                } else {
                    System.out.println("Table already exists");
                }
            } catch (Exception e) {
e.printStackTrace();
            }
        }
    }

}
