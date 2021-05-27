package com.example.dobrobytplus.controller;

import com.example.dobrobytplus.dto.*;
import com.example.dobrobytplus.entities.AccountTypes;
import com.example.dobrobytplus.entities.Accounts;
import com.example.dobrobytplus.entities.Users;
import com.example.dobrobytplus.exceptions.UserHasNoAccess;
import com.example.dobrobytplus.security.MyUsersPrincipal;
import com.example.dobrobytplus.service.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Personal controller.
 */
@Controller
@AllArgsConstructor
public class PersonalController {

    @Autowired
    private final AccountsService accountsService;
    @Autowired
    private final PermissionsService permissionsService;
    @Autowired
    private final CurrentTransactionsService currentTransactionsService;
    @Autowired
    private final DispositionsService dispositionsService;
    @Autowired
    private final AutoDispositionsService autoDispositionsService;

    /**
     * View personal page string.
     *
     * @param idAccount the id account
     * @param model     the model
     * @return the string
     */
    @RequestMapping({"/personal/{idAccount}"})
    public String viewPersonalPage(@PathVariable(name = "idAccount") Long idAccount, Model model) {
        // jesli uzytkownik nie ma uprawnien, to wyrzucamy go na strone z bledem
        //Long idAccount = (Long) modelMap.getAttribute("idAccount");
        if (!permissionsService.doesCurrentUserHaveAccessToAccount(idAccount)) {
            throw new UserHasNoAccess();
        }

        List<SaldaDto> saldaDtos = accountsService.sumAccount(idAccount);

        model.addAttribute("salda",saldaDtos);
        // typ rachunku i rola uzytkownika do wyswietlenia na stronie
        String accountType = accountsService.getAccountType(idAccount);
        String userRole = permissionsService.currentUserRoleInAccount(idAccount);

        model.addAttribute("accountType", accountType);
        model.addAttribute("userRole", userRole);

        return "personal";
    }

    /**
     * Delete dispositions string.
     *
     * @param idDispositions the id dispositions
     * @param idAccount      the id account
     * @return the string
     */
// realizuje personal.html w tabelce DISPOZYCJE akcja USUN
    @RequestMapping("/deleteDisposition/{idDispositions}/idAcc/{idAccount}")
    public String deleteDispositions(@PathVariable(name = "idDispositions") Long idDispositions, @PathVariable(name = "idAccount") Long idAccount) {
        dispositionsService.deleteDispositions(idAccount, idDispositions);

        return "redirect:/personal/{idAccount}";
    }


}