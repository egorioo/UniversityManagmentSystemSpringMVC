package spring.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import spring.dao.FacultyDAO;
import spring.dao.GroupDAO;
import spring.dao.StudentDAO;
import spring.models.Student;
import spring.security.dao.UserDAO;
import spring.security.model.User;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentsRestController {
    private final StudentDAO studentDAO;
    private final FacultyDAO facultyDAO;
    private final GroupDAO groupDAO;
    private final UserDAO userDAO;

    @Autowired
    public StudentsRestController(StudentDAO studentDAO, FacultyDAO facultyDAO, GroupDAO groupDAO, UserDAO userDAO) {
        this.studentDAO = studentDAO;
        this.facultyDAO = facultyDAO;
        this.groupDAO = groupDAO;
        this.userDAO = userDAO;
    }

    @RequestMapping(value = "/api",method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    @PreAuthorize("hasAuthority('users:write')")
    public List<Student> showAll() {
        return studentDAO.showAll();
    }

    @RequestMapping(value = "/{id}/api",method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    @PreAuthorize("hasAnyAuthority('users:read','users:write')")
    public Student studentIndex(@PathVariable("id") int id) {
        return studentDAO.showIndex(id);
    }

    @RequestMapping(value = "/api",method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    @PreAuthorize("hasAuthority('users:write')")
    public void create(@RequestBody Student student) {
        studentDAO.save(student);
    }

    @RequestMapping(value = "{id}/api",method = RequestMethod.PATCH, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    @PreAuthorize("hasAuthority('users:write')")
    public void update(@RequestBody Student student, @PathVariable("id") int id, Model model) {
        studentDAO.update(id,student);
    }

    @RequestMapping(value = "{id}/api",method = RequestMethod.DELETE, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    @PreAuthorize("hasAuthority('users:write')")
    public void delete(@PathVariable("id") int id) {
        studentDAO.delete(id);
    }
}
