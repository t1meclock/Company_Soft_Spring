package com.example.demo.controllers;

import com.example.demo.models.*;
import com.example.demo.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import com.example.demo.models.*;
import com.example.demo.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'CLIENT', 'STUFF')")
public class SiteController
{
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private PostRepository postRepository;

    public String currentUsername()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken))
        {
            String username = authentication.getName();

            return username;
        }

        return null;
    }

    @GetMapping("/")
    @PreAuthorize("isAuthenticated()")
    public String siteIndex(Model model)
    {
        return "index";
    }

    //region PROFILE
    @GetMapping("/profile/details")
    public String profileDetails(Model model)
    {
        User user = userRepository.findByUsername(currentUsername());

        model.addAttribute("user", user);

        return "profile/details";
    }

    @PostMapping("/profile/details")
    public String profileDetailsPost(@ModelAttribute ("user") @Valid User user,
                                  BindingResult bindingResult)
    {
        if(bindingResult.hasErrors()){
            return "profile/details";
        }

        return "redirect:/";
    }

    @GetMapping("/profile/edit/{id}")
    public String editProfile(@PathVariable(value = "id") long id, Model model)
    {
        if(!userRepository.existsById(id)){
            return "redirect:/";
        }

        Optional<User> user = userRepository.findById(id);
        model.addAttribute("user", user.get());

        return "profile/edit";
    }

    @PostMapping("/profile/edit/{id}")
    public String editProfilePost(@ModelAttribute ("user") @Valid User user,
                                  BindingResult bindingResult, Model model) // @RequestParam(value = "currentPassword") String currentPassword,
    {
        Boolean haveErrors = false;

        Optional<User> checkUser = userRepository.findById(user.getId());
        String currentPassword = checkUser.get().getPassword();

        String newPassword = user.getPassword();

        if(user.getSurname() != checkUser.get().getSurname())
        {
            if(user.getSurname().length() < 5 || user.getSurname().trim().contains(" "))
            {
                model.addAttribute("surname_errors", "Значение не должно быть меньше 5 символов или содержать пробелы.");
                haveErrors = true;
            }
        }

        if(user.getName() != checkUser.get().getName())
        {
            if(user.getName().length() < 5 || user.getName().trim().contains(" "))
            {
                model.addAttribute("name_errors", "Значение не должно быть меньше 5 символов или содержать пробелы.");
                haveErrors = true;
            }
        }

        if(user.getPatronymic() != checkUser.get().getPatronymic())
        {
            if(user.getPatronymic().length() < 5 || user.getPatronymic().trim().contains(" "))
            {
                model.addAttribute("patronymic_errors", "Значение не должно быть меньше 5 символов или содержать пробелы.");
                haveErrors = true;
            }
        }

        if (!passwordEncoder.matches(newPassword, currentPassword))
        {
            model.addAttribute("password_errors", "Введите Ваш текущий пароль.");
            haveErrors = true;
        }

        if (!haveErrors) {
            String debugString = "";

            // Редактируемые свойства
            user.setSurname(user.getSurname());
            user.setName(user.getName());
            user.setPatronymic(user.getPatronymic());

            // Статичные свойства
            user.setActive(checkUser.get().isActive());
            user.setUsername(checkUser.get().getUsername());
            user.setRoles(checkUser.get().getRoles());
            user.setPassword(passwordEncoder.encode(newPassword));
            user.setDateOfBirth(checkUser.get().getDateOfBirth());
            user.setClient(checkUser.get().getClient());
            user.setContact(checkUser.get().getContact());
            user.setStaffId(checkUser.get().getStaffId());

            userRepository.save(user);

            return "redirect:/profile/details";
        }

        return "profile/edit";
    }
    //endregion

    //region CONTACT
    @GetMapping("/contact/add/{id}")
    public String contactAdd(@PathVariable(value = "id") long id, Model model)
    {
        User user = userRepository.findById(id).get();

        Contact contact = new Contact();
        contact.setUser(user);
        model.addAttribute("contact", contact);

        return "contact/add";
    }

    @PostMapping("/contact/add")
    public String contactAddPost(@ModelAttribute("contact") @Valid Contact contact,
                                 BindingResult bindingResult,
                                 Model model)
    {
        Boolean haveErrors = false;

        if(contact.getEmail().length() < 5 || contact.getEmail().contains(" "))
        {
            model.addAttribute("email_errors", "Значение не должно быть меньше 5 символов или содержать пробелы.");
            haveErrors = true;
        }

        if(contactRepository.findByEmail(contact.getEmail()) != null &&
                !contactRepository.findByEmail(contact.getEmail()).getId().equals(contact.getId())){
            model.addAttribute("email_errors", "Данный email уже существует.");
            haveErrors = true;
        }

        if(contact.getPhoneNumber().length() < 5 || contact.getPhoneNumber().contains("-"))
        {
            model.addAttribute("phoneNumber_errors", "Значение не должно быть меньше 5 символов.");
            haveErrors = true;
        }

        if(contactRepository.findByPhoneNumber(contact.getPhoneNumber()) != null &&
                !contactRepository.findByPhoneNumber(contact.getPhoneNumber()).getId().equals(contact.getId())){
            model.addAttribute("phoneNumber_errors", "Данный телефон уже существует.");
            haveErrors = true;
        }

        if(bindingResult.hasErrors() || haveErrors) {
            return "contact/add";
        }

        contact.getUser().setContact(contact);
        contactRepository.save(contact);

        userRepository.save(contact.getUser());

        return "redirect:/profile/details";
    }
    //endregion

    //region CLIENT
    @GetMapping("/client/add/{id}")
    public String clientAdd(@PathVariable(value = "id") long id, Model model)
    {
        User user = userRepository.findById(id).get();

        Client client = new Client();
        client.setUser(user);

        model.addAttribute("client", client);

        return "client/add";
    }

    @PostMapping("/client/add")
    public String clientAddPost(@ModelAttribute("client") @Valid Client client,
                                 BindingResult bindingResult,
                                 Model model)
    {
        Boolean haveErrors = false;

        if(client.getOrgName().length() < 5)
        {
            model.addAttribute("orgName_errors", "Значение не должно быть меньше 5 символов или содержать пробелы.");
            haveErrors = true;
        }

        if(clientRepository.findByOrgName(client.getOrgName()) != null &&
                !clientRepository.findByOrgName(client.getOrgName()).getId().equals(client.getId())){
            model.addAttribute("orgName_errors", "Данная организация уже зарегистрирована.");
            haveErrors = true;
        }

        if(client.getOrgAddress().length() < 5)
        {
            model.addAttribute("orgAddress_errors", "Значение не должно быть меньше 5 символов или содержать пробелы.");
            haveErrors = true;
        }

        if(clientRepository.findByOrgAddress(client.getOrgAddress()) != null &&
                !clientRepository.findByOrgAddress(client.getOrgAddress()).getId().equals(client.getId())){
            model.addAttribute("orgAddress_errors", "По данному адресу зарегистрирована другая организация.");
            haveErrors = true;
        }

        if(bindingResult.hasErrors() || haveErrors) {
            return "client/add";
        }

        clientRepository.save(client);
        client.getUser().setClient(client);

        client.getUser().getRoles().add(Role.CLIENT);

        userRepository.save(client.getUser());

        return "redirect:/profile/details";
    }
    //endregion
}