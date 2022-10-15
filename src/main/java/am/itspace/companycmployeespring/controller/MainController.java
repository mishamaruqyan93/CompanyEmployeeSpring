package am.itspace.companycmployeespring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @GetMapping("/")
    public String mainPage() {
        return "index";
    }

    @GetMapping("accessDenied")
    public String accessDenied() {
        return "accessDenied";
    }


    @GetMapping("/loginSuccess")
    public String loginSuccess() {
        return "homePage";
    }

    @GetMapping("/loginPage")
    public String loginPage(@RequestParam(value = "error", required = false) String error, ModelMap modelMap) {
        if (error != null && error.equals("true")) {
            modelMap.addAttribute("error", "Invalid credentials");
        }
        return "loginPage";
    }
}
