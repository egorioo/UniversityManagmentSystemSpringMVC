package spring.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring.dao.StudentDAO;
import spring.models.Student;

@Controller
@RequestMapping("/students")
public class StudentsController {
    private final StudentDAO studentDAO;

    @Autowired
    public StudentsController(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    @GetMapping()
    public String showAll(Model model) {
        model.addAttribute("students",studentDAO.showAll());
        return "students/allStudents";
    }

    @GetMapping("/{id}")
    public String studentIndex(@PathVariable("id") int id, Model model) {
        model.addAttribute("student",studentDAO.showIndex(id));
        return "students/student";
    }
    //
    @GetMapping("/new")
    public String newStudent(Model model) {
        model.addAttribute("student",new Student());
        return "students/newStudent";
    }

    @PostMapping()
    public String create(@ModelAttribute("student") Student student) {
        studentDAO.save(student);
        return "redirect:/students";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("student",studentDAO.showIndex(id));
        return "students/editStudent";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("student") Student student, @PathVariable("id") int id) {
        studentDAO.update(id,student);
        return "redirect:/students/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        studentDAO.delete(id);
        return "redirect:/students";
    }
}
