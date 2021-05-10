package com.example.dobrobytplus.controller;

import com.example.dobrobytplus.dto.UserDto;
import com.example.dobrobytplus.exceptions.UserAlreadyExistException;
import com.example.dobrobytplus.entities.User;
import com.example.dobrobytplus.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@AllArgsConstructor
@Controller
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    private final UserService userService;

    @GetMapping
    public String register(){
        return "register";
   }

    @GetMapping("/register")
    public String showRegistrationForm(WebRequest request, Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "register";
    }

    // @Valid TODO
    @PostMapping
    public ModelAndView registerUserAccount(@ModelAttribute("user") UserDto userDto, HttpServletRequest request, Errors errors) {
        try {
            User registered = userService.registerNewUserAccount(userDto);
        } catch (UserAlreadyExistException e) {
            e.printStackTrace();
//			TODO: dodac wyswietlanie erroru na stronie
//            mav.addObject("message", "An account for that username already exists.");
//            return mav;
        }
        return new ModelAndView("login", "user", userDto);
    }
}
