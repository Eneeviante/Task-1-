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
        try(Connection conn = Util.getConnection()) {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Users" +
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
        try(Connection conn = Util.getConnection()) {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DROP TABLE IF EXISTS Users");
            System.out.println("Таблица Users уничтожена.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try(Connection conn = Util.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO Users (name, lastName, age) VALUES (?,?,?)");
            stmt.setString(1, name);
            stmt.setString(2, lastName);
            stmt.setByte(3, age);
            stmt.executeUpdate();
            System.out.printf("Пользователь %s %s %d был добавлен.\n", name, lastName, age);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try(Connection conn = Util.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "DELETE FROM Users WHERE Id = ?");
            stmt.setLong(1, id);
            stmt.executeUpdate();
            System.out.print("Пользователь id = " + id + " успешно удален.\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try(Connection conn = Util.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Users");

            while(rs.next()) {
                User user = new User(
                        rs.getString("name"),
                        rs.getString("lastName"),
                        rs.getByte("age")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        try(Connection conn = Util.getConnection()) {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("TRUNCATE TABLE Users");
            System.out.println("Таблица Users очищена.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
