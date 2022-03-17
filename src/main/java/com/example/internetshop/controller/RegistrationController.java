package com.example.internetshop.controller;

import com.example.internetshop.Entities.Users;
import com.example.internetshop.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("/saveUsers")
    public ModelAndView addUserForm(Model model) {
        ModelAndView mav = new ModelAndView("registration");
        model.addAttribute("authority", "ROLE_USER");
        mav.addObject("users", new Users());
        return mav;
    }

    @PostMapping("/saveUsers")
    public String saveUsers(@ModelAttribute("users") @Valid Users users,
                            BindingResult result, Model model) {

        model.addAttribute("authority", "ROLE_USER");

        if (result.hasErrors()) {
            return "registration";
        }

        if (!users.getPassword().equals(users.getConfirm_password())) {
            model.addAttribute("passwordError", "Пароли не совпадают");
            return "registration";
        }

        var list = usersRepository.findByLogin(users.getLogin());

        if (list.size() > 0) {
            model.addAttribute("loginError", "Пользователь с таким логином уже существует");
            return "registration";
        } else if (list.isEmpty()){
            users.setPassword(passwordEncoder.encode(users.getPassword()));
            usersRepository.save(users);
            model.addAttribute("users", usersRepository.findAll());
        }
        return "redirect:/login";
    }
}
