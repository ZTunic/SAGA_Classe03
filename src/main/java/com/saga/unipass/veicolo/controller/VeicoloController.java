package com.saga.unipass.veicolo.controller;

import com.saga.unipass.autenticazione.controller.AutenticazioneController;
import com.saga.unipass.model.beans.Utente;
import com.saga.unipass.model.beans.Veicolo;
import com.saga.unipass.veicolo.service.VeicoloService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * Implementa il controller relativo al sottosistema Veicolo
 */
@Controller
@SessionAttributes("utenteLoggato")
public class VeicoloController {
    /**
     * Il service relativo al veicolo
     */
    private VeicoloService veicoloService;

    /**
     * Il costruttore della classe
     */
    public VeicoloController(){
        veicoloService = new VeicoloService();
    }

    /**
     * Implementa la funzionalità di aggiunta di un Veicolo
     * @param targa La targa del Veicolo
     * @param marca La marca del Veicolo
     * @param modello Il modello del Veicolo
     * @param colore Il colore del Veicolo
     * @param posti I posti disponibili del Veicolo
     * @param model Utilizzato per gestire la sessione
     * @return pagina-utente-guidatore La pagina in cui vengono mostrate le informazioni del Guidatore
     */
    @RequestMapping("/aggiungi-veicolo")
    public String aggiungiVeicolo(@RequestParam(name = "targa") String targa, @RequestParam(name = "marca") String marca,
                                  @RequestParam(name = "modello") String modello, @RequestParam(name = "colore") String colore,
                                  @RequestParam(name = "postiDisponibili") String posti, Model model){

        Utente utente = (Utente) model.getAttribute("utenteLoggato");
        AutenticazioneController autenticazioneController = new AutenticazioneController();

        if(utente != null) {
            utente.setVeicolo(veicoloService.aggiungiVeicolo(targa, marca, modello, colore, Integer.parseInt(posti),
                    (Utente) model.getAttribute("utenteLoggato")));

            utente.setTipo("guidatore");

            autenticazioneController.modificaTipoUtente(utente.getEmail(), "guidatore");

            model.addAttribute("utenteLoggato", utente);
        }

        return "redirect:/pagina-utente-guidatore";
    }

    /**
     * Implementa la funzionalità di modifica di un Veicolo
     * @param targa La targa di un Veicolo
     * @param marca La marca di un Veicolo
     * @param modello Il modello di un Veicolo
     * @param colore Il colore di un Veicolo
     * @param posti I posti disponibili di un Veicolo
     * @param model Utilizzato per gestire la sessione
     * @return pagina-utente-guidatore La pagina in cui vengono mostrate le informazioni del Guidatore
     */
    @RequestMapping("/modifica-veicolo")
    public String modificaVeicolo(@RequestParam(name = "targa") String targa, @RequestParam(name = "marca") String marca,
                                  @RequestParam(name = "modello") String modello, @RequestParam(name = "colore") String colore,
                                  @RequestParam(name = "postiDisponibili") String posti, Model model){

        Utente utente = (Utente) model.getAttribute("utenteLoggato");

        if(utente != null){
            String targaVeicoloModifica = utente.getVeicolo().getTarga();
            Veicolo veicolo = new Veicolo(targa, marca, modello, colore, Integer.parseInt(posti), utente);
            veicoloService.modificaVeicolo(targaVeicoloModifica, veicolo);
            utente.setVeicolo(veicolo);
            model.addAttribute("utenteLoggato", utente);
        }

        return "redirect:/pagina-utente-guidatore";
    }

    /**
     * Implementa la funzionalità di rimozione di un Veicolo
     * @param model Utilizzato per gestire la sessione
     * @return pagina-utente-passeggero La pagina in cui vengono mostrate le informazioni del Passeggero
     */
    @RequestMapping("/rimuovi-veicolo")
    public String rimuoviVeicolo(Model model){
        Utente utente = (Utente) model.getAttribute("utenteLoggato");
        AutenticazioneController autenticazioneController = new AutenticazioneController();

        if(utente != null) {
            veicoloService.rimuoviVeicolo(utente.getVeicolo().getTarga());

            utente.rimuoviVeicolo();
            utente.setTipo("passeggero");

            autenticazioneController.modificaTipoUtente(utente.getEmail(), "passeggero");

            model.addAttribute("utenteLoggato", utente);

        }

        return "redirect:/pagina-utente-passeggero";
    }
}
