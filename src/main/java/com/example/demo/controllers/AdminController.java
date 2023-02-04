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
@RequestMapping("/admin")
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class AdminController
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

    @GetMapping
    public String adminPanel(Model model)
    {
        return "admin/index";
    }

    @GetMapping("/orders")
    public String orderList(Model model)
    {
        model.addAttribute("orders", orderRepository.findAll());

        return "admin/orders/list";
    }

    @GetMapping("/departments")
    public String depList(Model model)
    {
        model.addAttribute("departments", departmentRepository.findAll());

        return "admin/departments/list";
    }

    @GetMapping("/departments/add")
    public String addDep(Model model)
    {
        Department department = new Department();
        model.addAttribute("department", department);

        return "admin/departments/add";
    }

    @PostMapping("/departments/add")
    public String addOrderPost(@ModelAttribute("department") @Valid Department department,
                               BindingResult bindingResult,
                               Model model)
    {
        boolean haveErrors = false;

        if (department.getDepartmentName().length() < 5)
        {
            model.addAttribute("departmentName_errors", "Значение не должно быть меньше 5 символов.");
            haveErrors = true;
        }

        if(!haveErrors)
        {
            departmentRepository.save(department);

            return "redirect:/admin/departments";
        }

        return "admin/departments/add";
    }

    //

    @GetMapping("/posts")
    public String postList(Model model)
    {
        model.addAttribute("posts", postRepository.findAll());
        model.addAttribute("departments", departmentRepository.findAll());

        return "admin/posts/list";
    }

    @GetMapping("/posts/add")
    public String addPost(Model model)
    {
        Post post = new Post();
        model.addAttribute("post", post);
        model.addAttribute("departments", departmentRepository.findAll());

        return "admin/posts/add";
    }

    @PostMapping("/posts/add")
    public String addPostPost(@ModelAttribute("post") @Valid Post post,
                               BindingResult bindingResult,
                               Model model)
    {
        boolean haveErrors = false;

        if (post.getPostName().length() < 5)
        {
            model.addAttribute("postName_errors", "Значение не должно быть меньше 5 символов.");
            haveErrors = true;
        }

        if (post.getSalary() < 0)
        {
            model.addAttribute("salary_errors", "Значение не может быть отрицательным.");
            haveErrors = true;
        }

        if (post.getDepartments() == null)
        {
            model.addAttribute("department_errors", "Необходимо выбрать хотя бы один отдел.");
            haveErrors = true;
        }

        if (!haveErrors)
        {
            postRepository.save(post);

            return "redirect:/admin/posts";
        }

        model.addAttribute("departments", departmentRepository.findAll());

        return "admin/posts/add";
    }

    //

    @GetMapping("/staffs")
    public String staffList(Model model)
    {
        List<User> userStaff = new ArrayList<>();

        for (User staff : userRepository.findAll())
        {
            if (staff.getRoles().contains(Role.STAFF))
            {
                userStaff.add(staff);
            }
        }

        model.addAttribute("userStaff", userStaff);
        model.addAttribute("staffs", staffRepository.findAll());
        model.addAttribute("departments", departmentRepository.findAll());
        model.addAttribute("posts", postRepository.findAll());

        return "admin/staffs/list";
    }

    @GetMapping("/staffs/add")
    public String addStaff(Model model)
    {
        User user = new User();
        Contact contact = new Contact();
        Staff staff = new Staff();

        model.addAttribute("user", user);
        model.addAttribute("contact", contact);
        model.addAttribute("staff", staff);
        model.addAttribute("departments", departmentRepository.findAll());
        model.addAttribute("posts", postRepository.findAll());

        return "admin/staffs/add";
    }

    @PostMapping("/staffs/add")
    public String addStaffPost(@ModelAttribute("user") @Valid User user, @ModelAttribute("contact") @Valid Contact contact,
                               @ModelAttribute("staff") @Valid Staff staff, BindingResult bindingResult,
                               Model model)
    {
        model.addAttribute("departments", departmentRepository.findAll());
        model.addAttribute("posts", postRepository.findAll());

        String username = user.getUsername().trim();
        String password = user.getPassword().trim();
        String surname = user.getSurname().trim();
        String name = user.getName().trim();
        String patronymic = user.getPatronymic().trim();
        String dateOfBirth = user.getDateOfBirth();

        //region Проверка введённых данных профиля
        Boolean haveErrors = false;

        User userFromDb = userRepository.findByUsername(user.getUsername());

        if (userFromDb != null)
        {
            model.addAttribute("message", "Пользователь с таким логином уже зарегистрирован.");
            haveErrors = true;
        }

        if (username.length() < 5 || username.trim().contains(" "))
        {
            model.addAttribute("username_errors", "Значение не должно быть меньше 5 символов или содержать пробелы.");
            haveErrors = true;
        }

        if (password.length() < 5 || password.trim().contains(" "))
        {
            model.addAttribute("password_errors", "Значение не должно быть меньше 5 символов или содержать пробелы.");
            haveErrors = true;
        }

        if (surname.length() < 5 || surname.trim().contains(" "))
        {
            model.addAttribute("surname_errors", "Значение не должно быть меньше 5 символов или содержать пробелы.");
            haveErrors = true;
        }

        if (name.length() < 5 || name.trim().contains(" "))
        {
            model.addAttribute("name_errors", "Значение не должно быть меньше 5 символов или содержать пробелы.");
            haveErrors = true;
        }

        if (patronymic.length() < 5 || patronymic.trim().contains(" "))
        {
            model.addAttribute("patronymic_errors", "Значение не должно быть меньше 5 символов или содержать пробелы.");
            haveErrors = true;
        }

        if (dateOfBirth.length() < 5 || dateOfBirth.trim().contains(" "))
        {
            model.addAttribute("dateOfBirth_errors", "Выберите дату рождения.");
            haveErrors = true;
        }

        if (!haveErrors)
        {
            user.setActive(true);
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setSurname(surname);
            user.setName(name);
            user.setPatronymic(patronymic);
        }
        //endregion

        //region Проверка введённых данных контактов
        Boolean haveErrorsContact = false;

        if(contact.getEmail().length() < 5 || contact.getEmail().contains(" "))
        {
            model.addAttribute("email_errors", "Значение не должно быть меньше 5 символов или содержать пробелы.");
            haveErrorsContact = true;
        }

        if(contactRepository.findByEmail(contact.getEmail()) != null && !contactRepository.findByEmail(contact.getEmail()).getId().equals(contact.getId()))
        {
            model.addAttribute("email_errors", "Данный email уже существует.");
            haveErrorsContact = true;
        }

        if(contact.getPhoneNumber().length() < 5 || contact.getPhoneNumber().contains("-"))
        {
            model.addAttribute("phoneNumber_errors", "Значение не должно быть меньше 5 символов.");
            haveErrorsContact = true;
        }

        if(contactRepository.findByPhoneNumber(contact.getPhoneNumber()) != null && !contactRepository.findByPhoneNumber(contact.getPhoneNumber()).getId().equals(contact.getId()))
        {
            model.addAttribute("phoneNumber_errors", "Данный телефон уже существует.");
            haveErrorsContact = true;
        }

        if(!bindingResult.hasErrors() && !haveErrorsContact)
        {
            user.setContact(contact);
        }
        //endregion

        //region Проверка выбранных отделов и должностей
        boolean haveErrorsStaff = false;

        if (staff.getDepartment() == null)
        {
            model.addAttribute("department_errors", "Выберите отдел.");
            haveErrorsStaff = true;
        }

        if (staff.getPost() == null)
        {
            model.addAttribute("post_errors", "Выберите должность.");
            haveErrorsStaff = true;
        }

        if (!haveErrorsStaff && !haveErrorsContact && !haveErrors)
        {
            contactRepository.save(contact);
            //staff.setUser(user);
            staff.setPostId(staff.getPost().getId());
            staff.setDepartmentId(staff.getDepartment().getId());
            staffRepository.save(staff);

            user.setStaffId(staff.getId());
            user.setRoles(Collections.singleton(Role.STAFF));

            userRepository.save(user);

            return "redirect:/admin/staffs";
        }
        //endregion

        return "/admin/staffs/add";
    }

    @GetMapping("users/edit/{id}")
    public String editProfile(@PathVariable(value = "id") long id, Model model)
    {
        if(!userRepository.existsById(id)){
            return "redirect:/";
        }

        Optional<User> user = userRepository.findById(id);
        model.addAttribute("user", user.get());

        return "admin/users/edit";
    }

    @PostMapping("users/edit/{id}")
    public String editProfilePost(@ModelAttribute ("user") @Valid User user,
                                  BindingResult bindingResult, Model model)
    {
        Boolean haveErrors = false;

        Optional<User> checkUser = userRepository.findById(user.getId());
        String currentPassword = checkUser.get().getPassword();

        String newPassword = user.getPassword();

        //region Проверка корректности введённых данных
        if(user.getUsername() != checkUser.get().getUsername())
        {
            Boolean haveSpaces = false;

            for (char c : user.getUsername().trim().toCharArray())
            {
                if (c == ' ')
                {
                    haveSpaces = true;

                    break;
                }
            }

            if(user.getUsername().length() < 5 || haveSpaces)
            {
                model.addAttribute("username_errors", "Значение не должно быть меньше 5 символов или содержать пробелы.");
                haveErrors = true;
            }
        }

        if(user.getSurname() != checkUser.get().getSurname())
        {
            Boolean haveSpaces = false;

            for (char c : user.getSurname().trim().toCharArray())
            {
                if (c == ' ')
                {
                    haveSpaces = true;

                    break;
                }
            }

            if(user.getSurname().length() < 5 || haveSpaces)
            {
                model.addAttribute("surname_errors", "Значение не должно быть меньше 5 символов или содержать пробелы.");
                haveErrors = true;
            }
        }

        if(user.getName() != checkUser.get().getName())
        {
            Boolean haveSpaces = false;

            for (char c : user.getName().trim().toCharArray())
            {
                if (c == ' ')
                {
                    haveSpaces = true;

                    break;
                }
            }

            if(user.getName().length() < 5 || haveSpaces)
            {
                model.addAttribute("name_errors", "Значение не должно быть меньше 5 символов или содержать пробелы.");
                haveErrors = true;
            }
        }

        if(user.getPatronymic() != checkUser.get().getPatronymic())
        {
            Boolean haveSpaces = false;

            for (char c : user.getPatronymic().trim().toCharArray())
            {
                if (c == ' ')
                {
                    haveSpaces = true;

                    break;
                }
            }

            if(user.getPatronymic().length() < 5 || haveSpaces)
            {
                model.addAttribute("patronymic_errors", "Значение не должно быть меньше 5 символов или содержать пробелы.");
                haveErrors = true;
            }
        }

        if(user.getDateOfBirth() != checkUser.get().getDateOfBirth())
        {
            Boolean haveSpaces = false;

            for (char c : user.getDateOfBirth().toCharArray())
            {
                if (c == ' ') {
                    haveSpaces = true;

                    break;
                }
            }

            if (user.getDateOfBirth().length() < 5 || haveSpaces) {
                model.addAttribute("dateOfBirth_errors", "Выберите дату рождения.");
                haveErrors = true;
            }
        }

        if(user.getPassword().length() > 0)
        {
            Boolean haveSpaces = false;

            for (char c : user.getPatronymic().trim().toCharArray())
            {
                if (c == ' ')
                {
                    haveSpaces = true;

                    break;
                }
            }

            if(user.getPassword().length() < 8 || haveSpaces)
            {
                model.addAttribute("password_errors", "Введите корректный пароль. Значение не должно быть меньше 8 символов или содержать пробелы.");
                haveErrors = true;
            }
            else
            {
                newPassword = passwordEncoder.encode(user.getPassword());
            }
        }
        else
        {
            newPassword = currentPassword;
        }
        //endregion

        if (!haveErrors)
        {
            String debugString = "";

            // Редактируемые свойства
            user.setUsername(user.getUsername());
            user.setPassword(newPassword);
            user.setSurname(user.getSurname());
            user.setName(user.getName());
            user.setPatronymic(user.getPatronymic());
            user.setDateOfBirth(user.getDateOfBirth());


            // Статичные свойства
            user.setActive(checkUser.get().isActive());
            user.setRoles(checkUser.get().getRoles());
            user.setClient(checkUser.get().getClient());
            user.setContact(checkUser.get().getContact());
            user.setStaffId(checkUser.get().getStaffId());

            userRepository.save(user);

            return "redirect:/admin/users";
        }

        return "admin/users/edit";
    }
}
