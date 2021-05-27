package com.example.dobrobytplus.controller;

import com.example.dobrobytplus.dto.CurrentTransactionsDto;
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
 * The type Current transactions controller.
 */
@Controller
@AllArgsConstructor
public class CurrentTransactionsController {

    @Autowired
    private final AccountsService accountsService;
    @Autowired
    private final PermissionsService permissionsService;
    @Autowired
    private final CurrentTransactionsService currentTransactionsService;

    /**
     * View current transactions page string.
     *
     * @param idAccount the id account
     * @param model     the model
     * @return the string
     */
    @RequestMapping({"/current_transactions/{idAccount}"})
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

        List<CurrentTransactionsDto> currentTransactions = currentTransactionsService.getCurrentTransactions(idAccount);
        model.addAttribute("listCurrentTransactions", currentTransactions);

        List<Boolean> canUserDeleteCurrentTransaction = currentTransactionsService.canUserDelete(idAccount, currentTransactions);
        model.addAttribute("listCanUserDeleteCurrentTransaction", canUserDeleteCurrentTransaction);

        Double saldoCurrentPLN = currentTransactionsService.sumCurrentTransactionsPLN(idAccount);
        String strSaldoCurrentPLN = String.format("%.2f", saldoCurrentPLN);
        model.addAttribute("strSaldoCurrentPLN", strSaldoCurrentPLN);

        Double saldoCurrentMikroSasin = currentTransactionsService.plnToMikrosasin(saldoCurrentPLN);
        String strSaldoCurrentMikroSasin = String.format("%.2f", saldoCurrentMikroSasin);
        model.addAttribute("strSaldoCurrentMikroSasin", strSaldoCurrentMikroSasin);

        return "current_transactions";
    }

    /**
     * Delete current transaction string.
     *
     * @param idCurrentTransaction the id current transaction
     * @param idAccount            the id account
     * @return the string
     */
    @RequestMapping("/deleteCurrentTransaction/{idCurrentTransaction}/idAcc/{idAccount}")
    public String deleteCurrentTransaction(@PathVariable(name = "idCurrentTransaction") Long idCurrentTransaction, @PathVariable(name = "idAccount") Long idAccount) {
        currentTransactionsService.deleteCurrentTransaction(idCurrentTransaction);

        return "redirect:/current_transactions/{idAccount}";
    }
}
