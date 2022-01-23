package spring.controllers;

import org.apache.log4j.Logger;
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
    private static final Logger LOGGER = Logger.getLogger(DisciplinesController.class);

    @Autowired
    public DisciplinesController(DisciplineDAO disciplineDAO) {
        this.disciplineDAO = disciplineDAO;
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('users:write')")
    public String showAll(Model model) {
        model.addAttribute("disciplines",disciplineDAO.getAllDisciplines());
        LOGGER.debug("show all disciplines");
        return "disciplines/allDisciplines";
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasAuthority('users:write')")
    public String editFaculty(Model model, @PathVariable int id) {
        model.addAttribute("discipline",disciplineDAO.getDisciplineIndex(id));
        LOGGER.debug("discipline editing window open");
        return "disciplines/editDiscipline";
    }

    @GetMapping("/new")
    @PreAuthorize("hasAuthority('users:write')")
    public String newFaculty(Model model) {
        Subject subject = new Subject();
        model.addAttribute("discipline", subject);
        LOGGER.debug("discipline adding window open");
        return "disciplines/newDiscipline";
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('users:write')")
    public String create(@ModelAttribute("discipline") Subject subject) {
        /*facultyDAO.addFaculty(faculty);*/
        disciplineDAO.createDiscipline(subject);
        LOGGER.debug("discipline has been added");
        LOGGER.debug(subject);
        return "redirect:/disciplines";
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('users:write')")
    public String update(@ModelAttribute("discipline") Subject subject, @PathVariable int id) {
        disciplineDAO.updateDiscipline(id,subject);
        LOGGER.debug("discipline has been changed");
        LOGGER.debug(subject);
        return "redirect:/disciplines";
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('users:write')")
    public String delete(@PathVariable int id) {
        disciplineDAO.deleteDiscipline(id);
        LOGGER.debug("discipline - " + id + " has been removed");
        return "redirect:/disciplines";
    }
}
