package com.example.dobrobytplus.controller;

import com.example.dobrobytplus.dto.*;
import com.example.dobrobytplus.entities.AccountTypes;
import com.example.dobrobytplus.entities.Accounts;
import com.example.dobrobytplus.entities.Users;
import com.example.dobrobytplus.exceptions.UserHasNoAccess;
import com.example.dobrobytplus.security.MyUsersPrincipal;
import com.example.dobrobytplus.service.AccountsService;
import com.example.dobrobytplus.service.CurrentTransactionsService;
import com.example.dobrobytplus.service.DispositionsService;
import com.example.dobrobytplus.service.PermissionsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.time.LocalTime;
import java.util.List;

@Controller
@AllArgsConstructor
public class MembershipController {

    @Autowired
    private final AccountsService accountsService;
    @Autowired
    private final PermissionsService permissionsService;
    @Autowired
    private final CurrentTransactionsService currentTransactionsService;
    @Autowired
    private final DispositionsService dispositionsService;

    //    @GetMapping
    @RequestMapping({"/membership/{idAccount}"})
    public String viewMembershipPage(@PathVariable(name = "idAccount") Long idAccount, Model modelMap) {
        // jesli uzytkownik nie ma uprawnien, to wyrzucamy go na strone z bledem
        //Long idAccount = (Long) modelMap.getAttribute("idAccount");
        if (!permissionsService.doesCurrentUserHaveAccessToAccount(idAccount)) {
            throw new UserHasNoAccess();
        }

        // wlasciciel konta
        String owner = permissionsService.getAccountOwner(idAccount);
        modelMap.addAttribute("owner", owner);

        String userRole = permissionsService.currentUserRoleInAccount(idAccount);
        modelMap.addAttribute("userRole", userRole);


        // partner -- jesli nie ma partnera, to jest pusty String ""
        String partner = permissionsService.getAccountPartner(idAccount);
        modelMap.addAttribute("partner", partner);

        // dzieci
        List<String> children = permissionsService.getAccountChildren(idAccount);
        modelMap.addAttribute("listChildren", children);

        // obok partnera i dzieci bedzie przycisk do ich usuniecia z konta


        // tu bedzie formularz dodaj partnera, jesli nie ma partnera
        // tu zawsze bedzie formularz "dodaj dzieci"
        // albo to wszystko na osobnych stronach

        return "membership";
    }


    /** tą metodą usuwasz uzytkownikow z konta
     *
     * @param username
     * @param idAccount
     */
    @RequestMapping("/deleteUser/{username}/idAcc/{idAccount}")
    public String revokeAccessForUser(@PathVariable(name = "username") String username, @PathVariable(name = "idAccount") Long idAccount) {
        permissionsService.revokeAccessToAccount(username, idAccount);

        return "redirect:/membership/{idAccount}";
    }

}