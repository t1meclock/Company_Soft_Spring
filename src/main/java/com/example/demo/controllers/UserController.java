package com.example.demo.controllers;

import com.example.demo.models.*;
import com.example.demo.repo.*;
import net.bytebuddy.implementation.auxiliary.AuxiliaryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/users")
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class UserController
{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FilterUserRepository filterUserRepository;

    @GetMapping
    public String userList(Model model)
    {
        List<User> users = new ArrayList<>();

        for (User user : userRepository.findAll())
        {
            if (user.getRoles().contains(Role.USER) || user.getRoles().contains(Role.CLIENT))
            {
                users.add(user);
            }
        }

        model.addAttribute("users", users);

        return "admin/users/list";
    }

    @GetMapping("/filter")
    public String blogFilterProfile(@RequestParam(defaultValue = "") String username,
                                    @RequestParam(required = false) boolean accurate_search,
                                    Model model)
    {
        if (!username.equals("")) {
            List<User> results = accurate_search ? filterUserRepository.findByUsername(username) : filterUserRepository.findByUsernameContains(username);
            model.addAttribute("results", results);
        }

        model.addAttribute("username", username);
        model.addAttribute("accurate_search", accurate_search);

        return "admin/users/filter";
    }

    @GetMapping("/{id}/edit")
    public String userEdit(@PathVariable(value = "id") long id, Model model)
    {
        Optional<User> user = userRepository.findById(id);
        ArrayList<User> res = new ArrayList<>();
        user.ifPresent(res::add);
        model.addAttribute("user", res);
        model.addAttribute("roles", Role.values());

        return "admin/users/edit";
    }

    @PostMapping
    public String userSave(@RequestParam String username,
                           @RequestParam(name="roles[]", required = false) String[] roles,
                           @RequestParam("userId") User user)
    {
        user.setUsername(username);
        user.getRoles().clear();

        if(roles != null)
        {
            Arrays.stream(roles).forEach(r->user.getRoles().add(Role.valueOf(r)));
        }

        userRepository.save(user);

        return "redirect:/admin";
    }
}