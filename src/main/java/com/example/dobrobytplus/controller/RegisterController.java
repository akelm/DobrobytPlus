package com.example.dobrobytplus.controller;


import com.example.dobrobytplus.dto.UsersDto;
import com.example.dobrobytplus.entities.Users;
import com.example.dobrobytplus.exceptions.UserAlreadyExistException;
import com.example.dobrobytplus.service.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * The type Register controller.
 */
@AllArgsConstructor
@Controller
@RequestMapping("/register")
public class RegisterController {

    private final UsersService userService;

    /**
     * Register page.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping
    public String register(Model model){
        UsersDto usersDto = new UsersDto();
        model.addAttribute("usersDto", usersDto);
        return "register";
    }


    /**
     * Register user account.
     *
     * @param usersDto the users dto
     * @param request  the request
     * @param errors   the errors
     * @return the string
     */
    @PostMapping
    public String registerUserAccount(@ModelAttribute("usersDto") UsersDto usersDto, HttpServletRequest request, Errors errors) {
        try {
            Users registered = userService.registerNewUserAccount(usersDto);
        } catch (UserAlreadyExistException e) {
            //e.printStackTrace();
            return "redirect:/register?errorUAE";
        } catch (Exception e) {
            //e.printStackTrace();
            return "redirect:/register?error";
        }
        return "redirect:/register?success";
    }
}