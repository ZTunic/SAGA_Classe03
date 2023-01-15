package com.saga.unipass.controller;

import com.saga.unipass.service.RegistrazioneService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrazioneController {

    private RegistrazioneService registrazioneService;

    public RegistrazioneController(){
        registrazioneService = new RegistrazioneService();
    }

    @PostMapping
    public void registrazioneUtente(@RequestParam(name = "") String email, @RequestParam(name = "") String password,
                                    @RequestParam(name = "") String nome, @RequestParam(name = "") String cognome,
                                    @RequestParam(name = "") String telefono){

        registrazioneService.registrazioneUtente(email, password, nome, cognome, telefono);
    }
}
