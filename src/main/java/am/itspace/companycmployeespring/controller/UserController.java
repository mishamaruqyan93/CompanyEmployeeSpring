package am.itspace.companycmployeespring.controller;

import am.itspace.companycmployeespring.entity.User;
import am.itspace.companycmployeespring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/users")
    public String users(ModelMap modelMap) {
        List<User> usersList = userService.findAllUsers();
        modelMap.addAttribute("users", usersList);
        return "users";
    }

    @GetMapping("/users/add")
    public String addUser() {
        return "addUser";
    }

    @PostMapping("/users/add")
    public String add(@ModelAttribute User user, ModelMap modelMap) {
        Optional<User> byUserEmail = userService.findByUserEmail(user.getEmail());
        if (byUserEmail.isPresent()) {
            modelMap.addAttribute("errorMassageEmail", "Email already in use");
            return "addUser";
        }
        userService.saveUser(user);
        return "redirect:/users";
    }

    @GetMapping("/users/delete")
    public String deleteUser(@RequestParam("id") int id) {
        userService.userDelete(id);
        return "redirect:/users";
    }
}
