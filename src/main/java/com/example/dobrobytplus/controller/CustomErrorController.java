package com.example.dobrobytplus.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

/**
 * The type Custom error controller.
 */
@Controller
public class CustomErrorController implements ErrorController {

    /**
     * Handle error model and view.
     *
     * @param request the request
     * @return the model and view
     */
    @RequestMapping("/error")
    public ModelAndView handleError(HttpServletRequest request) {

        ModelAndView customErrorPage = new ModelAndView("custom_error_page");

        int errorStatusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        String errorMsg = "Http Error Code: " + errorStatusCode + "\n";

        switch (errorStatusCode) {
            case 400: {
                errorMsg += "Bad Request";
                break;
            }
            case 401: {
                errorMsg += "Unauthorized";
                break;
            }
            case 404: {
                errorMsg += "Resource not found";
                break;
            }
            case 500: {
                errorMsg += "Internal Server Error";
                break;
            }
        }

        customErrorPage.addObject("errorMsg",errorMsg);

        return customErrorPage;
    }

    /**
     * Test non existent page string.
     *
     * @return the string
     */
    @RequestMapping(value = "Err404", method = RequestMethod.GET)
    public String testNonExistentPage() {
        return "redirect:/spring-mvc-xml/invalidUrl";
    }

    /**
     * Test runtime exception.
     */
    @RequestMapping(value = "Err500", method = RequestMethod.GET)
    public void testRuntimeException() {
        throw new NullPointerException("Throwing NullPointerException");
    }


    // metoda musi byc: https://www.baeldung.com/spring-boot-custom-error-page
    @Override
    public String getErrorPath() {
        return null;
    }
}
