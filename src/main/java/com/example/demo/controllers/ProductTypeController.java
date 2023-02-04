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
@RequestMapping("/product_type")
@PreAuthorize("hasAnyAuthority('STAFF')")
public class ProductTypeController
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
    @Autowired
    private TypeProductRepository typeProductRepository;

    @GetMapping
    public String productTypePanel(Model model)
    {
        List<TypeProduct> typeProductList = (List<TypeProduct>) typeProductRepository.findAll();
        boolean listNotEmpty = false;

        if(typeProductList.stream().count() > 0)
        {
            listNotEmpty = true;
        }

        model.addAttribute("listNotEmpty", listNotEmpty);
        model.addAttribute("typeProductList", typeProductList);

        return "staff/product_type/list";
    }

    @GetMapping("/add")
    public String typeProductAdd(Model model)
    {
        TypeProduct typeProduct = new TypeProduct();

        model.addAttribute("typeProduct", typeProduct);

        return "staff/product_type/add";
    }

    @PostMapping("/add")
    public String typeProductAddPost(@ModelAttribute("typeProduct") @Valid TypeProduct typeProduct,
                                     BindingResult bindingResult, Model model)
    {
        boolean haveErrors = false;

        TypeProduct typeProductFromDb = typeProductRepository.findByTypeProductName(typeProduct.getTypeProductName());

        if (typeProductFromDb != null)
        {
            model.addAttribute("typeProductName_errors", "Такой тип заказа уже есть.");
            haveErrors = true;
        }

        if(typeProduct.getTypeProductName().length() < 2)
        {
            model.addAttribute("typeProductName_errors", "Значение не должно быть меньше 2 символов.");
            haveErrors = true;
        }

        if(!haveErrors)
        {
            typeProductRepository.save(typeProduct);

            return "redirect:/product_type";
        }

        return "staff/product_type/add";
    }
}
