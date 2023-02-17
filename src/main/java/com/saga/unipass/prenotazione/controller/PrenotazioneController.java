package com.saga.unipass.prenotazione.controller;

import com.saga.unipass.model.beans.Utente;
import com.saga.unipass.model.beans.Viaggio;
import com.saga.unipass.prenotazione.service.PrenotazioneService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * Implementa il controller relativo al sottosistema Prenotazione
 */
@Controller
@SessionAttributes("utenteLoggato")
public class PrenotazioneController {

    /**
     * Il service relativo alla prenotazione
     */
    private PrenotazioneService prenotazioneService;

    /**
     * Il costruttore della classe
     */
    public PrenotazioneController(){
        prenotazioneService = new PrenotazioneService();
    }

    /**
     * Implementa la funzionalità di prenotazione ad un viaggio da parte di un Passeggero
     * @param idViaggio L'id del Viaggio
     * @param model Utilizzato per gestire la sessione
     * @return storico-viaggi La pagina relativa allo storico viaggi dell'Utente
     */
    @RequestMapping("/prenota-viaggio")
    public String prenotaViaggio(@RequestParam(name = "idViaggio") String idViaggio, Model model){

        Utente utente = (Utente) model.getAttribute("utenteLoggato");
        Viaggio viaggioPrenotato = prenotazioneService.prenotaViaggio(Integer.parseInt(idViaggio), utente.getEmail());

        utente.getListaViaggiPartecipati().add(viaggioPrenotato);
        model.addAttribute("utenteLoggato", utente);

        return "redirect:/storico-viaggi";
    }

    /**
     * Implementa la funzionalità di cancellazione di una prenotazione da parte di un Passeggero
     * @param idViaggio L'id del Viaggio
     * @param model Utilizzato per gestire la sessione
     */
    @RequestMapping("/cancella-prenotazioni")
    public void cancellaPrenotazioni(@RequestParam(name = "idViaggio") String idViaggio, Model model){

        Utente utente = (Utente) model.getAttribute("utenteLoggato");
        Viaggio viaggioPrenotazioneCancellata = prenotazioneService.cancellaPrenotazione(Integer.parseInt(idViaggio), utente.getEmail());

        utente.getListaViaggiPartecipati().remove(viaggioPrenotazioneCancellata);
        model.addAttribute("utenteLoggato", utente);
    }

    /**
     * Implementa la funzionalità di chiusura delle prenotazioni da parte di un Guidatore
     * @param idViaggio L'id del Viaggio
     * @param model Utilizzato per gestire la sessione
     * @return storico-viaggi La pagina dello storico viaggi del Guidatore
     */
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
