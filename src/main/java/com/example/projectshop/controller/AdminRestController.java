package com.example.projectshop.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@Slf4j
@RequestMapping("/admin")
public class AdminRestController {

    @GetMapping("/get")//localhost:8080/api/admin/get
    public String getAccount(Principal principal){
        log.info(principal.getName() + "access to API/admin");
        return "Welcome back admin : "+ principal.getName();
    }
}