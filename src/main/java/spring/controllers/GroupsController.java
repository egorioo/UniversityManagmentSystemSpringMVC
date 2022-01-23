package spring.controllers;

import org.apache.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring.dao.FacultyDAO;
import spring.dao.GroupDAO;
import spring.models.Group;

@Component
@RequestMapping("/groups")
public class GroupsController {
    private final GroupDAO groupDAO;
    private final FacultyDAO facultyDAO;
    private static final Logger LOGGER = Logger.getLogger(GroupsController.class);

    public GroupsController(GroupDAO groupDAO, FacultyDAO facultyDAO) {
        this.groupDAO = groupDAO;
        this.facultyDAO = facultyDAO;
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('users:write')")
    public String showAll(Model model) {
        model.addAttribute("groups",groupDAO.getAllGroups());
        LOGGER.debug("show all groups");
        return "groups/allGroups";
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasAuthority('users:write')")
    public String editFaculty(Model model, @PathVariable int id) {
        model.addAttribute("group",groupDAO.getGroupIndex(id));
        model.addAttribute("faculties", facultyDAO.getAllFaculties());
        LOGGER.debug("group editing window open");
        return "groups/editGroup";
    }

    @GetMapping("/new")
    @PreAuthorize("hasAuthority('users:write')")
    public String newFaculty(Model model) {
        Group group = new Group();
        model.addAttribute("group",group);
        model.addAttribute("faculties", facultyDAO.getAllFaculties());
        LOGGER.debug("group adding window open");
        return "groups/newGroup";
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('users:write')")
    public String create(@ModelAttribute("group") Group group) {
        groupDAO.createGroup(group);
        LOGGER.debug("group has been added");
        LOGGER.debug(group);
        return "redirect:/groups";
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('users:write')")
    public String update(@ModelAttribute("group") Group group, @PathVariable int id) {
        groupDAO.updateGroup(id,group);
        LOGGER.debug("group has been changed");
        LOGGER.debug(group);
        return "redirect:/groups";
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('users:write')")
    public String delete(@PathVariable int id) {
        groupDAO.deleteGroup(id);
        LOGGER.debug("group - " + id + " has been removed");
        return "redirect:/groups";
    }
}
