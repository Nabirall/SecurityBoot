package demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import demo.model.User;
import demo.service.UserServiceImpl;

@Controller
@RequestMapping("/register")
public class RegistrationController {

private final UserServiceImpl userServiceImpl;

public RegistrationController(
        UserServiceImpl userServiceImpl) {
    this.userServiceImpl = userServiceImpl;
}

@GetMapping()
public String registerForm(Model model) {
    User user = new User();
    model.addAttribute("user", new User());
    return "registration";
}

@PostMapping()
public String processRegistration(@ModelAttribute("user") User user) {
    userServiceImpl.saveUser(user);
    return "redirect:/login";
}

}
