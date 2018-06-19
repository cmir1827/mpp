package com.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Created by sergiubulzan on 17/06/2017.
 */
public class HibernateUtil {

    private SessionFactory sessionFactory;
    private ServiceRegistry serviceRegistry;

    private HibernateUtil(){
        initialize();
    }

    public static HibernateUtil instance = new HibernateUtil();

    void initialize() {
        // A SessionFactory is set up once for an application!

        Configuration configuration = new Configuration();
        configuration.configure();
        configuration.addResource("/masina.xml");

        serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();

        try {
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    void close() {
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }
    }

    public SessionFactory getSessionFactory()
    {
        return sessionFactory;
    }
}
