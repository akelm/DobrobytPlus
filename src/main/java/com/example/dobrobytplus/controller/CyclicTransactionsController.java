package com.example.dobrobytplus.controller;

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
public class CyclicTransactionsController {

    @Autowired
    private final AccountsService accountsService;
    @Autowired
    private final PermissionsService permissionsService;
    @Autowired
    private final DispositionsService dispositionsService;

    @RequestMapping({"cyclic_transactions/{idAccount}"})
    public String viewCurrentTransactionsPage(@PathVariable(name = "idAccount") Long idAccount, Model model) {
        // jesli uzytkownik nie ma uprawnien, to wyrzucamy go na strone z bledem
        //Long idAccount = (Long) modelMap.getAttribute("idAccount");
        if (!permissionsService.doesCurrentUserHaveAccessToAccount(idAccount)) {
            throw new UserHasNoAccess();
        }

        // ================================================================================

        // typ rachunku i rola uzytkownika do wyswietlenia na stronie
        String accountType = accountsService.getAccountType(idAccount);
        model.addAttribute("accountType", accountType);

        String userRole = permissionsService.currentUserRoleInAccount(idAccount);
        model.addAttribute("userRole", userRole);

        // ================================================================================

        // CURRENT TRANSACTIONS

        List<DispositionsDto> cyclicTransactions = dispositionsService.getDispositions(idAccount);
        model.addAttribute("listCyclicTransactions", cyclicTransactions);

        List<Boolean> canUserDeleteCyclicTransaction = dispositionsService.canUserDelete(idAccount, cyclicTransactions);
        model.addAttribute("listCanUserDeleteCyclicTransaction", canUserDeleteCyclicTransaction);

        Double saldoCyclicPLN = dispositionsService.sumDispositionsPLN(idAccount);
        String strSaldoCyclicPLN = String.format("%.2f", saldoCyclicPLN);
        model.addAttribute("strSaldoCyclicPLN", strSaldoCyclicPLN);

        Double saldoCyclicMikroSasin = dispositionsService.plnToMikrosasin(saldoCyclicPLN);
        String strSaldoCyclicMikroSasin = String.format("%.2f", saldoCyclicMikroSasin);
        model.addAttribute("strSaldoCyclicMikroSasin", strSaldoCyclicMikroSasin);

        return "cyclic_transactions";
    }

    @RequestMapping("/deleteCyclicTransaction/{idCyclicTransaction}/idAcc/{idAccount}")
    public String deleteCyclicTransaction(@PathVariable(name = "idCyclicTransaction") Long idCyclicTransaction, @PathVariable(name = "idAccount") Long idAccount) {
        dispositionsService.deleteDispositions(idAccount, idCyclicTransaction);

        return "redirect:/cyclic_transactions/{idAccount}";
    }
}
