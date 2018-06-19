package com.repositories;

import com.model.Game;
import com.model.TestCultura;
import com.util.HibernateUtil;
import com.util.JdbcUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by sergiubulzan on 22/06/2017.
 */
public class TestHBNRepository implements IRepository<Integer,TestCultura> {
    @Override
    public int size() {
        SessionFactory sessionFactory = HibernateUtil.instance.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Integer count = (Integer) ((Long) session.createQuery("select count(*) from TestCultura").uniqueResult()).intValue();

        session.getTransaction().commit();
        session.close();

        return count;
    }

    @Override
    public void save(TestCultura entity) {
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
    public void update(Integer integer, TestCultura entity) {
        assert false : "NOT IMPLEMENTED";
    }

    @Override
    public TestCultura findOne(Integer integer) {
        SessionFactory sessionFactory = HibernateUtil.instance.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List result = session.createQuery("from TestCultura T where T.id = " + integer).list();

        session.getTransaction().commit();
        session.close();

        if(result.size() > 0){
            return (TestCultura) result.get(0);
        }else{
            return null;
        }
    }

    @Override
    public List<TestCultura> findAll() {
        System.out.println("before session factory");
        SessionFactory sessionFactory = HibernateUtil.instance.getSessionFactory();
        System.out.println(sessionFactory);
        Session session = sessionFactory.openSession();
        System.out.println(session);
        session.beginTransaction();
        List result =  session.createQuery( "FROM TestCultura" ).list();
        session.getTransaction().commit();
        session.close();

        System.out.println(result);

        return  (List<TestCultura>) result;
    }
}