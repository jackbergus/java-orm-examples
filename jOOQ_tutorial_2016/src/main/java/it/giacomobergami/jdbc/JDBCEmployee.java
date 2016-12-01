package it.giacomobergami.jdbc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Just a test class to store more quickly the informations that I want to send to the relational database
 */
public class JDBCEmployee {
    public final int get_no;
    public final Date birth_date;


    public JDBCEmployee(int get_no, Date birth_date, String first_name, String last_name, boolean isFemale, Date hire_date) {
        this.get_no = get_no;
        this.birth_date = birth_date;
        this.first_name = first_name;
        this.last_name = last_name;
        this.isFemale = isFemale;
        this.hire_date = hire_date;
    }

    private JDBCEmployee(int get_no, String birth_date, String first_name, String last_name, boolean isFemale, String hire_date) throws ParseException {
        this.get_no = get_no;
        this.birth_date = Parameters.sdf.parse(birth_date);
        this.first_name = first_name;
        this.last_name = last_name;
        this.isFemale = isFemale;
        this.hire_date = Parameters.sdf.parse(hire_date);
    }

    public static JDBCEmployee generate(int get_no, String birth_date, String first_name, String last_name, boolean isFemale, String hire_date) {
        try {
            return new JDBCEmployee(get_no,birth_date,first_name,last_name,isFemale,hire_date);
        } catch (ParseException e) {
            return null;
        }
    }

    public final String first_name;
    public final String last_name;
    public final boolean isFemale;
    public final Date hire_date;



}
