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
        try (Statement statement = Util.connect()) {
            statement.executeUpdate("DROP TABLE IF EXISTS  user");
            statement.executeUpdate(
                    "CREATE TABLE User ("
                            + "id int primary key auto_increment,"
                            + "name VARCHAR (255) NOT NULL,"
                            + "lastName VARCHAR (255) NOT NULL,"
                            + "age INT NOT NULL);");
            System.out.println("Создали таблицу");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Statement statement = Util.connect()) {
            statement.executeUpdate("DROP TABLE IF EXISTS user");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO User(name, lastName, age) VALUES(?, ?, ?)";
        try (Connection connection = Util.connect().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM User WHERE id = ?";
        try (Connection connection = Util.connect().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        String sql = "select * from User ";
        List<User> list = new ArrayList<>();
        try (Statement statement = Util.connect();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge((byte) resultSet.getInt("age"));
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void cleanUsersTable() {
        String sql = "DELETE FROM User";
        try (Statement statement = Util.connect()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
