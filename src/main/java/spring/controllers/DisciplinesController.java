package spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring.dao.DisciplineDAO;
import spring.dao.FacultyDAO;
import spring.models.Faculty;
import spring.models.Subject;

@Controller
@RequestMapping("/disciplines")
public class DisciplinesController {
    private final DisciplineDAO disciplineDAO;

    @Autowired
    public DisciplinesController(DisciplineDAO disciplineDAO) {
        this.disciplineDAO = disciplineDAO;
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('users:write')")
    public String showAll(Model model) {
        model.addAttribute("disciplines",disciplineDAO.getAllDisciplines());
        return "disciplines/allDisciplines";
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasAuthority('users:write')")
    public String editFaculty(Model model, @PathVariable int id) {
        model.addAttribute("discipline",disciplineDAO.getDisciplineIndex(id));
        return "disciplines/editDiscipline";
    }

    @GetMapping("/new")
    @PreAuthorize("hasAuthority('users:write')")
    public String newFaculty(Model model) {
        Subject subject = new Subject();
        model.addAttribute("discipline", subject);
        return "disciplines/newDiscipline";
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('users:write')")
    public String create(@ModelAttribute("discipline") Subject subject) {
        /*facultyDAO.addFaculty(faculty);*/
        System.out.println(subject);
        disciplineDAO.createDiscipline(subject);
        return "redirect:/disciplines";
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('users:write')")
    public String update(@ModelAttribute("discipline") Subject subject, @PathVariable int id) {
        System.out.println(subject);
        disciplineDAO.updateDiscipline(id,subject);
        return "redirect:/disciplines";
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('users:write')")
    public String delete(@PathVariable int id) {
        disciplineDAO.deleteDiscipline(id);
        return "redirect:/disciplines";
    }
}
