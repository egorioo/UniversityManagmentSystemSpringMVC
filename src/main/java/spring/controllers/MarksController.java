package spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring.dao.StudentDAO;
import spring.models.Student;
import spring.models.Subject;

@Controller
@RequestMapping("/students/{id}/marks")
public class MarksController {
    private final StudentDAO studentDAO;

    @Autowired
    public MarksController(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    @GetMapping()
    public String studentGroup(@PathVariable int id, Model model) {
        model.addAttribute("student",studentDAO.showIndex(id));
        return "students/studentMarks";
    }

    @GetMapping("/edit/{subject}")
    public String editMark(@PathVariable("id") int id, @PathVariable String subject, Model model) {
        Student student = studentDAO.showIndex(id);
        model.addAttribute("student", student);
        model.addAttribute("subject", student.getSubjects().stream()
                .filter(subject1 -> subject1.getName().equals(subject))
                .findFirst()
                .orElse(null));
        return "students/editMarks";
    }

    @PatchMapping()
    public String updateMark(@ModelAttribute Subject subject, @PathVariable("id") int id) {
        studentDAO.updateMark(id,subject);
        return "redirect:/students/" + id + "/marks";
    }

    @GetMapping("/new")
    public String addNewMark(@PathVariable("id") int id, Model model) {
        Subject subject = new Subject();
        model.addAttribute("subject", subject);
        model.addAttribute("student", studentDAO.showIndex(id));
        return "students/addNewMark";
    }

    @PostMapping()
    public String createMark(@ModelAttribute("subject") Subject subject, @PathVariable int id) {
        System.out.println(subject);
        studentDAO.addNewMark(id, subject);
        return "redirect:/students/" + id + "/marks";
    }

    //update method
    @DeleteMapping()
    public String deleteDiscipline(@ModelAttribute("subject") Subject subject, @PathVariable int id) {
        studentDAO.deleteDiscipline(id, subject);
        return "redirect:/students/" + id + "/marks";
    }

    @GetMapping("/{subject}/delete")
    public String deleteDiscipline(@PathVariable("id") int id, @PathVariable String subject, Model model) {
        Student student = studentDAO.showIndex(id);
        Subject subject2 =  student.getSubjects().stream()
                .filter(subject1 -> subject1.getName().equals(subject))
                .findFirst()
                .orElse(null);
        studentDAO.deleteDiscipline(id,subject2);
        return "redirect:/students/" + id + "/marks";
    }

}
