/**
 * This class is generated by jOOQ
 */
package it.giacomobergami.jOOQ.model.tables.daos;


import it.giacomobergami.jOOQ.model.tables.Titles;
import it.giacomobergami.jOOQ.model.tables.records.TitlesRecord;

import java.sql.Date;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.Record3;
import org.jooq.impl.DAOImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.3"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TitlesDao extends DAOImpl<TitlesRecord, it.giacomobergami.jOOQ.model.tables.pojos.Titles, Record3<Integer, String, Date>> {

    /**
     * Create a new TitlesDao without any configuration
     */
    public TitlesDao() {
        super(Titles.TITLES, it.giacomobergami.jOOQ.model.tables.pojos.Titles.class);
    }

    /**
     * Create a new TitlesDao with an attached configuration
     */
    public TitlesDao(Configuration configuration) {
        super(Titles.TITLES, it.giacomobergami.jOOQ.model.tables.pojos.Titles.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Record3<Integer, String, Date> getId(it.giacomobergami.jOOQ.model.tables.pojos.Titles object) {
        return compositeKeyRecord(object.getEmpNo(), object.getTitle(), object.getFromDate());
    }

    /**
     * Fetch records that have <code>emp_no IN (values)</code>
     */
    public List<it.giacomobergami.jOOQ.model.tables.pojos.Titles> fetchByEmpNo(Integer... values) {
        return fetch(Titles.TITLES.EMP_NO, values);
    }

    /**
     * Fetch records that have <code>title IN (values)</code>
     */
    public List<it.giacomobergami.jOOQ.model.tables.pojos.Titles> fetchByTitle(String... values) {
        return fetch(Titles.TITLES.TITLE, values);
    }

    /**
     * Fetch records that have <code>from_date IN (values)</code>
     */
    public List<it.giacomobergami.jOOQ.model.tables.pojos.Titles> fetchByFromDate(Date... values) {
        return fetch(Titles.TITLES.FROM_DATE, values);
    }

    /**
     * Fetch records that have <code>to_date IN (values)</code>
     */
    public List<it.giacomobergami.jOOQ.model.tables.pojos.Titles> fetchByToDate(Date... values) {
        return fetch(Titles.TITLES.TO_DATE, values);
    }
}
