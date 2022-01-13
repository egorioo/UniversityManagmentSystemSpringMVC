package spring.models;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private String name;
    private String surname;
    private String patronymic;
    private String email;
    private int idStudent;
    private String group;
    private int course;
    private List<Subject> subjects = new ArrayList<>();

    public Student() {

    }

    public Student(String name, String surname, String patronymic, String email, int idStudent, String group, int course) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.email = email;
        this.idStudent = idStudent;
        this.group = group;
        this.course = course;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return idStudent;
    }

    public void setId(int id) {
        this.idStudent = id;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", email='" + email + '\'' +
                ", id=" + idStudent +
                ", group='" + group + '\'' +
                ", course=" + course +
                ", subjects=" + subjects +
                '}';
    }
}
