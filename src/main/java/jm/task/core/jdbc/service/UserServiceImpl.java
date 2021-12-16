package jm.task.core.jdbc.service;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {

    public void createUsersTable() {
        try (Statement statement = Util.connect()) {
            statement.executeUpdate("DROP TABLE IF EXISTS  user");
            statement.executeUpdate(
                    "CREATE TABLE user ("
                            // +"id LONG AUTOINCREMENT NOT NULL,"
                            + "id int primary key auto_increment,"
                            + "name VARCHAR (255) NOT NULL,"
                            + "lastname VARCHAR (255) NOT NULL,"
                            + "age INT NOT NULL);");

            System.out.println("Создали таблицу");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("ошибка в createUsersTable");
        }
    }

    public void dropUsersTable() {
        try {
            Statement statement = Util.connect();
            statement.executeUpdate("DROP TABLE IF EXISTS user");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("ошибка в dropUsersTable");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO user(name, lastname, age) VALUES(?, ?, ?)";
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
            System.out.println("ошибка в saveUser");
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM user WHERE id = ?";
        try (Connection connection = Util.connect().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("ошибка в removeUserById");
        }
    }

    public List<User> getAllUsers() {
        String sql = "select * from user ";
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
            System.out.println("ошибка в getAllUsers");
        }
        return list;
    }

    public void cleanUsersTable() {
        String sql = "DELETE FROM user";
        try (Statement statement = Util.connect()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("ошибка в cleanUsersTable");
        }
    }
}
