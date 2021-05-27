package com.example.dobrobytplus.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The type Login page filter.
 */
public class LoginPageFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
                && ((HttpServletRequest)request).getRequestURI().equals("/login")) {
            System.out.println("DENIED ACCESS TO");
            System.out.println("LOGIN PAGE.");
            System.out.println("FOR AUTHENTICATED USER.");
            System.out.println("USER REDIRECTED TO MAIN.");
            ((HttpServletResponse)response).sendRedirect("/");
        }
        else if (SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
                && ((HttpServletRequest)request).getRequestURI().equals("/register")) {
            System.out.println("DENIED ACCESS TO");
            System.out.println("REGISTER PAGE.");
            System.out.println("FOR AUTHENTICATED USER.");
            System.out.println("USER REDIRECTED TO MAIN.");
            ((HttpServletResponse)response).sendRedirect("/");
        }
        chain.doFilter(request, response);
    }

}
