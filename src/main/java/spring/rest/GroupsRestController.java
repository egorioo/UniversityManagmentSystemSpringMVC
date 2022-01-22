package spring.rest;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring.dao.FacultyDAO;
import spring.dao.GroupDAO;
import spring.models.Group;
import spring.models.Subject;

import java.util.List;

@RestController
@RequestMapping("/groups")
public class GroupsRestController {
    private final GroupDAO groupDAO;

    public GroupsRestController(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    @RequestMapping(value = "/api", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @PreAuthorize("hasAuthority('users:write')")
    public List<Group> showAll() {
        return groupDAO.getAllGroups();
    }

    @RequestMapping(value = "/api", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @PreAuthorize("hasAuthority('users:write')")
    public void create(@RequestBody Group group) {
        groupDAO.createGroup(group);
    }

    @RequestMapping(value = "/{id}/api", method = RequestMethod.PATCH, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @PreAuthorize("hasAuthority('users:write')")
    public void update(@RequestBody Group group, @PathVariable int id) {
        groupDAO.updateGroup(id,group);
    }

    @RequestMapping(value = "/{id}/api", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @PreAuthorize("hasAuthority('users:write')")
    public void delete(@PathVariable int id) {
        groupDAO.deleteGroup(id);
    }
}