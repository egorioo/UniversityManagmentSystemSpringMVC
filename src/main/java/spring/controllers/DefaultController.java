package spring.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring.dao.AdminDAO;
import spring.models.Student;
import spring.security.dao.UserDAO;
import spring.security.model.User;

@Controller
@RequestMapping("/")
public class DefaultController {

    private final UserDAO userDAO;
    private final AdminDAO adminDAO;
    private static final Logger LOGGER = Logger.getLogger(DefaultController.class);

    @Autowired
    public DefaultController(UserDAO userDAO, AdminDAO adminDAO) {
        this.userDAO = userDAO;
        this.adminDAO = adminDAO;
    }

    @GetMapping()
    @PreAuthorize("hasAnyAuthority('users:read','users:write')")
    public String index(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = userDAO.findByEmail(currentPrincipalName);
        if (userDAO.isStudent(user.getId())) {
            LOGGER.debug("student logged in");
            LOGGER.debug(user);
            LOGGER.debug("redirecting to /students/id");
            return "redirect:/students/" + user.getId();
        }
        else {
            model.addAttribute("admin",adminDAO.getAdminById(user.getId()));
            LOGGER.debug("admin logged in");
            LOGGER.debug(user);
            return "helloAdmin";
        }
    }

}
