package com.example.dobrobytplus.controller;

import com.example.dobrobytplus.dto.AutoDispositionsDto;
import com.example.dobrobytplus.dto.DispositionsDto;
import com.example.dobrobytplus.dto.MonthsDto;
import com.example.dobrobytplus.exceptions.UserHasNoAccess;
import com.example.dobrobytplus.service.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * The type Months controller.
 */
@Controller
@AllArgsConstructor
public class MonthsController {

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
    @Autowired
    private final HistoryService historyService;

    /**
     * View personal page string.
     *
     * @param idAccount the id account
     * @param model     the model
     * @return the string
     */
    @RequestMapping({"/months/{idAccount}"})
    public String viewPersonalPage(@PathVariable(name = "idAccount") Long idAccount, Model model) {
        // jesli uzytkownik nie ma uprawnien, to wyrzucamy go na strone z bledem
        //Long idAccount = (Long) modelMap.getAttribute("idAccount");
        if (!permissionsService.doesCurrentUserHaveAccessToAccount(idAccount)) {
            throw new UserHasNoAccess();
        }

        String accountType = accountsService.getAccountType(idAccount);
        model.addAttribute("accountType", accountType);
        List<MonthsDto> months = historyService.getHistoryMonthsForAccount(idAccount);
        model.addAttribute("listAutoDispositions", months);

        return "months";
    }


}