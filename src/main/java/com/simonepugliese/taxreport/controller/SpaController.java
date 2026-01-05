package com.simonepugliese.taxreport.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SpaController {

    @GetMapping({"/", "/nuova-spesa"})
    public String forward() {
        // Forza il ritorno dell'index.html per le rotte di React
        return "forward:/index.html";
    }
}