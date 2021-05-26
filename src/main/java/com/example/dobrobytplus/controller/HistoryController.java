package com.example.dobrobytplus.controller;

import com.example.dobrobytplus.dto.HistoryDto;
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

@Controller
@AllArgsConstructor
public class HistoryController {

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

    @RequestMapping({"/history/{monthStr}/idAcc/{idAccount}"})
    public String viewPersonalPage(@PathVariable(name = "monthStr") String monthStr, @PathVariable(name = "idAccount") Long idAccount, Model model) {
        // jesli uzytkownik nie ma uprawnien, to wyrzucamy go na strone z bledem
        //Long idAccount = (Long) modelMap.getAttribute("idAccount");
        if (!permissionsService.doesCurrentUserHaveAccessToAccount(idAccount)) {
            throw new UserHasNoAccess();
        }

        String accountType = accountsService.getAccountType(idAccount);
        model.addAttribute("accountType", accountType);
        model.addAttribute("monStr", monthStr);
        Double saldoPLN = historyService.sumForMonth(monthStr,idAccount);
        model.addAttribute("saldoPLN", saldoPLN);
        Double saldomikrosasin = historyService.plnToMikroSasin(saldoPLN);
        model.addAttribute("saldoMikrosasin", saldomikrosasin);
        List<HistoryDto> hist = historyService.getHistoryForMonth(idAccount,monthStr);
        model.addAttribute("listAutoDispositions", hist);

        return "history";
    }


}