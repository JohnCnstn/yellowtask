package com.johncnstn.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Value("#{systemEnvironment['MYSQL_DB_PORT']}")
    private String javaHome;

    @GetMapping("/home")
    public String home() {
        System.out.println(javaHome);
        return "/home";
    }
}
