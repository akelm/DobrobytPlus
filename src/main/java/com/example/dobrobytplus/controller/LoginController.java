package com.example.dobrobytplus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The type Login controller.
 */
@Controller
public class LoginController {
    /**
     * Login page.
     *
     * @return the string
     */
    @RequestMapping({"/login"})
    public String login() {
        return "login";
    }
}
