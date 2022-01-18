package spring.dao;

import org.springframework.stereotype.Component;
import spring.models.Faculty;
import spring.models.Group;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class GroupDAO {
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

    public List<Group> getAllGroups() {
        List<Group> groups = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet =  statement.executeQuery("SELECT * FROM groups");

            while (resultSet.next()) {
                Group group = new Group();
                group.setId(resultSet.getInt("group_id"));
                group.setGroupCode(resultSet.getString("group_code"));
                group.setFullName(resultSet.getString("group_full_name"));

                groups.add(group);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groups;
    }

    public Group getStudentGroup(int id) {
        Group group = null;
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("select * from students join groups g on (students.group = g.group_id) where student_id = ?;");
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            group = new Group();
            group.setId(resultSet.getInt("group_id"));
            group.setGroupCode(resultSet.getString("group_code"));
            group.setFullName(resultSet.getString("group_full_name"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return group;
    }

    public Group getGroupIndex(int id) {
        Group group = null;
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM groups where group_id = ?");
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            group = new Group();
            group.setId(resultSet.getInt("group_id"));
            group.setGroupCode(resultSet.getString("group_code"));
            group.setFullName(resultSet.getString("group_full_name"));
            group.setFacultyId(resultSet.getInt("faculty"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return group;
    }

    public void updateGroup(int id, Group group) {
        try {
            PreparedStatement preparedStatementDiscipline =
                    connection.prepareStatement("UPDATE groups SET group_code = ?, group_full_name = ?, faculty_id = ? where group_id = ?");
            preparedStatementDiscipline.setString(1,group.getGroupCode());
            preparedStatementDiscipline.setString(1,group.getFullName());
            preparedStatementDiscipline.setInt(3,group.getFacultyId());
            preparedStatementDiscipline.setInt(3,id);
            preparedStatementDiscipline.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createGroup(Group group) {
        try {
            PreparedStatement preparedStatementDiscipline =
                    connection.prepareStatement("INSERT INTO groups(group_code, group_full_name, faculty) VALUES (?,?,?)");
            preparedStatementDiscipline.setString(1,group.getGroupCode());
            preparedStatementDiscipline.setString(2,group.getFullName());
            preparedStatementDiscipline.setInt(3,group.getFacultyId());
            preparedStatementDiscipline.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteGroup(int id) {
        try {
            PreparedStatement preparedStatementDiscipline =
                    connection.prepareStatement("DELETE FROM groups WHERE group_id = ?");
            preparedStatementDiscipline.setInt(1,id);
            preparedStatementDiscipline.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
