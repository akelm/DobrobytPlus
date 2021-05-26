package com.example.dobrobytplus.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public ModelAndView handleError(HttpServletRequest request) {

        ModelAndView customErrorPage = new ModelAndView("custom_error_page");

        Object errorStatusCode = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        String errorMsg = "Http Error Code: " + errorStatusCode + ".\n";

        switch (errorStatusCode.toString()) {
            case "400": {
                errorMsg += "Bad Request";
                break;
            }
            case "401": {
                errorMsg += "Unauthorized";
                break;
            }
            case "404": {
                errorMsg += "Resource not found";
                break;
            }
            case "500": {
                errorMsg += "Internal Server Error";
                break;
            }
        }

        customErrorPage.addObject("errorMsg",errorMsg);

        return customErrorPage;
    }

    // FOR TESTING
    @RequestMapping("/Err")
    public String viewNonExistentPage() {
        int i=1/0;          // throws ArithmeticException
        return "non_existent_page";
    }


    // metoda musi byc: https://www.baeldung.com/spring-boot-custom-error-page
    @Override
    public String getErrorPath() {
        return null;
    }
}
