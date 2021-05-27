package com.example.dobrobytplus.controller;

import com.example.dobrobytplus.dto.DispositionsDto;
import com.example.dobrobytplus.security.MyUsersPrincipal;
import com.example.dobrobytplus.service.DispositionsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * The type Register new cyclic transaction controller.
 */
@Controller
@AllArgsConstructor
public class RegisterNewCyclicTransactionController {

    @Autowired
    private final DispositionsService dispositionsService;

    /**
     * View register new cyclic transaction string.
     *
     * @param idAccount the id account
     * @param model     the model
     * @return the string
     */
    @RequestMapping("/registerNewCyclicTransaction/{idAccount}")
    public String viewRegisterNewCyclicTransaction(@PathVariable(name = "idAccount") Long idAccount, Model model) {

        DispositionsDto cyclicTransactionsDto = new DispositionsDto();
        model.addAttribute("cyclicTransactionsDto", cyclicTransactionsDto);

        return "register_new_cyclic_transaction";
    }

    /**
     * Add cyclic transaction string.
     *
     * @param cyclicTransactionsDto the cyclic transactions dto
     * @param idAccount             the id account
     * @param request               the request
     * @param errors                the errors
     * @return the string
     */
    @RequestMapping(value="/saveNewCyclicTransaction/{idAccount}", method = RequestMethod.POST)
    public String addCyclicTransaction(@ModelAttribute("cyclicTransactionsDto") DispositionsDto cyclicTransactionsDto, @PathVariable(name = "idAccount") Long idAccount, HttpServletRequest request, Errors errors) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username =  ((MyUsersPrincipal) principal).getUsername();

        long timeStamp = System.currentTimeMillis();
        java.sql.Date sqlDate = new java.sql.Date(timeStamp);
        cyclicTransactionsDto.setTime(sqlDate);

        cyclicTransactionsDto.setIdAccount(idAccount);
        cyclicTransactionsDto.setUsername(username);

        dispositionsService.saveDisposition(cyclicTransactionsDto);

        return "redirect:/cyclic_transactions/{idAccount}";
    }
}
