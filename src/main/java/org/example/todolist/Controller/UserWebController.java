package org.example.todolist.Controller;

import lombok.RequiredArgsConstructor;
import org.example.todolist.Entity.User;
import org.example.todolist.Service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/web")
@RequiredArgsConstructor
public class UserWebController {
    private final UserService userService;
    // --- QUẢN LÝ USER (CHỈ ADMIN) ---
    @GetMapping("/admin/users")
    @PreAuthorize("hasRole('ADMIN')")
    public String listUsers(Model model) {
        List<User> users = userService.getAll();
        model.addAttribute("users", users);
        return "admin/user-list";
    }

    @GetMapping("/admin/users/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/web/admin/users";
    }

    @GetMapping("/admin/users/form")
    @PreAuthorize("hasRole('ADMIN')")
    public String showUserForm(@RequestParam(required = false) Long id, Model model) {
        User user;
        if (id != null) {
            user = userService.getById(id);
            if (user == null) {
                return "redirect:/web/admin/users";
            }
        } else {
            user = new User();
        }
        model.addAttribute("user", user);
        model.addAttribute("isEditing", id != null);
        return "admin/user-form";
    }

    @PostMapping("/admin/users/save")
    @PreAuthorize("hasRole('ADMIN')")
    public String saveUser(@ModelAttribute("user") User user) {
        if (user.getId() == null) {
            userService.createUser(user);
        } else {
            userService.updateUser(user, user.getId());
        }
        return "redirect:/web/admin/users";
    }
}
