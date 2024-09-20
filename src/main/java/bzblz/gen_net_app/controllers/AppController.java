package bzblz.gen_net_app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class AppController {
    @GetMapping("")
    public String index() {
        return "index";
    }
    @GetMapping("/page")
    public String page() {return "redirect:/page/dashboard";}
    @GetMapping("/page/dashboard")
    public String pageDashboard() {
        return "page/dashboard";
    }
    @GetMapping("/page/projects")
    public String pageProjects() {
        return "page/projects";
    }
    @GetMapping("/page/persons")
    public String pagePersons() {
        return "page/persons";
    }
    @GetMapping("/page/about")
    public String pageAbout(Model model) {
        model.addAttribute("route", "/about");
        return "page/about";
    }
    @GetMapping("/page/metawidget")
    public String metawidget() {
        return "page/metawidget";
    }
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

}
