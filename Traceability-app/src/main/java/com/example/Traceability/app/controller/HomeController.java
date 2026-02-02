package com.example.Traceability.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    private String showLogin(){
        return "login";

    }

    @GetMapping("/nav")
    private String showNavigation(){
        return "navigation";
    }
}
