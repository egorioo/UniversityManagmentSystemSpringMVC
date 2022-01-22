package spring.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Group {
    private int id;
    @NotEmpty(message = "Field should not be empty")
    @Size(min = 2, max = 30, message = "Code should be between 2 and 10 characters")
    private String groupCode;
    @NotEmpty(message = "Field should not be empty")
    @Size(min = 2, max = 30, message = "Full name should be between 2 and 30 characters")
    private String fullName;
    private int facultyId;

    public int getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", groupCode='" + groupCode + '\'' +
                ", fullName='" + fullName + '\'' +
                ", facultyId=" + facultyId +
                '}';
    }
}
