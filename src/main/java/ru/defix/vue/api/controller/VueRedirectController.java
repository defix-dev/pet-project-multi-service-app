package ru.defix.vue.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VueRedirectController {
    @GetMapping({"/services/**", "/main"})
    public String services() { return "services"; }
}
