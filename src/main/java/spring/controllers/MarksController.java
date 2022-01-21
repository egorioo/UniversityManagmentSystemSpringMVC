package spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring.dao.*;
import spring.models.Student;
import spring.models.Subject;

@Controller
@RequestMapping("/students/{id}/marks")
public class MarksController {
    private final StudentDAO studentDAO;
    private final MarkDAO markDAO;
    private final FacultyDAO facultyDAO;
    private final GroupDAO groupDAO;
    private final DisciplineDAO disciplineDAO;

    @Autowired
    public MarksController(StudentDAO studentDAO, MarkDAO markDAO, FacultyDAO facultyDAO, GroupDAO groupDAO, DisciplineDAO disciplineDAO) {
        this.studentDAO = studentDAO;
        this.markDAO = markDAO;
        this.facultyDAO = facultyDAO;
        this.groupDAO = groupDAO;
        this.disciplineDAO = disciplineDAO;
    }

    @GetMapping()
    @PreAuthorize("hasAnyAuthority('users:read','users:write')")
    public String studentMarks(@PathVariable int id, Model model) {
        Student student = markDAO.initMarks(id);
        model.addAttribute("student",student);
        model.addAttribute("group",groupDAO.getStudentGroup(id));
        model.addAttribute("faculty",facultyDAO.getStudentFaculty(id));

        return "marks/studentMarks";
    }

    @GetMapping("/edit/{subject}")
    @PreAuthorize("hasAuthority('users:write')")
    public String editMark(@PathVariable("id") int id, @PathVariable String subject, Model model) {
        Student student = studentDAO.showIndex(id);
        student = markDAO.initMarks(id);
        model.addAttribute("student", student);
        model.addAttribute("subject", student.getSubjects().stream()
                .filter(subject1 -> subject1.getName().equals(subject))
                .findFirst()
                .orElse(null));
        return "marks/editMarks";
    }

    @PatchMapping()
    @PreAuthorize("hasAuthority('users:write')")
    public String updateMark(@ModelAttribute Subject subject, @PathVariable("id") int id) {
        markDAO.updateMark(id,subject);
        return "redirect:/students/" + id + "/marks";
    }

    @GetMapping("/new")
    @PreAuthorize("hasAuthority('users:write')")
    public String addNewMark(@PathVariable("id") int id, Model model) {
        Subject subject = new Subject();
        model.addAttribute("subject", subject);
        model.addAttribute("subjects",disciplineDAO.getAllDisciplines());
        model.addAttribute("student", studentDAO.showIndex(id));
        return "marks/addNewMark";
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('users:write')")
    public String createMark(@ModelAttribute("subject") Subject subject, @PathVariable int id) {
        System.out.println(subject);
        markDAO.addNewMark(id, subject);
        return "redirect:/students/" + id + "/marks";
    }

    /*@GetMapping("/{subjectStr}/delete")
    public String deleteDiscipline(@PathVariable("id") int id, @PathVariable String subjectStr, Model model) {
        Student student = studentDAO.showIndex(id);
        student = markDAO.initMarks(id);
        Subject subject =  student.getSubjects().stream()
                .filter(subject1 -> subject1.getName().equals(subjectStr))
                .findFirst()
                .orElse(null);
        markDAO.deleteDiscipline(id,subject);
        return "redirect:/students/" + id + "/marks";
    }*/

    //update method
    @DeleteMapping("/{subjectStr}/delete")
    @PreAuthorize("hasAuthority('users:write')")
    public String deleteDiscipline(@PathVariable("id") int id, @PathVariable String subjectStr, Model model) {
        Student student = studentDAO.showIndex(id);
        student = markDAO.initMarks(id);
        Subject subject =  student.getSubjects().stream()
                .filter(subject1 -> subject1.getName().equals(subjectStr))
                .findFirst()
                .orElse(null);
        markDAO.deleteMark(id,subject);
        return "redirect:/students/" + id + "/marks";
    }

}
