package spring.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import spring.dao.DisciplineDAO;
import spring.models.Subject;

import java.util.List;

@RestController
@RequestMapping("/disciplines")
public class DisciplinesRestController {

    private final DisciplineDAO disciplineDAO;

    @Autowired
    public DisciplinesRestController(DisciplineDAO disciplineDAO) {
        this.disciplineDAO = disciplineDAO;
    }

    @RequestMapping(value = "/api", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @PreAuthorize("hasAuthority('users:write')")
    public List<Subject> showAll() {
        return disciplineDAO.getAllDisciplines();
    }

    @RequestMapping(value = "/api", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @PreAuthorize("hasAuthority('users:write')")
    public void create(@RequestBody Subject subject) {
        disciplineDAO.createDiscipline(subject);
    }

    @RequestMapping(value = "/{id}/api", method = RequestMethod.PATCH, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @PreAuthorize("hasAuthority('users:write')")
    public void update(@RequestBody Subject subject, @PathVariable int id) {
        disciplineDAO.updateDiscipline(id, subject);
    }

    @RequestMapping(value = "/{id}/api", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @PreAuthorize("hasAuthority('users:write')")
    public void delete(@PathVariable int id) {
        disciplineDAO.deleteDiscipline(id);
    }
}