package spring.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring.dao.FacultyDAO;
import spring.dao.GroupDAO;
import spring.dao.StudentDAO;
import spring.models.Faculty;
import spring.models.Student;

@Controller
@RequestMapping("/students")
public class StudentsController {
    private final StudentDAO studentDAO;
    private final FacultyDAO facultyDAO;
    private final GroupDAO groupDAO;

    @Autowired
    public StudentsController(StudentDAO studentDAO, FacultyDAO facultyDAO, GroupDAO groupDAO) {
        this.studentDAO = studentDAO;
        this.facultyDAO = facultyDAO;
        this.groupDAO = groupDAO;
    }

    //ok
    @GetMapping()
    @PreAuthorize("hasAuthority('developers:read')")
    public String showAll(Model model) {
        model.addAttribute("students",studentDAO.showAll());
        return "students/allStudents";
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('developers:read')")
    public String studentIndex(@PathVariable("id") int id, Model model) {
        model.addAttribute("student",studentDAO.showIndex(id));
        return "students/student";
    }
    //ok
    @GetMapping("/new")
    @PreAuthorize("hasAuthority('developers:write')")
    public String newStudent(Model model) {
        model.addAttribute("student",new Student());
        model.addAttribute("faculties", facultyDAO.getAllFaculties());
        model.addAttribute("groups", groupDAO.getAllGroups());
        return "students/newStudent";
    }
    //ok

    @PostMapping()
    @PreAuthorize("hasAuthority('developers:write')")
    public String create(@ModelAttribute("student") Student student) {
        System.out.println(student);
        studentDAO.save(student);
        return "redirect:/students";
    }
    //ok

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasAuthority('developers:write')")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("student",studentDAO.showIndex(id));
        model.addAttribute("faculties", facultyDAO.getAllFaculties());
        model.addAttribute("groups", groupDAO.getAllGroups());
        return "students/editStudent";
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('developers:write')")
    public String update(@ModelAttribute("student") Student student, @PathVariable("id") int id) {
        studentDAO.update(id,student);
        return "redirect:/students/" + id;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('developers:write')")
    public String delete(@PathVariable("id") int id) {
        studentDAO.delete(id);
        return "redirect:/students";
    }
}
