package com.example.demo.controllers;

import com.example.demo.models.*;
import com.example.demo.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

@Controller
@RequestMapping("/order")
@PreAuthorize("hasAnyAuthority('CLIENT')")
public class OrderController
{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private TypeProductRepository typeProductRepository;

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
    public String orderIndex(Model model)
    {
        User user = userRepository.findByUsername(currentUsername());
        Client client = clientRepository.findClientById(user.getClient().getId());

        model.addAttribute("client", client);

        return "order/index";
    }

    //region ORDER
    @GetMapping("/add/{id}")
    public String addOrder(@PathVariable(value = "id") long id, Model model)
    {
        User user = userRepository.findByUsername(currentUsername());
        Client client = clientRepository.findClientById(user.getClient().getId());

        Order order = new Order();
        order.setClient(client);
        model.addAttribute("order", order);
        model.addAttribute("typeProductList", typeProductRepository.findAll());

        return "order/add";
    }

    @PostMapping("/add")
    public String addOrderPost(@ModelAttribute("order") @Valid Order order,
                               BindingResult bindingResult,
                               Model model)
    {
        model.addAttribute("typeProductList", typeProductRepository.findAll());

        boolean haveErrors = false;

        if(order.getProductName().length() < 5)
        {
            model.addAttribute("productName_errors", "Значение не должно быть меньше 5 символов.");
            haveErrors = true;
        }

        if (order.getTypeProduct() == null)
        {
            model.addAttribute("typeProduct_errors", "Необходимо выбрать тип заказа.");
            haveErrors = true;
        }

        if (order.getDeadline().length() < 5)
        {
            model.addAttribute("deadline_errors", "Выберите дату дедлайна.");
            haveErrors = true;
        }

        if(!haveErrors)
        {
            order.getClient().setOrder(order);
            order.setTypeProductName(order.getTypeProduct().getTypeProductName());
            orderRepository.save(order);

            clientRepository.save(order.getClient());

            return "redirect:/order";
        }

        return "order/add";
    }

    @GetMapping("/details/{id}")
    public String orderDetails(@PathVariable(value = "id") Long id, Model model)
    {
        Order order = orderRepository.findOrderByClientId(id);

        if(order != null)
        {
            model.addAttribute("order", order);
        }

        return "order/details";
    }
    //endregion
}
