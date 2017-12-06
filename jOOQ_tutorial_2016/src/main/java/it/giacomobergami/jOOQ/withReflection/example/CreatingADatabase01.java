package it.giacomobergami.jOOQ.withReflection.example;

import it.giacomobergami.jOOQ.withReflection.dbms.Database;
import it.giacomobergami.jOOQ.withReflection.dbms.rdbms.PostgreSQL;

import java.util.Optional;

public class CreatingADatabase01 {

    public static void main(String args[]) {
        // Creating a database if it does not exists
        Optional<Database> db = Database.openOrCreate(new PostgreSQL(), "firma", "giacomo", "password");
        // After the previous process, it should always
        System.out.println(db.isPresent());
    }

}
