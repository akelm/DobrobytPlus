package com.example.dobrobytplus.controller;

import com.example.dobrobytplus.dto.AutoDispositionsDto;
import com.example.dobrobytplus.dto.DispositionsDto;
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

    @RequestMapping({"/months/{idAccount}"})
    public String viewPersonalPage(@PathVariable(name = "idAccount") Long idAccount, Model model) {
        // jesli uzytkownik nie ma uprawnien, to wyrzucamy go na strone z bledem
        //Long idAccount = (Long) modelMap.getAttribute("idAccount");
        if (!permissionsService.doesCurrentUserHaveAccessToAccount(idAccount)) {
            throw new UserHasNoAccess();
        }

        // typ rachunku i rola uzytkownika do wyswietlenia na stronie
        String accountType = accountsService.getAccountType(idAccount);


        model.addAttribute("accountType", accountType);

        List<String> months = historyService.getHistoryMonthsForAccount(idAccount);

        // ================================================================================

        // AUTO DISPOSITIONS

        // dostajesz tutaj liste autoDispositions
        // zaden z uzytkownikow nie moze niczego usunac

//        List<AutoDispositionsDto> autoDispositions = autoDispositionsService.getAutoDispositions(idAccount);

        // podsumowanie
        Double saldoAutoDispositionPLN = autoDispositionsService.sumAutoDispositionsPLN(idAccount);
        String strSaldoAutoDispositionPLN = String.format("%.2f", saldoAutoDispositionPLN);
        Double saldoAutoDispositionMikroSasin = autoDispositionsService.plnToMikrosasin(saldoAutoDispositionPLN);
        String strSaldoAutoDispositionMikroSasin = String.format("%.2f", saldoAutoDispositionMikroSasin);

        model.addAttribute("listAutoDispositions", months);
        model.addAttribute("strSaldoAutoDispositionPLN", strSaldoAutoDispositionPLN);
        model.addAttribute("strSaldoAutoDispositionMikroSasin", strSaldoAutoDispositionMikroSasin);


        return "months";
    }


}