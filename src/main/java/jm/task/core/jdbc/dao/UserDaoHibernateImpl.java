package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try(Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS Users" +
                    "(Id SERIAL PRIMARY KEY," +
                    "Name VARCHAR(50) NOT NULL," +
                    "LastName VARCHAR(50)," +
                    "Age SMALLINT NOT NULL)").executeUpdate();
            transaction.commit();
            System.out.println("Таблица Users создана.");
        }
        catch (HibernateException e) {
            if(transaction != null)
                transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try(Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS Users").executeUpdate();
            transaction.commit();
            System.out.println("Таблица Users уничтожена.");
        }
        catch (HibernateException e) {
            if(transaction != null)
                transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try(Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
            System.out.printf("Пользователь %s %s %d был добавлен.\n", name, lastName, age);
        }
        catch (HibernateException e) {
            if(transaction != null)
                transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try(Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(session.get(User.class, id));
            transaction.commit();
            System.out.print("Пользователь id = " + id + " успешно удален.\n");
        }
        catch (HibernateException e) {
            if(transaction != null)
                transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        List<User> users = new ArrayList<>();
        try(Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            users  = session.createQuery("FROM User", User.class).list();
            transaction.commit();
        }
        catch (HibernateException e) {
            if(transaction != null)
                transaction.rollback();
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try(Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("TRUNCATE TABLE Users").executeUpdate();
            transaction.commit();
            System.out.println("Таблица Users очищена.");
        }
        catch (HibernateException e) {
            if(transaction != null)
                transaction.rollback();
            e.printStackTrace();
        }
    }
}
