package it.giacomobergami.jOOQ.withReflection.model;

import java.lang.annotation.Retention;

@Retention(value=java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface Table {
    String sqlTable();
}
