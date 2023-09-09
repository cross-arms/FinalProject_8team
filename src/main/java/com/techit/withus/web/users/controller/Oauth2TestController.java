package com.techit.withus.web.users.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class Oauth2TestController
{
    @GetMapping("/test")
    public String logIn()
    {
        return "oauth";
    }
}
