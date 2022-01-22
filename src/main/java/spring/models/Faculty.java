package spring.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Faculty {
    private int id;
    @NotEmpty(message = "Field should not be empty")
    @Size(min = 2, max = 30, message = "Short name should be between 2 and 10 characters")
    private String shortName;
    @NotEmpty(message = "Field should not be empty")
    @Size(min = 2, max = 30, message = "Full name should be between 2 and 30 characters")
    private String fullName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return "Faculty{" +
                "id=" + id +
                ", shortName='" + shortName + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
