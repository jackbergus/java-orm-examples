package it.giacomobergami.jOOQ.withReflection.example;

import it.giacomobergami.jOOQ.withReflection.dbms.rdbms.PostgreSQL;

public class TypeConversion02 {

    public static void main(String args[]) {
        System.out.println(new PostgreSQL().getClassAssociatedToSQLType("varchar").getType().getCanonicalName());
    }

}
