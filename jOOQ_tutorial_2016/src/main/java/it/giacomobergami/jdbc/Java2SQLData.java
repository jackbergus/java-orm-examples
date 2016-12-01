package it.giacomobergami.jdbc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;

/**
 * Created by vasistas on 01/12/16.
 */
public class Java2SQLData {

    public static java.sql.Date toSQLData(java.util.Date date) {
        return java.sql.Date.valueOf(Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
    }

    public static java.sql.Date sqlData(String str) {
        try {
            return toSQLData(Parameters.sdf.parse(str));
        } catch (ParseException e) {
            return null;
        }
    }

}
