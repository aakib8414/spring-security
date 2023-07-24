package com.jwt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class HomeController {
    @GetMapping("/home")
    public String getHome(){
        return "home page";
    }

    @GetMapping("/login")
    public String login(){
        return "login page";
    }
    @GetMapping("/register")
    public String register(){
        return "Register page";
    }
}
