package com.exemplo.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/publico")
    public String rotaPublica() {
        return "Essa rota é aberta! Qualquer um vê.";
    }

    @GetMapping("/privado")
    public String rotaPrivada() {
        return "Se você está vendo isso, parabéns! Você está autenticado.";
    }
}