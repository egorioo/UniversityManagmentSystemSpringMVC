package spring.models;

public class Group {
    private int id;
    private String groupCode;
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
