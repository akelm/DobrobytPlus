package com.example.dobrobytplus.controller;

import com.example.dobrobytplus.dto.UsersDto;
import com.example.dobrobytplus.entities.Users;
import com.example.dobrobytplus.exceptions.UsernameNotFoundException;
import com.example.dobrobytplus.repository.UsersRepository;
import com.example.dobrobytplus.security.MyUsersPrincipal;
import com.example.dobrobytplus.service.PermissionsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;

/**
 * The type Add partner controller.
 */
@Controller
@AllArgsConstructor
public class AddPartnerController {

    @Autowired
    private final PermissionsService permissionsService;

    private final UsersRepository usersRepository;

    /**
     * Gets current username.
     *
     * @return the current username
     */
    public String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ((MyUsersPrincipal) principal).getUsername();
    }

    /**
     * View add partner page string.
     *
     * @param idAccount the id account
     * @param model     the model
     * @return the string
     */
    @RequestMapping({"/addPartner/{idAccount}"})
    public String viewAddPartnerPage(@PathVariable(name = "idAccount") Long idAccount, Model model) {
        model.addAttribute("idAccount", idAccount);
        UsersDto usersDto = new UsersDto();
        model.addAttribute("usersDto", usersDto);
        return "add_partner";
    }

    /**
     * dodawanie partnera
     * jesli nie ma partnera, metoda rzuca UserNotFound albo cos innego
     * jak np. user jest niepełnoletni
     *
     * @param usersDto  the users dto
     * @param idAccount the id account
     * @return the string
     */
    @RequestMapping({"/addPartnerAccount/{idAccount}"})
    public String addPartnerToAccount(@ModelAttribute("usersDto") UsersDto usersDto, @PathVariable(name = "idAccount") Long idAccount) {

        try {
            permissionsService.addCPartnerToAccount(usersDto.getUsername(), idAccount);

        } catch(UsernameNotFoundException e) {

            System.out.println("EXCEPTION: UsernameNotFoundException");
            //e.printStackTrace();

            return "redirect:/addPartner/{idAccount}?error";
        }

        return "redirect:/membership/{idAccount}";
    }
}
