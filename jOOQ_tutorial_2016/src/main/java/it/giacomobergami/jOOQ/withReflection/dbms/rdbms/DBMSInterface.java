package it.giacomobergami.jOOQ.withReflection.dbms.rdbms;

import org.jooq.DataType;
import org.jooq.SQLDialect;

import java.lang.reflect.Field;

public interface DBMSInterface {

    /**
     * Driver class associated to the RDBMS
     * @return
     */
    String driverClass();

    /**
     * JDBC url used to connect to the database
     * @param dbname    Using the dbname
     * @return
     */
    String connectToDatabaseOnLocalhost(String dbname);
    String defaultDatabaseName();
    SQLDialect currentDialect();
    Class<?> getAssociatedTypes();

    default DataType getClassAssociatedToSQLType(String name) {
            Field[] fls = getAssociatedTypes().getFields();
            DataType search = null;
            for (int i = 0; i< fls.length; i++) {
                Field f = fls[i];
                f.setAccessible(true);
                try {
                    if ((search = ((DataType)f.get(null))).getTypeName().toLowerCase().equals(name.toLowerCase())) {
                        return search;
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            return null;
    }

}
