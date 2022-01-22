package spring.models;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Objects;

public class Subject {
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String name;
    @Min(value = 0, message = "Mark can`t be less then zero")
    @Max(value = 100, message = "Mark cannot be greater than 100")
    private int mark;
    private int id;
    @Min(value = 0, message = "Hours cannot be less than zero")
    @Max(value = 10, message = "Hours cannot be greater than 100")
    private int hours;

    public Subject() {

    }

    public Subject(String name, int mark, int id) {
        this.name = name;
        this.mark = mark;
        this.id = id;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "name='" + name + '\'' +
                ", mark=" + mark +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return Double.compare(subject.mark, mark) == 0 && id == subject.id && Objects.equals(name, subject.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, mark, id);
    }
}
