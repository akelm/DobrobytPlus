package com.example.dobrobytplus.controller;

import com.example.dobrobytplus.dto.AccountsDto;
import com.example.dobrobytplus.dto.PermissionsDto;
import com.example.dobrobytplus.entities.AccountTypes;
import com.example.dobrobytplus.service.AccountsService;
import com.example.dobrobytplus.service.PermissionsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/main")
public class MainController {


    @Autowired
    private final AccountsService accountsService;
    @Autowired
    private final PermissionsService permissionsService;

    @GetMapping
    public String main(Model modelMap) {
        // wszystkie konta, do ktorych ma dostep uzytkownik
        List<PermissionsDto> currentUserPermissions = permissionsService.getUserPermissions();
        // konta, ktore moze jeszcze utworzyc uzytkownik
        List<AccountTypes> accountsUserCanCreate = accountsService.accountsUserCanCreate();

        // @Valid TODO

        //        modelMap.addAttribute("operationsbyuser", operationService.getAuthenticatedUserOperations());
        return "main";
    }


    /** rejestruje konto
     * byc moze trzeba to bedzie cos zmienic w zaleznosci od template's
     *
     * @param accountsDto
     * @param request
     * @param errors
     * @return
     */
    @PostMapping
    public String registerNewAccount(@ModelAttribute("accountsDto") AccountsDto accountsDto, HttpServletRequest request, Errors errors) {
        accountsService.registerNewAccount(accountsDto);
        return "redirect:/main";
    }


}