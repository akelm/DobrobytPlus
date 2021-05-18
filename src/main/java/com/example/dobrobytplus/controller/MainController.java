package com.example.dobrobytplus.controller;

import com.example.dobrobytplus.dto.PermissionsDto;
import com.example.dobrobytplus.entities.AccountTypes;
import com.example.dobrobytplus.entities.Accounts;
import com.example.dobrobytplus.entities.Permissions;
import com.example.dobrobytplus.service.MainService;
import com.example.dobrobytplus.service.OperationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/main")
public class MainController {

    @Autowired
    private final MainService mainService;


    @GetMapping
    public String main(Model modelMap) {
        // wszystkie konta, do ktorych ma dostep uzytkownik
        List<PermissionsDto> currentUserPermissions = mainService.getUserPermissions();
        // konta, ktore moze jeszcze utworzyc uzytkownik
        List<AccountTypes> accountsUserCanCreate = mainService.accountsUserCanCreate();


//        modelMap.addAttribute("operationsbyuser", operationService.getAuthenticatedUserOperations());
        return "main";
    }


}