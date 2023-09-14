package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws SQLException {
        UserServiceImpl service = new UserServiceImpl();
        service.createUsersTable();

        service.saveUser("Kevin", "De Bruyne", (byte) 32);
        service.saveUser("Elon", "Musk", (byte) 52);
        service.saveUser("Ryan", "Gosling", (byte) 42);
        service.saveUser("Donald", "Trump", (byte) 77);

        Stream.of(service.getAllUsers()).forEach(System.out::println);

        service.cleanUsersTable();

        service.dropUsersTable();
    }
}
