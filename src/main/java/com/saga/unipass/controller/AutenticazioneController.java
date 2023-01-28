package com.saga.unipass.controller;

import com.saga.unipass.model.beans.Utente;
import com.saga.unipass.service.AutenticazioneService;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@SessionAttributes("utenteLoggato")
public class AutenticazioneController {
    private AutenticazioneService autenticazioneService;

    public AutenticazioneController(){
        autenticazioneService = new AutenticazioneService();
    }

    @RequestMapping("/login-page")
    public String loginUtente(Model model) {
        model.addAttribute("utenteLoggato", null);
        return "login.html";
    }

    @RequestMapping("/home")
    public String home(){
        return "home.html";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam String email, @RequestParam String password, Model model){

        Utente utente = autenticazioneService.login(email, password);

        if(utente == null){
            model.addAttribute("error", true);
            return "login.html";
        }

        model.addAttribute("utenteLoggato", utente);
        return "home.html";
    }

    @RequestMapping("/logout")
    public String logout(SessionStatus sessionStatus){
        sessionStatus.setComplete();
        return "redirect:/";
    }

    @RequestMapping("/storico-viaggi")
    public String visualizzaStorico(){
        return "storico.html";
    }

    @RequestMapping("/modifica")
    public String modificaProfilo(@RequestParam(name = "email") String email, @RequestParam(name = "password") String password,
                                  @RequestParam(name = "nome") String nome, @RequestParam(name = "cognome") String cognome,
                                  @RequestParam(name = "telefono") String telefono, Model model){

        Utente utenteLoggato = (Utente) model.getAttribute("utenteLoggato");
        Utente utenteModificato = utenteLoggato;
        utenteModificato.setEmail(email);
        utenteModificato.setPassword(password);
        utenteModificato.setNome(nome);
        utenteModificato.setCognome(cognome);
        utenteModificato.setTelefono(telefono);

        if(autenticazioneService.modificaProfilo(utenteLoggato.getEmail(), utenteModificato)){
            model.addAttribute("utenteLoggato", utenteModificato);
            model.addAttribute("success-update", true);
        }
        else
            model.addAttribute("error", true);

        return "redirect:/home";
    }


}
