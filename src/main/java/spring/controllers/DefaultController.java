package spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.security.dao.UserDAO;
import spring.security.model.User;

@Controller
@RequestMapping("/")
public class DefaultController {

    private final UserDAO userDAO;

    @Autowired
    public DefaultController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @GetMapping()
    public String index(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = userDAO.findByEmail(currentPrincipalName);
        System.out.println("current " + user);
        if (userDAO.isStudent(user.getId())) {
            return "redirect:/students/" + user.getId();
        }
        else {
            return "students/allStudents";
        }
    }
}
