package spring.controllers;

import org.apache.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring.dao.FacultyDAO;
import spring.models.Faculty;

@Controller
@RequestMapping("/faculties")
public class FacultiesController {
    private final FacultyDAO facultyDAO;
    private static final Logger LOGGER = Logger.getLogger(FacultiesController.class);

    public FacultiesController(FacultyDAO facultyDAO) {
        this.facultyDAO = facultyDAO;
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('users:write')")
    public String showAll(Model model) {
        model.addAttribute("faculties",facultyDAO.getAllFaculties());
        LOGGER.debug("show all faculties");
        return "faculties/allFaculties";
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasAuthority('users:write')")
    public String editFaculty(Model model, @PathVariable int id) {
        model.addAttribute("faculty",facultyDAO.getFacultyIndex(id));
        LOGGER.debug("faculty editing window open");
        return "faculties/editFaculty";
    }

    @GetMapping("/new")
    @PreAuthorize("hasAuthority('users:write')")
    public String newFaculty(Model model) {
        Faculty faculty = new Faculty();
        model.addAttribute("faculty",faculty);
        LOGGER.debug("faculty adding window open");
        return "faculties/newFaculty";
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('users:write')")
    public String create(@ModelAttribute("faculty") Faculty faculty) {
        facultyDAO.createFaculty(faculty);
        LOGGER.debug("faculty has been added");
        LOGGER.debug(faculty);
        return "redirect:/faculties";
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('users:write')")
    public String update(@ModelAttribute("faculty") Faculty faculty, @PathVariable int id) {
        facultyDAO.updateFaculty(id,faculty);
        LOGGER.debug("faculty has been changed");
        LOGGER.debug(faculty);
        return "redirect:/faculties";
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('users:write')")
    public String delete(@PathVariable int id) {
        facultyDAO.deleteFaculty(id);
        LOGGER.debug("faculty - " + id + " has been removed");
        return "redirect:/faculties";
    }
}
