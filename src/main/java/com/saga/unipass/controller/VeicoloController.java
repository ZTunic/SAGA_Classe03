package com.saga.unipass.controller;

import com.saga.unipass.model.beans.Utente;
import com.saga.unipass.model.beans.Veicolo;
import com.saga.unipass.service.VeicoloService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("utenteLoggato")
public class VeicoloController {
    private VeicoloService veicoloService;

    public VeicoloController(){
        veicoloService = new VeicoloService();
    }

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
