package it.giacomobergami.jOOQ.withReflection.dbms;


import it.giacomobergami.jOOQ.withReflection.dbms.rdbms.DBMSInterface;
import it.giacomobergami.jOOQ.withReflection.model.SQLType;
import it.giacomobergami.jOOQ.withReflection.model.Table;
import it.giacomobergami.jOOQ.withReflection.model.UniqueIndex;
import org.jooq.*;
import org.jooq.impl.DSL;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Database implements AutoCloseable {

    private final String name;
    private final Connection connection;
    private final DBMSInterface dialect;

    private Database(String name, Connection connection, DBMSInterface dialect) {
        this.name = name;
        this.connection = connection;
        this.dialect = dialect;
    }

    public DSLContext jooq() {
        return DSL.using(connection, dialect.currentDialect());
    }

    public List<org.jooq.Table<?>> getTables() {
        return jooq().meta().getTables();
    }

    public Optional<org.jooq.Table<?>> getTable(Class<?> clazz) {
        String tableName = clazz.getDeclaredAnnotation(Table.class).sqlTable();
        for (org.jooq.Table<?> table : jooq().meta().getTables()) {
            if (table.getName().equals(tableName)) {
                return Optional.of(table);
            }
        }
        return Optional.empty();
    }

    public boolean tableExists(Class<?> clazz) {
        String tableName = clazz.getDeclaredAnnotation(Table.class).sqlTable();
        return jooq().meta().getTables().stream().anyMatch(x -> x.getName().equals(tableName));
    }

    public <T> InsertValuesStepN<?> insertInto(T record) {
        Optional<org.jooq.Table<?>> table = getTable(record.getClass());
        if (table.isPresent()) {
            Field[] f = record.getClass().getDeclaredFields();
            ArrayList<Object> values = new ArrayList<>();
            CreateTableColumnStep step = null;
            for (int i = 0; i<f.length; i++) {
                Field ff = f[i];
                ff.setAccessible(true);
                if (ff.isAnnotationPresent(SQLType.class)) {
                    try {
                        values.add(ff.get(record));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            // INSERT INTO record.class() ( .. fields ...) ( .. values ... )
            return jooq().insertInto(table.get(), table.get().fields()).values(values);
        }
        return null;
    }

    public boolean createTableFromClass(Class<?> clazz) {
        // CREATE TABLE clazz (
        CreateTableAsStep<Record> statement = jooq().createTableIfNotExists(clazz.getDeclaredAnnotation(Table.class).sqlTable());
        Field[] f = clazz.getDeclaredFields();
        CreateTableColumnStep step = null;
        for (int i = 0; i<f.length; i++) {
            Field ff = f[i];
            ff.setAccessible(true);
            if (ff.isAnnotationPresent(SQLType.class)) {
                // fieldName fieldType
                if (step == null) {
                    step = statement.column(ff.getName(), dialect.getClassAssociatedToSQLType(ff.getAnnotation(SQLType.class).type()));
                } else {
                    step = step.column(ff.getName(), dialect.getClassAssociatedToSQLType(ff.getAnnotation(SQLType.class).type()));
                }
            }
        }
        // )
        step.execute();
        return true;
    }

    public boolean createUniqueIndex(Class<?> clazz, String indexName) {
        Field[] f = clazz.getDeclaredFields();
        ArrayList<String> uniqueAnnotated = new ArrayList<>();
        for (int i = 0; i<f.length; i++) {
            Field ff = f[i];
            ff.setAccessible(true);
            if (ff.isAnnotationPresent(UniqueIndex.class)) {
                uniqueAnnotated.add(ff.getName());
            }
        }
        if (!uniqueAnnotated.isEmpty()) {
            String arrays[] = uniqueAnnotated.toArray(new String[uniqueAnnotated.size()]);
            // CREATE UNIQUE INDEX indexname ON clazz ( ... array ... )
            jooq().createUniqueIndex(indexName).on(clazz.getDeclaredAnnotation(Table.class).sqlTable(), arrays).execute();
            return true;
        }
        return false;
    }

    public static Optional<Database> open(DBMSInterface iface, String dbname, String username, String password) {
        if (dbname == null)
            dbname = iface.defaultDatabaseName();
        Optional<Connection> functConnection = ConnectionBridge.connect(iface, dbname, username, password);
        if (functConnection.isPresent())
            return Optional.of(new Database(dbname, functConnection.get(), iface));
        else
            return Optional.empty();
    }

    public static Optional<Database> openOrCreate(DBMSInterface iface, String dbname, String username, String password) {
        Optional<Database> functConnection = open(iface, dbname, username, password);
        if (functConnection.isPresent())
            return functConnection;
        else {
            // Trying to create the new database
            Optional<Database> defaultDb = open(iface, null, username, password);
            if (!defaultDb.isPresent()) {
                return Optional.empty();
            } else {
                try {
                    Statement s = defaultDb.get().connection.createStatement();
                    s.executeUpdate("CREATE DATABASE " + dbname);
                    try {
                        defaultDb.get().close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return open(iface, dbname, username, password);
                } catch (SQLException e) {
                    e.printStackTrace();
                    return Optional.empty();
                }
            }
        }
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
