package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try(Session session = Util.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS Users" +
                    "(Id SERIAL PRIMARY KEY," +
                    "Name VARCHAR(50) NOT NULL," +
                    "LastName VARCHAR(50)," +
                    "Age SMALLINT NOT NULL)").executeUpdate();
            tx.commit();
            System.out.println("Таблица Users создана.");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try(Session session = Util.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS Users").executeUpdate();
            tx.commit();
            System.out.println("Таблица Users уничтожена.");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try(Session session = Util.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.save(new User(name, lastName, age));
            tx.commit();
            System.out.printf("Пользователь %s %s %d был добавлен.\n", name, lastName, age);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try(Session session = Util.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.delete(session.get(User.class, id));
            tx.commit();
            System.out.print("Пользователь id = " + id + " успешно удален.\n");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try(Session session = Util.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            users  = session.createQuery("FROM User", User.class).list();
            tx.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try(Session session = Util.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.createSQLQuery("TRUNCATE TABLE Users").executeUpdate();
            tx.commit();
            System.out.println("Таблица Users очищена.");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
