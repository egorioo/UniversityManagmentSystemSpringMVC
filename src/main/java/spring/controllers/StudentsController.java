package spring.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring.dao.FacultyDAO;
import spring.dao.GroupDAO;
import spring.dao.StudentDAO;
import spring.models.Student;
import spring.security.dao.UserDAO;
import spring.security.model.User;

@Controller
@RequestMapping("/students")
public class StudentsController {
    private final StudentDAO studentDAO;
    private final FacultyDAO facultyDAO;
    private final GroupDAO groupDAO;
    private final UserDAO userDAO;

    @Autowired
    public StudentsController(StudentDAO studentDAO, FacultyDAO facultyDAO, GroupDAO groupDAO, UserDAO userDAO) {
        this.studentDAO = studentDAO;
        this.facultyDAO = facultyDAO;
        this.groupDAO = groupDAO;
        this.userDAO = userDAO;
    }

    //ok
    @GetMapping()
    @PreAuthorize("hasAuthority('users:write')")
    public String showAll(Model model) {
        model.addAttribute("students",studentDAO.showAll());
        return "students/allStudents";
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('users:read')")
    public String studentIndex(@PathVariable("id") int id, Model model) {
        model.addAttribute("student",studentDAO.showIndex(id));
        return "students/student";
    }
    //ok
    @GetMapping("/new")
    @PreAuthorize("hasAuthority('users:write')")
    public String newStudent(Model model) {
        model.addAttribute("student",new Student());
        model.addAttribute("faculties", facultyDAO.getAllFaculties());
        model.addAttribute("groups", groupDAO.getAllGroups());
        return "students/newStudent";
    }
    //ok

    @PostMapping()
    @PreAuthorize("hasAuthority('users:write')")
    public String create(@ModelAttribute("student") Student student) {
        System.out.println(student);
        studentDAO.save(student);
        return "redirect:/students";
    }
    //ok

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasAuthority('users:write')")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("student",studentDAO.showIndex(id));
        model.addAttribute("faculties", facultyDAO.getAllFaculties());
        model.addAttribute("groups", groupDAO.getAllGroups());
        return "students/editStudent";
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('users:write')")
    public String update(@ModelAttribute("student") Student student, @PathVariable("id") int id) {
        studentDAO.update(id,student);
        return "redirect:/students/" + id;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('users:write')")
    public String delete(@PathVariable("id") int id) {
        studentDAO.delete(id);
        return "redirect:/students";
    }

    @GetMapping("/{id}/settings")
    public String settings(@PathVariable("id") int id,Model model) {
        Student student = studentDAO.showIndex(id);
        String password = "";
        User user = userDAO.findByEmail(student.getEmail());
        model.addAttribute("user", user);
        model.addAttribute("student", student);
        return "students/settings";
    }

    @PatchMapping("/{id}/settings")
    public String changeLogInfo(@ModelAttribute("user") User user, @PathVariable("id") int id) {
        userDAO.updateUser(id, user);
        return "redirect:/students/" + id;
    }
}
