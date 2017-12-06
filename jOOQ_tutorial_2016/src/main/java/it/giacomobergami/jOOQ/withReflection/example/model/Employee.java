package it.giacomobergami.jOOQ.withReflection.example.model;

import it.giacomobergami.jOOQ.withReflection.model.SQLType;
import it.giacomobergami.jOOQ.withReflection.model.Table;
import it.giacomobergami.jOOQ.withReflection.model.UniqueIndex;

@Table(sqlTable = "employee")
public class Employee {

    @SQLType(type = "bigint") @UniqueIndex
    public Long id;

    @SQLType(type = "varchar")
    public String name;

    @SQLType(type = "varchar")
    public String surname;

    @SQLType(type = "varchar")
    public String office;

}
