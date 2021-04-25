package com.example.dobrobytplus.controller;

import com.example.dobrobytplus.security.MyUserPrincipal;
import com.example.dobrobytplus.service.OperationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/operations")
public class OperationsController {

    @Autowired
    private final OperationService operationService;


    @GetMapping
    public String index(Model modelMap) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((MyUserPrincipal) principal).getUsername();
        modelMap.addAttribute("operationsbyuser", operationService.getUserOperations(username));
        modelMap.addAttribute("username", username);
        return "operations";
    }


}