package demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import demo.model.Role;
import demo.model.User;
import demo.service.UserServiceImpl;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
public final UserServiceImpl userServiceImpl;

@Autowired
public AdminController(UserServiceImpl userServiceImpl) {
    this.userServiceImpl = userServiceImpl;
}

@GetMapping("/list")
public String userList(Model model) {
    model.addAttribute("users", userServiceImpl.allUsers());
    return "list";
}

@DeleteMapping("/{id}")
public String delete(@PathVariable("id") Long id) {
    userServiceImpl.deleteUser(id);
    return "redirect:/admin/list";
}
@GetMapping("/{id}/edit")
public String edit(Model model, @PathVariable("id") Long id) {
    model.addAttribute("user", userServiceImpl.findUserById(id));
    List<Role> roles = userServiceImpl.listRole();
    model.addAttribute("listRoles",roles);
    return "/edit";
}

@PatchMapping("/{id}")
public String update(@ModelAttribute("user") User user, BindingResult bindingResult,
                     @PathVariable("id") Long id) {
    if (bindingResult.hasErrors()) {
        return "/edit";
    }

    userServiceImpl.updateUser(user, id);
    return "redirect:/admin/list";
}

@GetMapping("/")
public String showUserById(@RequestParam("id") Long id, ModelMap model) {
    model.addAttribute("user", userServiceImpl.findUserById(id));
    return "showById";
}

}