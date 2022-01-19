package spring.security;

import org.springframework.stereotype.Component;
import spring.models.Subject;

import java.sql.*;

@Component
public class UserDAO {

    private static Connection connection;
    private static String URL = "jdbc:postgresql://localhost:5432/db";
    private static String username = "postgres";
    private static String password = "postgres";

    static {
        System.out.println("test");
        try {
            Class.forName("org.postgresql.Driver");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(URL, username, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public User findByEmail(String email) {
        User user = null;
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM login_info where login = ?");
            preparedStatement.setString(1,email);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            user = new User();
            user.setId(resultSet.getInt("id"));
            user.setLogin(resultSet.getString("login"));
            user.setPassword(resultSet.getString("password"));
            String role = resultSet.getString("role");
            if (role.equals("USER")) {
                user.setRole(Role.USER);
            } else {
                user.setRole(Role.ADMIN);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
