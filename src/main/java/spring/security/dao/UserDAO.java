package spring.security.dao;

import org.springframework.stereotype.Component;
import spring.dao.JDBC;
import spring.security.model.Role;
import spring.security.model.User;
import spring.security.util.Encoder;

import java.sql.*;

@Component
public class UserDAO {
    public User findByEmail(String email) {
        User user = null;
        try (Connection connection = JDBC.getInstance().getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement("SELECT * FROM login_info where login = ?");
             ) {

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

        }
        return user;
    }

    public boolean isStudent(int id) {
        boolean isStudent = false;
        try (Connection connection = JDBC.getInstance().getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement("SELECT * FROM USERS WHERE id = ?");
             ) {

            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            isStudent = resultSet.getString("status").equals("STUDENT");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isStudent;
    }

    public void updateUser(int id, User user) {
        try (Connection connection = JDBC.getInstance().getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement("UPDATE login_info SET login = ?, password = ? WHERE id = ?");
             PreparedStatement preparedStatementStudent =
                     connection.prepareStatement("UPDATE students SET email = ? WHERE student_id = ?");
             ) {

            preparedStatement.setString(1,user.getLogin());
            preparedStatement.setString(2, Encoder.passwordEncoder().encode(user.getPassword()));
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();

            preparedStatementStudent.setString(1,user.getLogin());
            preparedStatementStudent.setInt(2, id);
            preparedStatementStudent.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateAdmin(int id, User user) {
        try (Connection connection = JDBC.getInstance().getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement("UPDATE login_info SET login = ?, password = ? WHERE id = ?");
             ){

            preparedStatement.setString(1,user.getLogin());
            preparedStatement.setString(2, Encoder.passwordEncoder().encode(user.getPassword()));
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*public Optional<User> findByEmail(String email) {
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
        Optional<User> user1 = Optional.of(user);
        return user1;
    }*/
}
