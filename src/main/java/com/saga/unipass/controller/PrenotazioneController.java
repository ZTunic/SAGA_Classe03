package com.saga.unipass.controller;

import com.saga.unipass.model.beans.Utente;
import com.saga.unipass.model.beans.Viaggio;
import com.saga.unipass.service.PrenotazioneService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("utenteLoggato")
public class PrenotazioneController {

    private PrenotazioneService prenotazioneService;

    public PrenotazioneController(){
        prenotazioneService = new PrenotazioneService();
    }

    @RequestMapping("/prenota-viaggio")
    public String prenotaViaggio(@RequestParam(name = "idViaggio") String idViaggio, Model model){

        Utente utente = (Utente) model.getAttribute("utenteLoggato");
        Viaggio viaggioPrenotato = prenotazioneService.prenotaViaggio(Integer.parseInt(idViaggio), utente.getEmail());

        utente.getListaViaggiPartecipati().add(viaggioPrenotato);
        model.addAttribute("utenteLoggato", utente);

        return "redirect:/storico-viaggi";
    }

    @RequestMapping("/cancella-prenotazioni")
    public void cancellaPrenotazioni(@RequestParam(name = "idViaggio") String idViaggio, Model model){

        Utente utente = (Utente) model.getAttribute("utenteLoggato");
        Viaggio viaggioPrenotazioneCancellata = prenotazioneService.cancellaPrenotazione(Integer.parseInt(idViaggio), utente.getEmail());

        utente.getListaViaggiPartecipati().remove(viaggioPrenotazioneCancellata);
        model.addAttribute("utenteLoggato", utente);
    }


    @RequestMapping("/modifica-stato-prenotazioni")
    public String modificaStatoPrenotazioni(@RequestParam(name = "idViaggio") String idViaggio, Model model){

        boolean statoAggiornato = false;

        prenotazioneService.modificaStatoPrenotazioni(Integer.parseInt(idViaggio), false);

        Utente utente = (Utente) model.getAttribute("utenteLoggato");

        for(int i=0; i<utente.getListaViaggiCreati().size(); i++){
            if(utente.getListaViaggi().get(i).getIdViaggio() == Integer.parseInt(idViaggio))
                utente.getListaViaggi().get(i).setPrenotabile(statoAggiornato);
        }

        model.addAttribute("utenteLoggato", utente);

        return "redirect:/storico-viaggi";
    }

}
