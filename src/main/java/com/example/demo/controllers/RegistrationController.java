package com.example.demo.controllers;

import com.example.demo.models.*;
import com.example.demo.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/registration")
    public String registration(User user)
    {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Model model)
    {
        Boolean haveErrors = false;

        String username = user.getUsername().trim();
        String password = user.getPassword().trim();
        String surname = user.getSurname().trim();
        String name = user.getName().trim();
        String patronymic = user.getPatronymic().trim();
        String dateOfBirth = user.getDateOfBirth();

        User userFromDb = userRepository.findByUsername(user.getUsername());

        if (userFromDb != null)
        {
            model.addAttribute("message", "Пользователь с таким логином уже зарегистрирован.");
            haveErrors = true;
        }

        // ===================================================
        if (username.length() < 5 || username.trim().contains(" "))
        {
            model.addAttribute("username_errors", "Значение не должно быть меньше 5 символов или содержать пробелы.");
            haveErrors = true;
        }

        // ===================================================
        if (password.length() < 5 || password.trim().contains(" "))
        {
            model.addAttribute("password_errors", "Значение не должно быть меньше 5 символов или содержать пробелы.");
            haveErrors = true;
        }

        // ===================================================
        if (surname.length() < 5 || surname.trim().contains(" "))
        {
            model.addAttribute("surname_errors", "Значение не должно быть меньше 5 символов или содержать пробелы.");
            haveErrors = true;
        }

        // ===================================================
        if (name.length() < 5 || name.trim().contains(" "))
        {
            model.addAttribute("name_errors", "Значение не должно быть меньше 5 символов или содержать пробелы.");
            haveErrors = true;
        }

        // ===================================================
        if (patronymic.length() < 5 || patronymic.trim().contains(" "))
        {
            model.addAttribute("patronymic_errors", "Значение не должно быть меньше 5 символов или содержать пробелы.");
            haveErrors = true;
        }

        // ===================================================
        if (dateOfBirth.length() < 5 || dateOfBirth.trim().contains(" "))
        {
            model.addAttribute("dateOfBirth_errors", "Выберите дату рождения.");
            haveErrors = true;
        }
        // ===================================================

        if (!haveErrors)
        {
            user.setActive(true);
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setSurname(surname);
            user.setName(name);
            user.setPatronymic(patronymic);
            user.setRoles(Collections.singleton(Role.USER));

            userRepository.save(user);

            return "redirect:/login";
        }

        return "registration";
    }
}