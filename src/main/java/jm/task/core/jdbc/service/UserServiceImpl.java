package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {
    //private final UserDao userDao = new UserDaoHibernateImpl();
    private final UserDao userDao = new UserDaoJDBCImpl();

    public void createUsersTable() {
        userDao.createUsersTable();
        System.out.println("Таблица Users создана.");
    }

    public void dropUsersTable() {
        userDao.dropUsersTable();
        System.out.println("Таблица Users уничтожена.");
    }

    public void saveUser(String name, String lastName, byte age) {
        userDao.saveUser(name, lastName, age);
        System.out.printf("Пользователь %s %s %d был добавлен.\n", name, lastName, age);
    }

    public void removeUserById(long id) {
        userDao.removeUserById(id);
        System.out.print("Пользователь id = " + id + " успешно удален.\n");
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public void cleanUsersTable() {
        userDao.cleanUsersTable();
        System.out.println("Таблица Users очищена.");
    }
}
