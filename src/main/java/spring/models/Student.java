package spring.models;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private int id;
    private String name;
    private String surname;
    private String patronymic;
    private String email;
    private int course;
    private Group group;
    private int groupId;
    private Faculty faculty;
    private int facultyId;

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    private List<Subject> subjects = new ArrayList<>();

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public Student() {

    }

    public Student(String name, String surname, String patronymic, String email, int idStudent, int course) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.email = email;
        this.id = idStudent;
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
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
                ", id=" + id +
                ", group='" + groupId + '\'' +
                ", faculty='" + facultyId + '\'' +
                ", course=" + course +
                ", subjects=" + subjects +
                '}';
    }
}
