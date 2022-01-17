package spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spring.models.Student;
import spring.models.Subject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class DisciplineDAO {
    private static Connection connection;
    private static String URL = "jdbc:postgresql://localhost:5432/db";
    private static String username = "postgres";
    private static String password = "postgres";
    /*private final StudentDAO studentDAO;

    @Autowired
    public MarkDAO(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }*/

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

    public List<Subject> getAllDisciplines() {
        List<Subject> subjects = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet =  statement.executeQuery("SELECT * FROM disciplines");

            while (resultSet.next()) {
                Subject subject = new Subject();
                subject.setId(resultSet.getInt("discipline_id"));
                subject.setName(resultSet.getString("discipline_name"));
                subjects.add(subject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subjects;
    }
}
