package spring.dao;

import org.springframework.stereotype.Component;
import spring.models.Faculty;
import spring.models.Group;
import spring.models.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class FacultyDAO {
    public List<Faculty> getAllFaculties() {
        List<Faculty> faculties = new ArrayList<>();

        try (Connection connection = JDBC.getInstance().getConnection();
             Statement statement = connection.createStatement();
        ) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM faculties");

            while (resultSet.next()) {
                Faculty faculty = new Faculty();
                faculty.setId(resultSet.getInt("faculty_id"));
                faculty.setShortName(resultSet.getString("faculty_code"));
                faculty.setFullName(resultSet.getString("faculty_full_name"));
                faculties.add(faculty);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return faculties;
    }

    public Faculty getStudentFaculty(int studentId) {
        Faculty faculty = null;
        try (Connection connection = JDBC.getInstance().getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement("select f.faculty_id, f.faculty_code, f.faculty_full_name from students join faculties f on(f.faculty_id = students.faculty) where student_id = ?;");
        ) {

            preparedStatement.setInt(1, studentId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            faculty = new Faculty();
            faculty.setId(resultSet.getInt("faculty_id"));
            faculty.setShortName(resultSet.getString("faculty_code"));
            faculty.setFullName(resultSet.getString("faculty_full_name"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return faculty;
    }

    public Faculty getFacultyIndex(int id) {
        Faculty faculty = null;
        try (Connection connection = JDBC.getInstance().getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement("SELECT * FROM faculties where faculty_id = ?");
        ) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            faculty = new Faculty();
            faculty.setId(resultSet.getInt("faculty_id"));
            faculty.setShortName(resultSet.getString("faculty_code"));
            faculty.setFullName(resultSet.getString("faculty_full_name"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return faculty;
    }

    public void updateFaculty(int id, Faculty faculty) {
        try (Connection connection = JDBC.getInstance().getConnection();
             PreparedStatement preparedStatementDiscipline =
                     connection.prepareStatement("UPDATE faculties SET faculty_code = ?, faculty_full_name = ? where faculty_id = ?");) {

            preparedStatementDiscipline.setString(1, faculty.getShortName());
            preparedStatementDiscipline.setString(2, faculty.getFullName());
            preparedStatementDiscipline.setInt(3, id);
            preparedStatementDiscipline.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createFaculty(Faculty faculty) {
        try (Connection connection = JDBC.getInstance().getConnection();
             PreparedStatement preparedStatementDiscipline =
                     connection.prepareStatement("INSERT INTO faculties(faculty_code, faculty_full_name) VALUES(?,?)");
        ) {

            preparedStatementDiscipline.setString(1, faculty.getShortName());
            preparedStatementDiscipline.setString(2, faculty.getFullName());
            preparedStatementDiscipline.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteFaculty(int id) {
        try (Connection connection = JDBC.getInstance().getConnection();
             PreparedStatement preparedStatementDiscipline =
                     connection.prepareStatement("DELETE FROM faculties WHERE faculty_id = ?");
        ) {
            preparedStatementDiscipline.setInt(1, id);
            preparedStatementDiscipline.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
