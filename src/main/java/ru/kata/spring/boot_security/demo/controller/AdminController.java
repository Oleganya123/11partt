package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import jakarta.validation.Valid;
import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", userService.getAllRoles());
        return "admin";
    }

    @PostMapping("/users/add")
    public String addUser(@Valid @ModelAttribute("user") User user,
                          BindingResult bindingResult,
                          @RequestParam List<Long> roleIds,
                          Model model) {

        if (bindingResult.hasErrors() || roleIds == null || roleIds.isEmpty()) {
            if (roleIds == null || roleIds.isEmpty()) {
                bindingResult.rejectValue("roles", "error.user", "Выберите хотя бы одну роль");
            }
            model.addAttribute("users", userService.getAllUsers());
            model.addAttribute("allRoles", userService.getAllRoles());
            return "admin";
        }

        userService.addUser(user, roleIds);
        return "redirect:/admin/users";
    }

    @PostMapping("/users/update")
    public String updateUser(@RequestParam Long id,
                             @RequestParam String name,
                             @RequestParam String lastName,
                             @RequestParam int age,
                             @RequestParam String email,
                             @RequestParam(required = false) String password,
                             @RequestParam(required = false) List<Long> roleIds) {

        User user = userService.getUserById(id);
        user.setName(name);
        user.setLastName(lastName);
        user.setAge(age);
        user.setEmail(email);

        userService.updateUser(user, password, roleIds);
        return "redirect:/admin/users";
    }

    @PostMapping("/users/delete")
    public String deleteUser(@RequestParam Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }
}
