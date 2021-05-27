package com.example.dobrobytplus.controller;

import com.example.dobrobytplus.dto.AccountsDto;
import com.example.dobrobytplus.dto.PermissionsDto;
import com.example.dobrobytplus.entities.AccountTypes;
import com.example.dobrobytplus.entities.Accounts;
import com.example.dobrobytplus.service.AccountsService;
import com.example.dobrobytplus.service.PermissionsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * The type Main controller.
 */
@Controller
@Configuration
@AllArgsConstructor
public class MainController {

    @Autowired
    private final AccountsService accountsService;
    @Autowired
    private final PermissionsService permissionsService;

    /**
     * View main page.
     *
     * @param modelMap the model map
     * @return the string
     */
//    @GetMapping
    @RequestMapping({"/main", "/", "/home", "", "*"})
    public String viewMainPage(Model modelMap) {
        // wszystkie konta, do ktorych ma dostep uzytkownik
        List<PermissionsDto> currentUserPermissions = permissionsService.getUserPermissions();
//        currentUserPermissions.get(0).getAccount().getIdAccounts();

        modelMap.addAttribute("listCurrentUserPermissions", currentUserPermissions);

        // liste id
        // konta, ktore moze jeszcze utworzyc uzytkownik
        List<AccountTypes> accountsUserCanCreate = accountsService.accountsUserCanCreate();

        modelMap.addAttribute("listAccountsToCreate", accountsUserCanCreate);

        //@Valid TODO

        //modelMap.addAttribute("operationsbyuser", operationService.getAuthenticatedUserOperations());
        return "main";
    }

    /**
     * Register new account.
     *
     * @param accountType the account type
     * @return the string
     */
    @RequestMapping("/utworz/{accountTypeToCreate}")
    public String registerNewAccount(@PathVariable(name = "accountTypeToCreate") AccountTypes accountType) {
        AccountsDto accountsDto = new AccountsDto(accountType);
        accountsService.registerNewAccount(accountsDto);

        return "redirect:/main";
    }
}