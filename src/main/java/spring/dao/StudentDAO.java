package spring.dao;

import org.springframework.stereotype.Component;
import spring.models.Student;
import spring.models.Subject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class StudentDAO {
    private static int STUDENTS_COUNT;
    private List<Student> students;

    {
        students = new ArrayList<>();

        Student s1 = new Student("Egor","Rudenko","Andriyovuch","egorrudenko2002@gmail.com",++STUDENTS_COUNT,"kb91",3);
        s1.setSubjects(new ArrayList<Subject>(Arrays.asList(new Subject("math",74,0), new Subject("english",70,1))));
        Student s2 = new Student("Ann","Dragun","MAximivna","ann2005@gmail.com",++STUDENTS_COUNT,"in01",2);
        s2.setSubjects(new ArrayList<Subject>(Arrays.asList(new Subject("ukrainian",50,0), new Subject("english",99,1))));
        Student s3 = new Student("Vasya","Kaplun","VAsil","vasya2@gmail.com",++STUDENTS_COUNT,"it91",3);
        s3.setSubjects(new ArrayList<Subject>(Arrays.asList(new Subject("lan",64,0), new Subject("english",79,1))));
        students.add(s1);
        students.add(s2);
        students.add(s3);
    }

    public List<Student> showAll() {
        return students;
    }

    public Student showIndex(int id) {
        return students.stream().filter(student -> student.getId() == id).findAny().orElse(null);
    }

    public void save(Student student) {
        student.setId(++STUDENTS_COUNT);
        students.add(student);
    }

    public void update(int id, Student student) {
        Student studentToBeUpdated = showIndex(id);
        studentToBeUpdated.setName(student.getName());
        studentToBeUpdated.setSurname(student.getSurname());
        studentToBeUpdated.setPatronymic(student.getPatronymic());
        studentToBeUpdated.setGroup(student.getGroup());
        studentToBeUpdated.setCourse(student.getCourse());
    }

    public void delete(int id) {
        students.removeIf(student -> student.getId() == id);
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
