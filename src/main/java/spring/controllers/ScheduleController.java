package spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.service.GroupManager;

import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {

    private final GroupManager groupManager;

    @Autowired
    public ScheduleController(GroupManager groupManager) {
        this.groupManager = groupManager;
    }

    @GetMapping
    public String chooseGroup(Model model) {
        model.addAttribute("groups", groupManager.getGroupsName());
        String group ="";
        model.addAttribute("group", group);
        return "schedule/mainPage";
    }

    @GetMapping("/name")
    public String process(Model model, @RequestParam String group) throws UnsupportedEncodingException {
        return "redirect:" + groupManager.getGroup(group);
    }
}
