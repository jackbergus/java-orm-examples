package it.giacomobergami.jOOQ.withReflection.dbms.rdbms;

import org.jooq.SQLDialect;
import org.jooq.util.mysql.MySQLDataType;

public class MySQL implements DBMSInterface {
    @Override
    public String driverClass() {
        return "com.mysql.jdbc.Driver";
    }

    @Override
    public String connectToDatabaseOnLocalhost(String dbname) {
        return "jdbc:mysql://localhost/"+dbname;
    }

    @Override
    public String defaultDatabaseName() {
        return "";
    }

    @Override
    public SQLDialect currentDialect() {
        return SQLDialect.MYSQL;
    }

    @Override
    public Class<?> getAssociatedTypes() {
        return MySQLDataType.class;
    }
}
