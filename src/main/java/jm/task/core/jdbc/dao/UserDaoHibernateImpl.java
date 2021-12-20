package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;


public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        String sql =
                "CREATE TABLE IF NOT EXISTS users  ("
                        + "id BIGINT primary key auto_increment,"
                        + "name VARCHAR (255) NOT NULL,"
                        + "lastName VARCHAR (255) NOT NULL,"
                        + "age INT NOT NULL);";
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.createSQLQuery(sql).addEntity(User.class).executeUpdate();
        transaction.commit();
        session.close();
        System.out.println("Таблица создана");

    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.createSQLQuery(sql).addEntity(User.class).executeUpdate();
        transaction.commit();
        session.close();
        System.out.println("Таблица удалена");

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(new User(name, lastName, age));
        transaction.commit();
        session.close();
        System.out.println("User с именем – " + name + " добавлен в базу данных");

    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(session.get(User.class, id));
        transaction.commit();
        session.close();
        System.out.println("Пользователь удалён из таблицы");

    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = null;
        Session session = Util.getSessionFactory().openSession();
        users = session.createQuery("From User").list();
        session.close();
        return users;
    }


    @Override
    public void cleanUsersTable() {
        String sql = "DELETE FROM User";
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.createQuery(sql).executeUpdate();
        transaction.commit();
        session.close();
        System.out.println("Таблица очищена");
    }
}
