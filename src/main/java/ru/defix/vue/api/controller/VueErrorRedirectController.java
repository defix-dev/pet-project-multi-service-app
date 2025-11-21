package ru.defix.vue.api.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VueErrorRedirectController implements ErrorController {
    @GetMapping("/error")
    public String handleError(HttpServletRequest req) {
        Integer statusCode = (Integer) req.getAttribute("jakarta.servlet.error.status_code");

        if (statusCode != null && statusCode == HttpStatus.NOT_FOUND.value()) {
            return "redirect:/main";
        }

        return "error";
    }
}
