package com.example.dobrobytplus.controller;

import com.example.dobrobytplus.dto.UsersDto;
import com.example.dobrobytplus.entities.Users;
import com.example.dobrobytplus.exceptions.UserAlreadyExistException;
import com.example.dobrobytplus.repository.UsersRepository;
import com.example.dobrobytplus.security.MyUsersPrincipal;
import com.example.dobrobytplus.service.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;

@Controller
@AllArgsConstructor
public class EditUserDataController {

    private final UsersService usersService;

    private final UsersRepository usersRepository;


    public String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ((MyUsersPrincipal) principal).getUsername();
    }

    // CHECK
    @RequestMapping({"/edit_user_data"})
    public ModelAndView viewEditUserDataPage() {

        ModelAndView mav = new ModelAndView("edit_user_data");
        String username = getCurrentUsername();
        Users user = usersRepository.findByUsername(username);
        UsersDto userDto = new UsersDto(user);
        mav.addObject("usersDto", userDto);
        return mav;
    }


    // CHECK
    @RequestMapping(value="/modify_user_data", method = RequestMethod.POST)
    public String modifyUserData(@ModelAttribute("userDto") UsersDto userDto, HttpServletRequest request, Errors errors) {

        usersService.updateUserData(userDto);

        return "redirect:/logout";
    }

}