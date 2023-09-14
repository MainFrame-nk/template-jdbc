package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final Session session = Util.getSessionFactory().openSession();
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Users (id int not null auto_increment, " +
                "name varchar(20)," +
                "lastName varchar(20), " +
                "age int, " +
                "primary key(id))";
        try {
            session.beginTransaction();

            session.createSQLQuery(sql).addEntity(User.class).executeUpdate();

            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            System.out.println("[WARNING] Таблица Users уже создана!");
        }
    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS Users";
        try {
            session.beginTransaction();

            session.createSQLQuery(sql).addEntity(User.class).executeUpdate();

            session.getTransaction().commit();
            System.out.println("Таблица Users удалена!");
        } catch (Exception e) {
            session.getTransaction().rollback();
            System.out.println("[WARNING] Таблицы Users не существует!");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            User user = new User(name, lastName, age);
            session.beginTransaction();

            session.save(user);

            session.getTransaction().commit();
            System.out.println("Пользователь добавлен!");
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            session.beginTransaction();

            User user = session.get(User.class, id);
            session.delete(user);

            session.getTransaction().commit();
            System.out.println("Пользователь удалён!");
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        String sql = "SELECT id, name, lastName, age FROM users";
        List<User> userList = null;
        try {
            session.beginTransaction();

            userList = session.createSQLQuery(sql)
                    .addEntity(User.class)
                    .getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        String sql = "DELETE FROM Users";
        try {
            session.beginTransaction();

            session.createSQLQuery(sql).executeUpdate();

            session.getTransaction().commit();
            System.out.println("Таблица Users очищена!");
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
    }
}
