package spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.dao.StudentDAO;
import spring.security.dao.UserDAO;
import spring.security.model.User;
import spring.service.GroupManager;

import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping("/schedule")
@PreAuthorize("hasAnyAuthority('users:read','users:write')")
public class ScheduleController {

    private final GroupManager groupManager;
    private final StudentDAO studentDAO;
    private final UserDAO userDAO;

    @Autowired
    public ScheduleController(GroupManager groupManager, StudentDAO studentDAO, UserDAO userDAO) {
        this.groupManager = groupManager;
        this.studentDAO = studentDAO;
        this.userDAO = userDAO;
    }

    @GetMapping
    public String chooseGroup(Model model) {
        model.addAttribute("groups", groupManager.getGroupsName());
        String group ="";
        model.addAttribute("group", group);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = userDAO.findByEmail(currentPrincipalName);
        model.addAttribute("student",studentDAO.showIndex(user.getId()));
        return "schedule/mainPage";
    }

    @GetMapping("/name")
    public String process(Model model, @RequestParam String group) throws UnsupportedEncodingException {
        return "redirect:" + groupManager.getGroup(group);
    }
}
