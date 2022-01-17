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
    private static Connection connection;
    private static String URL = "jdbc:postgresql://localhost:5432/db";
    private static String username = "postgres";
    private static String password = "postgres";

    static {
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

    public List<Faculty> getAllFaculties() {
        List<Faculty> faculties = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet =  statement.executeQuery("SELECT * FROM faculties");

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

    public Faculty getStudentFaculty(int id) {
        Faculty faculty = null;
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("select f.faculty_id, f.faculty_code, f.faculty_full_name from students join faculties f on(f.faculty_id = students.faculty) where student_id = ?;");
            preparedStatement.setInt(1,id);
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
}
