package spring.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import spring.models.Student;
import spring.models.Subject;

import javax.swing.plaf.nimbus.State;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@Component
public class StudentDAO {

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

    public List<Student> showAll() {
        List<Student> students = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet =  statement.executeQuery("SELECT * FROM students");

            while (resultSet.next()) {
                Student student = new Student();
                student.setName(resultSet.getString("name"));
                student.setSurname(resultSet.getString("surname"));
                student.setId(resultSet.getInt("student_id"));
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public Student showIndex(int id) {
        Student student = null;
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM STUDENTS " +
                            "join groups g on(g.group_id = students.group) " +
                            "join faculties f on (f.faculty_id = g.faculty)  " +
                            "WHERE student_id=?;");
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            student = new Student();
            student.setId(resultSet.getInt("student_id"));
            student.setName(resultSet.getString("name"));
            student.setSurname(resultSet.getString("surname"));
            student.setPatronymic(resultSet.getString("patronymic"));

            student.setEmail(resultSet.getString("email"));
            student.setCourse(resultSet.getInt("course"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return student;
    }

    public void save(Student student) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("insert into users(status) values ('STUDENT');");
            ResultSet resultSet =  statement.executeQuery("select * from users order by id desc;");
            resultSet.next();
            int id = resultSet.getInt("id");

            PreparedStatement preparedStatement =
                    connection.prepareStatement("insert into students(student_id, \"group\", faculty, name, surname, patronymic, email, course) " +
                            "values (?,?,?,?,?,?,?,?);");
            preparedStatement.setInt(1,id);
            preparedStatement.setInt(2,student.getGroupId());
            preparedStatement.setInt(3,student.getFacultyId());
            preparedStatement.setString(4,student.getName());
            preparedStatement.setString(5,student.getSurname());
            preparedStatement.setString(6,student.getPatronymic());
            preparedStatement.setString(7,student.getEmail());
            preparedStatement.setInt(8,student.getCourse());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(int id, Student student) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE students " +
                            "SET \"group\"=?,faculty=?,name=?,surname=?,patronymic=?,email=?,course=? " +
                            "where student_id = ?;");

            preparedStatement.setInt(1,student.getGroupId());
            preparedStatement.setInt(2,student.getFacultyId());
            preparedStatement.setString(3,student.getName());
            preparedStatement.setString(4,student.getSurname());
            preparedStatement.setString(5,student.getPatronymic());
            preparedStatement.setString(6,student.getEmail());
            preparedStatement.setInt(7,student.getCourse());
            preparedStatement.setInt(8,id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        try {
            PreparedStatement preparedStatementMarks =
                    connection.prepareStatement("DELETE FROM marks WHERE student_id = ?");
            preparedStatementMarks.setInt(1,id);
            preparedStatementMarks.executeUpdate();

            PreparedStatement preparedStatementStudents =
                    connection.prepareStatement("DELETE FROM students WHERE student_id = ?");
            preparedStatementStudents.setInt(1,id);
            preparedStatementStudents.executeUpdate();
            PreparedStatement preparedStatementUsers =
                    connection.prepareStatement("DELETE FROM users WHERE id = ?");
            preparedStatementUsers.setInt(1,id);
            preparedStatementUsers.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
