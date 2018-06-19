package com.repositories;

import com.model.Masina;
import com.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by sergiubulzan on 22/06/2017.
 */
public class MasinaHBNRepositpry implements IRepository<Integer,Masina> {
    @Override
    public int size() {
        SessionFactory sessionFactory = HibernateUtil.instance.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Integer count = (Integer) ((Long) session.createQuery("select count(*) from Masina").uniqueResult()).intValue();

        session.getTransaction().commit();
        session.close();

        return count;
    }

    @Override
    public void save(Masina entity) {
        SessionFactory sessionFactory = HibernateUtil.instance.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.save(entity);

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(Integer integer) {
        assert false : "NOT IMPLEMENTED";
    }

    @Override
    public void update(Integer integer, Masina entity) {
        assert false : "NOT IMPLEMENTED";
    }

    @Override
    public Masina findOne(Integer integer) {
        SessionFactory sessionFactory = HibernateUtil.instance.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List result = session.createQuery("from Masina T where T.id = " + integer).list();

        session.getTransaction().commit();
        session.close();

        if(result.size() > 0){
            return (Masina) result.get(0);
        }else{
            return null;
        }
    }

    @Override
    public List<Masina> findAll() {
        System.out.println("before session factory");
        SessionFactory sessionFactory = HibernateUtil.instance.getSessionFactory();
        System.out.println(sessionFactory);
        Session session = sessionFactory.openSession();
        System.out.println(session);
        session.beginTransaction();
        List result =  session.createQuery( "FROM Masina" ).list();
        session.getTransaction().commit();
        session.close();

        System.out.println(result);

        return  (List<Masina>) result;
    }
}