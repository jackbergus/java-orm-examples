package it.db.bergamigiacomo.mysql;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Created by vasistas on 23/11/15.
 */
public class HibernateFactory {


    private static SessionFactory factory;

    public static SessionFactory createSessionFactory() {
        if (factory==null){
            try{
                factory = new Configuration().configure().buildSessionFactory();
            }catch (Throwable ex) {
                System.err.println("Failed to create sessionFactory object." + ex);
                throw new ExceptionInInitializerError(ex);
            }
        }
        return factory;
    }

}
