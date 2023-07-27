package com.clinic.domain.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping
    public String getIndex1(Authentication auth, Model model){
        model.addAttribute("isAuthenticated",auth!=null);
        return "index";
    }

}
