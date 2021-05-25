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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;


@Controller
@AllArgsConstructor
public class RegisterNewDispositionController {

    @Autowired
    private final DispositionsService dispositionsService;

    @RequestMapping("/registerNewDisposition/{idAccount}")
    public String viewRegisterNewDisposition(@PathVariable(name = "idAccount") Long idAccount, Model model) {
        DispositionsDto dispositionDto = new DispositionsDto();
        model.addAttribute("dispositionDto", dispositionDto);

        return "register_new_disposition";
    }

    @RequestMapping(value="/saveNewDisposition/{idAccount}", method = RequestMethod.POST)
    public String addDisposition(@ModelAttribute("dispositionDto") DispositionsDto dispositionDto, @PathVariable(name = "idAccount") Long idAccount, HttpServletRequest request, Errors errors) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username =  ((MyUsersPrincipal) principal).getUsername();

        long timeStamp = System.currentTimeMillis();
        java.sql.Date sqlDate = new java.sql.Date(timeStamp);
        dispositionDto.setTime(sqlDate);

        dispositionDto.setUsername(username);
        dispositionDto.setIdAccount(idAccount);

        dispositionsService.saveDisposition(dispositionDto);

        return "redirect:/personal/{idAccount}";
    }
}
