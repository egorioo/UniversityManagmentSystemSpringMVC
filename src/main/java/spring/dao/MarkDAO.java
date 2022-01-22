package spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spring.models.Student;
import spring.models.Subject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class MarkDAO {
    private final StudentDAO studentDAO;

    @Autowired
    public MarkDAO(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }


    public Student initMarks(int id) {
        Student student = null;
        try (Connection connection = JDBC.getInstance().getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement("select * from marks join disciplines d on (d.discipline_id = marks.discipline_id) where marks.student_id = ?");
        ) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Subject> subjects = new ArrayList<>();

            while (resultSet.next()) {
                Subject subject = new Subject();
                subject.setId(resultSet.getInt("discipline_id"));
                subject.setMark(resultSet.getInt("mark"));
                subject.setName(resultSet.getString("discipline_name"));
                subject.setHours(resultSet.getInt("discipline_hours"));

                subjects.add(subject);
            }
            student = studentDAO.showIndex(id);
            student.setSubjects(subjects);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return student;
    }

    public void updateMark(int id, Subject subject) {
        try (Connection connection = JDBC.getInstance().getConnection();
             PreparedStatement preparedStatementDiscipline =
                     connection.prepareStatement("update disciplines set discipline_name = ? where discipline_id = ?");
             PreparedStatement preparedStatementMark =
                     connection.prepareStatement("update marks set mark = ? where discipline_id = ? and student_id = ?");
        ) {
            preparedStatementDiscipline.setString(1, subject.getName());
            preparedStatementDiscipline.setInt(2, subject.getId());
            preparedStatementDiscipline.executeUpdate();

            preparedStatementMark.setInt(1, subject.getMark());
            preparedStatementMark.setInt(2, subject.getId());
            preparedStatementMark.setInt(3, id);
            preparedStatementMark.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addNewMark(int id, Subject subject) {
        try (Connection connection = JDBC.getInstance().getConnection();
             PreparedStatement preparedStatementDiscipline =
                     connection.prepareStatement("INSERT INTO marks VALUES(?,?,?)");
        ) {
            preparedStatementDiscipline.setInt(1, id);
            preparedStatementDiscipline.setInt(2, subject.getId());
            preparedStatementDiscipline.setInt(3, subject.getMark());
            preparedStatementDiscipline.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteMark(int id, Subject subject) {
        try (Connection connection = JDBC.getInstance().getConnection();
             PreparedStatement preparedStatementDiscipline =
                     connection.prepareStatement("DELETE FROM marks where student_id = ? AND discipline_id = ?");
        ) {
            preparedStatementDiscipline.setInt(1, id);
            preparedStatementDiscipline.setInt(2, subject.getId());
            preparedStatementDiscipline.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
