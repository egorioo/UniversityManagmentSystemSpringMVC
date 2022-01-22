package spring.dao;

import org.springframework.stereotype.Component;
import spring.models.Admin;

import java.sql.*;

@Component
public class AdminDAO {

    public Admin getAdminById(int id) {
        Admin admin = null;
        try (Connection connection = JDBC.getInstance().getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement("SELECT * FROM ADMINS WHERE ID = ?");
        ) {

            preparedStatement.setInt(1, id);
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
