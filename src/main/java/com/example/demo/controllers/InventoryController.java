package com.example.demo.controllers;

import com.example.demo.models.*;
import com.example.demo.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("/inventory")
@PreAuthorize("hasAnyAuthority('STAFF')")
public class InventoryController
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
    @Autowired
    private InventoryRepository inventoryRepository;

    @GetMapping
    public String inventoryPanel(Model model)
    {
        List<Inventory> inventoryList = (List<Inventory>) inventoryRepository.findAll();
        boolean listNotEmpty = false;

        if(inventoryList.stream().count() > 0)
        {
            listNotEmpty = true;
        }

        model.addAttribute("listNotEmpty", listNotEmpty);
        model.addAttribute("inventoryList", inventoryList);

        return "staff/inventory/list";
    }

    @GetMapping("/add")
    public String addInventory(Model model)
    {
        Inventory inventory = new Inventory();

        model.addAttribute("inventory", inventory);

        return "staff/inventory/add";
    }

    @PostMapping("/add")
    public String addInventoryPost(@ModelAttribute("inventory") @Valid Inventory inventory,
                                   BindingResult bindingResult, Model model)
    {
        boolean haveErrors = false;

        Inventory inventoryFromDb = inventoryRepository.findByInventoryNumber(inventory.getInventoryNumber());

        if (inventoryFromDb != null)
        {
            model.addAttribute("inventoryNumber_errors", "Такой инвентарный номер уже есть.");
            haveErrors = true;
        }

        if(inventory.getInventoryName().length() < 5)
        {
            model.addAttribute("inventoryName_errors", "Значение не должно быть меньше 5 символов.");
            haveErrors = true;
        }

        if(inventory.getInventoryNumber().length() < 5 || inventory.getInventoryNumber().trim().contains(" "))
        {
            model.addAttribute("inventoryNumber_errors", "Значение не должно быть меньше 5 символов или содержать пробелы.");
            haveErrors = true;
        }

        if(!haveErrors)
        {
            inventoryRepository.save(inventory);

            return "redirect:/inventory";
        }

        return "staff/inventory/add";
    }
}