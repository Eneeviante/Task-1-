package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try(Connection connection = Util.getConnection();
            Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Users" +
                    "(Id SERIAL PRIMARY KEY," +
                    "Name VARCHAR(50) NOT NULL," +
                    "LastName VARCHAR(50)," +
                    "Age SMALLINT NOT NULL)");
            System.out.println("Таблица Users создана.");
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try(Connection connection = Util.getConnection();
            Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS Users");
            System.out.println("Таблица Users уничтожена.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try(Connection connection = Util.getConnection();
            PreparedStatement  preparedStatement = connection.prepareStatement(
                    "INSERT INTO Users (name, lastName, age) VALUES (?,?,?)")){
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.printf("Пользователь %s %s %d был добавлен.\n", name, lastName, age);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try(Connection connection = Util.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM Users WHERE Id = ?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.print("Пользователь id = " + id + " успешно удален.\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try(Connection connection = Util.getConnection();
            Statement statement = connection.createStatement()) {
            ResultSet usersResultSet = statement.executeQuery("SELECT * FROM Users");

            while(usersResultSet.next()) {
                User user = new User(
                        usersResultSet.getString("name"),
                        usersResultSet.getString("lastName"),
                        usersResultSet.getByte("age")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        try(Connection connection = Util.getConnection();
            Statement statement = connection.createStatement()) {
            statement.executeUpdate("TRUNCATE TABLE Users");
            System.out.println("Таблица Users очищена.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
