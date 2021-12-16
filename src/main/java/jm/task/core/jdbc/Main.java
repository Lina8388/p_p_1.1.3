package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;


public class Main {

    public static void main(String[] args) {

        UserServiceImpl userService = new UserServiceImpl();

        userService.createUsersTable();
        userService.saveUser("Дмитрий", "Морозов", (byte) 33);
        userService.saveUser("Ирина", "Разумовская", (byte) 23);
        userService.saveUser("Анна", "Литвинова", (byte) 38);
        userService.saveUser("Иван", "Свиридов", (byte) 25);

        System.out.println(userService.getAllUsers());

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}