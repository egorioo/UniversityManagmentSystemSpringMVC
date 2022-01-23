package spring.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import spring.dao.*;
import spring.models.Student;
import spring.models.Subject;

@RestController
@RequestMapping("/students/{id}/marks")
public class MarksRestController {

    private final StudentDAO studentDAO;
    private final MarkDAO markDAO;

    @Autowired
    public MarksRestController(StudentDAO studentDAO, MarkDAO markDAO) {
        this.studentDAO = studentDAO;
        this.markDAO = markDAO;
    }

    @RequestMapping(value = "/api",method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    @PreAuthorize("hasAnyAuthority('users:read','users:write')")
    public Student studentMarks(@PathVariable int id) {
        return markDAO.initMarks(id);
    }

    @RequestMapping(value = "/api",method = RequestMethod.PATCH, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    @PreAuthorize("hasAuthority('users:write')")
    public void updateMark(@RequestBody Subject subject, @PathVariable("id") int id) {
        markDAO.updateMark(id,subject);
    }

    @RequestMapping(value = "/api",method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    @PreAuthorize("hasAuthority('users:write')")
    public void createMark(@ModelAttribute("subject") Subject subject, @PathVariable int id) {
        markDAO.addNewMark(id, subject);
    }

    @RequestMapping(value = "/api",method = RequestMethod.DELETE, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    @PreAuthorize("hasAuthority('users:write')")
    public void deleteDiscipline(@PathVariable("id") int id, @PathVariable String subjectStr) {
        Student student = studentDAO.showIndex(id);
        student = markDAO.initMarks(id);
        Subject subject =  student.getSubjects().stream()
                .filter(subject1 -> subject1.getName().equals(subjectStr))
                .findFirst()
                .orElse(null);
        markDAO.deleteMark(id,subject);
    }

}
