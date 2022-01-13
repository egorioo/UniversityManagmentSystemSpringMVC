package spring.models;

import java.util.Objects;

public class Subject {
    private String name;
    private double mark;
    private int idSubject;

    public int getId() {
        return idSubject;
    }

    public void setId(int id) {
        this.idSubject = id;
    }

    public Subject() {

    }

    public Subject(String name, double mark, int id) {
        this.name = name;
        this.mark = mark;
        this.idSubject = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMark() {
        return mark;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "name='" + name + '\'' +
                ", mark=" + mark +
                ", id=" + idSubject +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return Double.compare(subject.mark, mark) == 0 && idSubject == subject.idSubject && Objects.equals(name, subject.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, mark, idSubject);
    }
}
