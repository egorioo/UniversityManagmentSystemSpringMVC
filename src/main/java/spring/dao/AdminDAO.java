package spring.dao;

import org.springframework.stereotype.Component;
import spring.models.Admin;

import java.sql.*;

@Component
public class AdminDAO {
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

    public Admin getAdminById(int id) {
        Admin admin = null;
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM ADMINS WHERE ID = ?");
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            admin = new Admin();
            admin.setId(resultSet.getInt("id"));
            admin.setName(resultSet.getString("name"));
            admin.setSurname(resultSet.getString("surname"));
            admin.setPatronymic(resultSet.getString("patronymic"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admin;
    }
}
