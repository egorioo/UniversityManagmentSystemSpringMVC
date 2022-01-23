package spring.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    private static final Logger LOGGER = Logger.getLogger(StudentsController.class);

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
        LOGGER.debug("show all students");
        return "students/allStudents";
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('users:read','users:write')")
    public String studentIndex(@PathVariable("id") int id, Model model) {
        model.addAttribute("student",studentDAO.showIndex(id));
        LOGGER.debug("show student with " + id);
        LOGGER.debug(studentDAO.showIndex(id));
        return "students/student";
    }
    //ok
    @GetMapping("/new")
    @PreAuthorize("hasAuthority('users:write')")
    public String newStudent(Model model) {
        model.addAttribute("student",new Student());
        model.addAttribute("faculties", facultyDAO.getAllFaculties());
        model.addAttribute("groups", groupDAO.getAllGroups());
        LOGGER.debug("student creation");
        return "students/newStudent";
    }
    //ok

    @PostMapping()
    @PreAuthorize("hasAuthority('users:write')")
    public String create(@ModelAttribute("student") Student student, Model model) {
        studentDAO.save(student);
        LOGGER.debug("student created");
        LOGGER.debug(student);
        return "redirect:/students";
    }
    //ok

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasAuthority('users:write')")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("student",studentDAO.showIndex(id));
        model.addAttribute("faculties", facultyDAO.getAllFaculties());
        model.addAttribute("groups", groupDAO.getAllGroups());
        LOGGER.debug("student changing");
        return "students/editStudent";
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('users:write')")
    public String update(@ModelAttribute("student") Student student, @PathVariable("id") int id, Model model) {
        studentDAO.update(id,student);
        LOGGER.debug("student updated");
        LOGGER.debug(student);
        return "redirect:/students/" + id;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('users:write')")
    public String delete(@PathVariable("id") int id) {
        studentDAO.delete(id);
        LOGGER.debug("student with id " + id + "has been deleted");
        return "redirect:/students";
    }

    @GetMapping("/{id}/settings")
    public String settings(@PathVariable("id") int id,Model model) {
        Student student = studentDAO.showIndex(id);
        User user = userDAO.findByEmail(student.getEmail());
        model.addAttribute("user", user);
        model.addAttribute("student", student);
        LOGGER.debug("open settings menu");
        return "students/settings";
    }

    @PatchMapping("/{id}/settings")
    public String changeLogInfo(@ModelAttribute("user") User user, @PathVariable("id") int id, Model model) {
        userDAO.updateUser(id, user);
        LOGGER.debug("user" + id + " was updated");
        LOGGER.debug(user);
        return "redirect:/students/" + id;
    }

    @GetMapping("/{id}/group")
    public String studentGroup(@PathVariable("id") int id, Model model) {
        model.addAttribute("studentsGroup", studentDAO.getStudentsByGroup(id));
        model.addAttribute("student",studentDAO.showIndex(id));
        LOGGER.debug("student group page opened");
        return "students/studentsByGroup";
    }

    @GetMapping("/{id}/card")
    public String studentCard(@PathVariable("id") int id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = userDAO.findByEmail(currentPrincipalName);
        model.addAttribute("user", user);
        model.addAttribute("student",studentDAO.showIndex(id));
        model.addAttribute("group",groupDAO.getStudentGroup(id));
        model.addAttribute("faculty",facultyDAO.getStudentFaculty(id));
        LOGGER.debug("student card page opened");
        return "students/studentCard";
    }
}
