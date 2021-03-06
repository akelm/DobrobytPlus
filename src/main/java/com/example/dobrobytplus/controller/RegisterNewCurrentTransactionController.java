package com.example.dobrobytplus.controller;

import com.example.dobrobytplus.dto.CurrentTransactionsDto;
import com.example.dobrobytplus.security.MyUsersPrincipal;
import com.example.dobrobytplus.service.CurrentTransactionsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * The type Register new current transaction controller.
 */
@Controller
@AllArgsConstructor
public class RegisterNewCurrentTransactionController {

    @Autowired
    private final CurrentTransactionsService currentTransactionsService;

    /**
     * View register new current transaction.
     *
     * @param idAccount the id account
     * @param model     the model
     * @return the string
     */
    @RequestMapping("/registerNewCurrentTransaction/{idAccount}")
    public String viewRegisterNewCurrentTransaction(@PathVariable(name = "idAccount") Long idAccount, Model model) {

        CurrentTransactionsDto currentTransactionsDto = new CurrentTransactionsDto();
        model.addAttribute("currentTransactionsDto",currentTransactionsDto);

        return "register_new_current_transaction";
    }

    /**
     * Add current transaction.
     *
     * @param currentTransactionsDto the current transactions dto
     * @param idAccount              the id account
     * @param request                the request
     * @param errors                 the errors
     * @return the string
     */
    @RequestMapping(value="/saveNewCurrentTransaction/{idAccount}", method = RequestMethod.POST)
    public String addCurrentTransaction(@ModelAttribute("currentTransactionsDto") CurrentTransactionsDto currentTransactionsDto, @PathVariable(name = "idAccount") Long idAccount, HttpServletRequest request, Errors errors) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username =  ((MyUsersPrincipal) principal).getUsername();

        long timeStamp = System.currentTimeMillis();
        java.sql.Date sqlDate = new java.sql.Date(timeStamp);
        currentTransactionsDto.setTime(sqlDate);

        currentTransactionsDto.setIdAccount(idAccount);
        currentTransactionsDto.setUsername(username);

        currentTransactionsService.saveCurrentTransaction(currentTransactionsDto);

        return "redirect:/current_transactions/{idAccount}";
    }
}
