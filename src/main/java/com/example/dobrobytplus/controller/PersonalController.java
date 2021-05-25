package com.example.dobrobytplus.controller;

import com.example.dobrobytplus.dto.AccountsDto;
import com.example.dobrobytplus.dto.AutoDispositionsDto;
import com.example.dobrobytplus.dto.CurrentTransactionsDto;
import com.example.dobrobytplus.dto.DispositionsDto;
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
import java.util.List;

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

    @RequestMapping({"/personal/{idAccount}"})
    public String viewPersonalPage(@PathVariable(name = "idAccount") Long idAccount, Model model) {
        // jesli uzytkownik nie ma uprawnien, to wyrzucamy go na strone z bledem
        //Long idAccount = (Long) modelMap.getAttribute("idAccount");
        if (!permissionsService.doesCurrentUserHaveAccessToAccount(idAccount)) {
            throw new UserHasNoAccess();
        }

        // typ rachunku i rola uzytkownika do wyswietlenia na stronie
        String accountType = accountsService.getAccountType(idAccount);
        String userRole = permissionsService.currentUserRoleInAccount(idAccount);

        model.addAttribute("accountType", accountType);
        model.addAttribute("userRole", userRole);


        // ================================================================================

        // AUTO DISPOSITIONS

        // dostajesz tutaj liste autoDispositions
        // zaden z uzytkownikow nie moze niczego usunac

        List<AutoDispositionsDto> autoDispositions = autoDispositionsService.getAutoDispositions(idAccount);

        // podsumowanie
        Double saldoAutoDispositionPLN = autoDispositionsService.sumAutoDispositionsPLN(idAccount);
        String strSaldoAutoDispositionPLN = String.format("%.2f", saldoAutoDispositionPLN);
        Double saldoAutoDispositionMikroSasin = autoDispositionsService.plnToMikrosasin(saldoAutoDispositionPLN);
        String strSaldoAutoDispositionMikroSasin = String.format("%.2f", saldoAutoDispositionMikroSasin);

        model.addAttribute("listAutoDispositions", autoDispositions);
        model.addAttribute("strSaldoAutoDispositionPLN", strSaldoAutoDispositionPLN);
        model.addAttribute("strSaldoAutoDispositionMikroSasin", strSaldoAutoDispositionMikroSasin);


        // ================================================================================

        // DISPOSITIONS

        // dostajesz tutaj liste dispositions
        // i liste Boolean, czy uzytkownik moze miec aktywny przycisk usun
        // iteracje po dwoch listach mozna zrobic tak
        // https://stackoverflow.com/questions/43183709/how-to-iterate-simultaneously-over-two-lists-using-thymeleaf
        List<DispositionsDto> dispositions = dispositionsService.getDispositions(idAccount);

        List<Boolean> canUserDeleteDisposition = dispositionsService.canUserDelete(idAccount, dispositions);

        // podsumowanie
        Double saldoDispositionPLN = dispositionsService.sumDispositionsPLN(idAccount);
        String strSaldoDispositionPLN = String.format("%.2f", saldoDispositionPLN);
        Double saldoDispositionMikroSasin = dispositionsService.plnToMikrosasin(saldoDispositionPLN);
        String strSaldoDispositionMikroSasin = String.format("%.2f", saldoDispositionMikroSasin);

        model.addAttribute("listDispositions", dispositions);
        model.addAttribute("listCanUserDeleteDisposition", canUserDeleteDisposition);
        model.addAttribute("strSaldoDispositionPLN", strSaldoDispositionPLN);
        model.addAttribute("strSaldoDispositionMikroSasin", strSaldoDispositionMikroSasin);
        // potem bedzie przycisk "dodaj dyspozycje"


        // ================================================================================

        // CURRENT TRANSACTIONS

        // dostajesz tutaj liste current transactions
        // i liste Boolean, czy uzytkownik moze miec aktywny przycisk usun
        // iteracje po dwoch listach mozna zrobic tak
        // https://stackoverflow.com/questions/43183709/how-to-iterate-simultaneously-over-two-lists-using-thymeleaf
//        List<CurrentTransactionsDto> currentTransactions = currentTransactionsService.getCurrentTransactions(idAccount);
//
//        List<Boolean> canUserDeleteCurrentTransaction = currentTransactionsService.canUserDelete(idAccount, currentTransactions);
//
//        // podsumowanie
//        Double saldoCurrentPLN = currentTransactionsService.sumCurrentTransactionsPLN(idAccount);
//        String strSaldoCurrentPLN = String.format("%.2f", saldoDispositionPLN);
//        Double saldoCurrentMikroSasin = currentTransactionsService.plnToMikrosasin(saldoCurrentPLN);
//        String strSaldoCurrentMikroSasin = String.format("%.2f", saldoDispositionPLN);
//
//        model.addAttribute("listCurrentTransactions", currentTransactions);
//        model.addAttribute("listCanUserDeleteCurrentTransaction", canUserDeleteCurrentTransaction);
//        model.addAttribute("strSaldoCurrentPLN", strSaldoCurrentPLN);
//        model.addAttribute("strSaldoDispositionMikroSasin", strSaldoCurrentMikroSasin);
        // potem bedzie przycisk "dodaj biezaca transakcje"



        return "personal";
    }

    // realizuje personal.html w tabelce DISPOZYCJE akcja USUN
    @RequestMapping("/deleteDisposition/{idDispositions}/idAcc/{idAccount}")
    public String deleteDispositions(@PathVariable(name = "idDispositions") Long idDispositions, @PathVariable(name = "idAccount") Long idAccount) {
        dispositionsService.deleteDispositions(idAccount, idDispositions);

        return "redirect:/personal/{idAccount}";
    }


//    @PostMapping
//    public String addCurrentTransaction(Double value, String description, Long idAccount,HttpServletRequest request, Errors errors) {
//        Date time =  Date.valueOf(LocalTime.now().toString());
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String username =  ((MyUsersPrincipal) principal).getUsername();
//        CurrentTransactionsDto currentTransactionsDto = new CurrentTransactionsDto(value,time,description,idAccount,username);
//        currentTransactionsService.saveCurrentTransaction(currentTransactionsDto);
//        return "redirect:/personal";
//    }
//
//
//    @PostMapping
//    public String addDisposition(Double value, String description, Long idAccount,HttpServletRequest request, Errors errors) {
//        Date time =  Date.valueOf(LocalTime.now().toString());
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String username =  ((MyUsersPrincipal) principal).getUsername();
//        DispositionsDto dispositionsDto = new DispositionsDto(value,time,description,idAccount,username);
//        dispositionsService.saveDisposition(dispositionsDto);
//        return "redirect:/personal";
//    }
}