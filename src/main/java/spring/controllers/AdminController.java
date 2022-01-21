package spring.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import spring.dao.AdminDAO;
import spring.security.dao.UserDAO;
import spring.security.model.User;

@Controller
public class AdminController {
    /*private final UserDAO userDAO;
    private final AdminDAO adminDAO;

    public AdminController(UserDAO userDAO, AdminDAO adminDAO) {
        this.userDAO = userDAO;
        this.adminDAO = adminDAO;
    }

    @GetMapping("/settings")
    @PreAuthorize("hasAnyAuthority('users:read','users:write')")
    public String settings(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = userDAO.findByEmail(currentPrincipalName);
        model.addAttribute("user", user);
        return "adminSettings";
    }

    @PatchMapping("/settings/{id}")
    public String changeLogInfo(@ModelAttribute("user") User user, @PathVariable("id") int id) {
        userDAO.updateAdmin(id,user);
        return "redirect:/";
    }*/
}
