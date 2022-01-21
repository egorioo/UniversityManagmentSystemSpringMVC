package spring.controllers;

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

    public FacultiesController(FacultyDAO facultyDAO) {
        this.facultyDAO = facultyDAO;
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('users:write')")
    public String showAll(Model model) {
        model.addAttribute("faculties",facultyDAO.getAllFaculties());
        return "faculties/allFaculties";
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasAuthority('users:write')")
    public String editFaculty(Model model, @PathVariable int id) {
        model.addAttribute("faculty",facultyDAO.getFacultyIndex(id));
        return "faculties/editFaculty";
    }

    @GetMapping("/new")
    @PreAuthorize("hasAuthority('users:write')")
    public String newFaculty(Model model) {
        Faculty faculty = new Faculty();
        model.addAttribute("faculty",faculty);
        return "faculties/newFaculty";
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('users:write')")
    public String create(@ModelAttribute("faculty") Faculty faculty) {
        /*facultyDAO.addFaculty(faculty);*/
        System.out.println(faculty);
        facultyDAO.createFaculty(faculty);
        return "redirect:/faculties";
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('users:write')")
    public String update(@ModelAttribute("faculty") Faculty faculty, @PathVariable int id) {
        facultyDAO.updateFaculty(id,faculty);
        System.out.println(faculty);
        return "redirect:/faculties";
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('users:write')")
    public String delete(@PathVariable int id) {
        facultyDAO.deleteFaculty(id);
        return "redirect:/faculties";
    }
}
