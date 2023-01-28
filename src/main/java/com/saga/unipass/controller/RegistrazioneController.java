package com.saga.unipass.controller;

import com.saga.unipass.model.beans.Utente;
import com.saga.unipass.service.RegistrazioneService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("utenteLoggato")
public class RegistrazioneController {

    private RegistrazioneService registrazioneService;

    public RegistrazioneController(){
        registrazioneService = new RegistrazioneService();
    }

    @RequestMapping("/registrazione")
    public String registrazioneUtente() {

        return "registrazione.html";
    }

    @RequestMapping("/registrazione-utente")
    public String registrazioneUtente(@RequestParam(name = "email") String email, @RequestParam(name = "password") String password,
                                    @RequestParam(name = "nome") String nome, @RequestParam(name = "cognome") String cognome,
                                    @RequestParam(name = "telefono") String telefono, Model model){

        registrazioneService.registrazioneUtente(email, password, nome, cognome, telefono);

        model.addAttribute("utenteLoggato", new Utente(email, password, nome, cognome, telefono));
        return "redirect:/home  ";
    }
}
