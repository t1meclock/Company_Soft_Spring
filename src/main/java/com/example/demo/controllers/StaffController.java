package com.example.demo.controllers;

import com.example.demo.models.*;
import com.example.demo.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("/staff")
@PreAuthorize("hasAnyAuthority('STAFF')")
public class StaffController
{
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ContactRepository contactRepository;

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

    @GetMapping
    public String staffPanel(Model model)
    {
        User user = userRepository.findByUsername(currentUsername());

        if(user.getStaffId() != 0)
        {
            Staff staff = staffRepository.findStaffById(user.getStaffId());
            Post post = postRepository.findPostById(staff.getPostId());

            model.addAttribute("post", post);
        }

        return "staff/index";
    }
}
