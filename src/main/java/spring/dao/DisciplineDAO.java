package spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spring.models.Faculty;
import spring.models.Student;
import spring.models.Subject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class DisciplineDAO {
    public List<Subject> getAllDisciplines() {
        List<Subject> subjects = new ArrayList<>();
        try (Connection connection = JDBC.getInstance().getConnection();
             Statement statement = connection.createStatement();) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM disciplines");

            while (resultSet.next()) {
                Subject subject = new Subject();
                subject.setId(resultSet.getInt("discipline_id"));
                subject.setHours(resultSet.getInt("discipline_hours"));
                subject.setName(resultSet.getString("discipline_name"));
                subjects.add(subject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subjects;
    }

    //
    public Subject getDisciplineIndex(int id) {
        Subject subject = null;
        try (Connection connection = JDBC.getInstance().getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement("SELECT * FROM disciplines where discipline_id = ?");
        ) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            subject = new Subject();
            subject.setId(resultSet.getInt("discipline_id"));
            subject.setName(resultSet.getString("discipline_name"));
            subject.setHours(resultSet.getInt("discipline_hours"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subject;
    }

    public void updateDiscipline(int id, Subject subject) {
        try (Connection connection = JDBC.getInstance().getConnection();
             PreparedStatement preparedStatementDiscipline =
                     connection.prepareStatement("UPDATE disciplines SET discipline_name = ?, discipline_hours = ? where discipline_id = ?");
        ) {
            preparedStatementDiscipline.setString(1, subject.getName());
            preparedStatementDiscipline.setInt(2, subject.getHours());
            preparedStatementDiscipline.setInt(3, id);
            preparedStatementDiscipline.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createDiscipline(Subject subject) {
        try (Connection connection = JDBC.getInstance().getConnection();
             PreparedStatement preparedStatementDiscipline =
                     connection.prepareStatement("INSERT INTO disciplines(discipline_name, discipline_hours) VALUES(?,?)");
        ) {

            preparedStatementDiscipline.setString(1, subject.getName());
            preparedStatementDiscipline.setInt(2, subject.getHours());
            preparedStatementDiscipline.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteDiscipline(int id) {
        try (Connection connection = JDBC.getInstance().getConnection();
             PreparedStatement preparedStatementDiscipline =
                     connection.prepareStatement("DELETE FROM disciplines WHERE discipline_id = ?");
        ) {
            preparedStatementDiscipline.setInt(1, id);
            preparedStatementDiscipline.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
