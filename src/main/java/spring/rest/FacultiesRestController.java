package spring.rest;


import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring.dao.FacultyDAO;
import spring.models.Faculty;

import java.util.List;

@RestController
@RequestMapping("/faculties")
public class FacultiesRestController {
    private final FacultyDAO facultyDAO;

    public FacultiesRestController(FacultyDAO facultyDAO) {
        this.facultyDAO = facultyDAO;
    }

    @RequestMapping(value = "/api", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @PreAuthorize("hasAuthority('users:write')")
    public List<Faculty> showAll(Model model) {
        return facultyDAO.getAllFaculties();
    }

    @RequestMapping(value = "/api", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @PreAuthorize("hasAuthority('users:write')")
    public void create(@ModelAttribute("faculty") Faculty faculty) {
        facultyDAO.createFaculty(faculty);
    }

    @RequestMapping(value = "/{id}/api", method = RequestMethod.PATCH, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @PreAuthorize("hasAuthority('users:write')")
    public void update(@RequestBody Faculty faculty, @PathVariable int id) {
        facultyDAO.updateFaculty(id,faculty);
    }

    @RequestMapping(value = "/{id}/api", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @PreAuthorize("hasAuthority('users:write')")
    public void delete(@PathVariable int id) {
        facultyDAO.deleteFaculty(id);
    }
}
