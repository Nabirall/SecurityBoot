package demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import demo.model.User;

@Controller
@RequestMapping(value = "/user")
public class UserController {



@GetMapping()
public String showUserInfo(ModelMap model, @AuthenticationPrincipal UserDetails userDetails) {
    User user = (User) userDetails;
    model.addAttribute("user", user);
    return "user";
}
}