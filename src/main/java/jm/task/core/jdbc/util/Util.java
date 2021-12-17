package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {

    private static final String URL = "jdbc:mysql://localhost:3306/lesson_1_1_3?" + "useSSL=false" + "&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "vfhbz638879";

    public static Statement connect() throws SQLException {

        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASS);
            Statement statement = connection.createStatement();
            //System.out.println("Соединение с БД установлено");
            return statement;
        } catch (SQLException e) {
            System.err.println("Ошибка подключения к БД");
        }
        return null;
    }
}
