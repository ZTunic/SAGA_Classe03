package com.saga.unipass.controller;

import com.saga.unipass.model.beans.Utente;
import com.saga.unipass.model.beans.Viaggio;
import com.saga.unipass.service.ViaggioService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.ArrayList;
import java.util.Date;

@Controller
@SessionAttributes({"utenteLoggato", "viaggio", "viaggi-ricerca"})
public class ViaggioController {
    private ViaggioService viaggioService;

    public ViaggioController(){
        viaggioService = new ViaggioService();
    }

    @RequestMapping("/crea-viaggio")
    public void creaViaggio(@RequestParam(name = "destinazione") String destinazione,
                            @RequestParam(name = "dataOraPartenza") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dataOraPartenza,
                            @RequestParam(name = "posti") String posti, @RequestParam(name = "prezzo") String prezzo, Model model){

        Utente guidatore = (Utente) model.getAttribute("utenteLoggato");

        Viaggio viaggio = new Viaggio(destinazione, dataOraPartenza, Integer.parseInt(posti), Double.parseDouble(prezzo), guidatore);

        Viaggio viaggioCreato = viaggioService.creaViaggio(viaggio);

        if(guidatore != null)
            guidatore.getListaViaggi().add(viaggioCreato);

        model.addAttribute("utenteLoggato", guidatore);
    }

    @RequestMapping("/elimina-viaggio")
    public String eliminaViaggio(@RequestParam(name = "idViaggio") String idViaggio, Model model){
        Viaggio viaggioEliminato = viaggioService.eliminaViaggio(Integer.parseInt(idViaggio));

        Utente guidatore = (Utente) model.getAttribute("utenteLoggato");

        if(guidatore != null) {
            guidatore.getListaViaggi().remove(viaggioEliminato);
            model.addAttribute("utenteLoggato", guidatore);
        }
        return "/storico-viaggi.html";
    }

    @RequestMapping("/cerca-viaggio")
    public void cercaViaggio(@RequestParam(name = "destinazione") String destinazione,
                             @RequestParam(name = "dataOraPartenza") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dataOraPartenza,
                             @RequestParam(name = "prezzo") String prezzo, Model model){

        ArrayList<Viaggio> viaggiRicerca =  viaggioService.cercaViaggio(destinazione, dataOraPartenza, Double.parseDouble(prezzo));
        model.addAttribute("viaggi-ricerca", viaggiRicerca);
    }

    @RequestMapping("/escludi-passeggero")
    public String escludiPasseggero(@RequestParam(name = "idViaggio") String idViaggio,
                                  @RequestParam(name = "emailPasseggero") String emailPasseggero){

        Utente passeggero = new Utente();
        passeggero.setEmail(emailPasseggero);

        viaggioService.escludiPasseggero(Integer.parseInt(idViaggio), passeggero);

        return "/dettagli-viaggio?idViaggio="+idViaggio;
    }

    @RequestMapping("/dettagli-viaggio")
    public String dettagliViaggio(@RequestParam(name = "idViaggio") String idViaggio, Model model){

        model.addAttribute("viaggio", viaggioService.dettagliViaggio(Integer.parseInt(idViaggio)));

        return "dettagliViaggio.html";
    }
}
