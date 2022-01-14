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

                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public Student showIndex(int id) {
        /*return students.stream().filter(student -> student.getId() == id).findAny().orElse(null);*/
        return null;
    }

    public void save(Student student) {
       /* student.setId(++STUDENTS_COUNT);
        students.add(student);*/
    }

    public void update(int id, Student student) {
        /*Student studentToBeUpdated = showIndex(id);
        studentToBeUpdated.setName(student.getName());
        studentToBeUpdated.setSurname(student.getSurname());
        studentToBeUpdated.setPatronymic(student.getPatronymic());
        studentToBeUpdated.setGroup(student.getGroup());
        studentToBeUpdated.setCourse(student.getCourse());*/
    }

    public void delete(int id) {
        //students.removeIf(student -> student.getId() == id);
    }


    //marks
    public void updateMark(int id, Subject subject) {
        Student student = showIndex(id);
        for (int i = 0; i < student.getSubjects().size(); i++) {
            if (student.getSubjects().get(i).getId() == subject.getId()) {
                student.getSubjects().set(i,subject);
            }
        }
    }

    public void addNewMark(int id, Subject subject) {
        Student student = showIndex(id);
        subject.setId(student.getSubjects().size());
        student.getSubjects().add(subject);
    }

    public void deleteDiscipline(int id, Subject subject) {
        Student student = showIndex(id);
        System.out.println(subject);
        System.out.println(student.getSubjects());
        student.getSubjects().removeIf(subject1 -> subject1.getName().equals(subject.getName()));
    }
    /*public void save(Person person) {
        person.setId(++STUDENTS_COUNT);
        students.add(person);
    }

    public void update(int id, Person updatedPerson) {
        Person personToBeUpdated = show(id);
        personToBeUpdated.setName(updatedPerson.getName());
    }

    public void delete(int id) {
        students.removeIf(person -> person.getId() == id);
    }*/
}
