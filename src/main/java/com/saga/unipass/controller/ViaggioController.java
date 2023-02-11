package com.saga.unipass.controller;

import com.saga.unipass.model.beans.Utente;
import com.saga.unipass.model.beans.Viaggio;
import com.saga.unipass.service.ViaggioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.Date;
import java.util.ArrayList;

@Controller
@SessionAttributes({"utenteLoggato", "viaggio", "viaggiRicerca"})
public class ViaggioController {
    private ViaggioService viaggioService;

    public ViaggioController(){
        viaggioService = new ViaggioService();
    }

    @RequestMapping("/crea-viaggio")
    public String creaViaggio(@RequestParam(name = "destinazione") String destinazione,
                              @RequestParam(name = "dataOraPartenza") String dataOraPartenza,
                              @RequestParam(name = "posti") String posti, @RequestParam(name = "prezzo") String prezzo, Model model) {

        Utente guidatore = (Utente) model.getAttribute("utenteLoggato");

        Date dataOra = new java.sql.Date(2022,11,21);
        Viaggio viaggio = new Viaggio(destinazione, (java.sql.Date) dataOra, Integer.parseInt(posti), Double.parseDouble(prezzo), guidatore);

        int y = Integer.parseInt(dataOraPartenza.substring(0,4));
        int m = Integer.parseInt(dataOraPartenza.substring(5,7));
        int d = Integer.parseInt(dataOraPartenza.substring(8,10));
        int h = Integer.parseInt(dataOraPartenza.substring(11,13));
        int mm = Integer.parseInt(dataOraPartenza.substring(14,16));

        String dataSave = y+"-"+m+"-"+d+" "+h+":"+mm;

        Viaggio viaggioCreato = viaggioService.creaViaggio(viaggio, dataSave);

        if(guidatore != null)
            guidatore.getListaViaggiCreati().add(viaggioCreato);

        model.addAttribute("utenteLoggato", guidatore);

        return "redirect:/dettagli-viaggio?idViaggio="+viaggioCreato.getIdViaggio();
    }

    @RequestMapping("/elimina-viaggio")
    public String eliminaViaggio(@RequestParam(name = "idViaggio") String idViaggio, Model model){
        Viaggio viaggioEliminato = viaggioService.eliminaViaggio(Integer.parseInt(idViaggio));

        Utente guidatore = (Utente) model.getAttribute("utenteLoggato");

        if(guidatore != null) {
            for(int i=0; i<guidatore.getListaViaggiCreati().size(); i++){
                if(guidatore.getListaViaggiCreati().get(i).getIdViaggio() == Integer.parseInt(idViaggio)){
                    guidatore.getListaViaggiCreati().remove(i);
                    break;
                }
            }
            model.addAttribute("utenteLoggato", guidatore);
        }
        return "redirect:/storico-viaggi";
    }

    @RequestMapping("/cerca-viaggio")
    public String cercaViaggio(@RequestParam(name = "destinazione") String destinazione,
                               @RequestParam(name = "dataOraPartenza") String dataOraPartenza,
                               @RequestParam(name = "prezzo") String prezzo, Model model){

        int y = Integer.parseInt(dataOraPartenza.substring(0,4));
        int m = Integer.parseInt(dataOraPartenza.substring(5,7));
        int d = Integer.parseInt(dataOraPartenza.substring(8,10));
        int h = Integer.parseInt(dataOraPartenza.substring(11,13));
        int mm = Integer.parseInt(dataOraPartenza.substring(14,16));

        String dataSave = y+"-"+m+"-"+d+" "+h+":"+mm;

        Utente utente = (Utente) model.getAttribute("utenteLoggato");

        ArrayList<Viaggio> viaggiRicerca =  viaggioService.cercaViaggio(destinazione, dataSave, Double.parseDouble(prezzo), utente.getEmail());
        model.addAttribute("viaggiRicerca", viaggiRicerca);

        return "risultatiRicerca.html";
    }

    @RequestMapping("/escludi-passeggero")
    public String escludiPasseggero(@RequestParam(name = "idViaggio") String idViaggio,
                                    @RequestParam(name = "emailPasseggero") String emailPasseggero, Model model){

        Utente passeggero = new Utente();
        passeggero.setEmail(emailPasseggero);

        viaggioService.escludiPasseggero(Integer.parseInt(idViaggio), passeggero);

        Utente utente = (Utente) model.getAttribute("utenteLoggato");

        for(int i=0; i<utente.getListaViaggiCreati().size(); i++){
            if(utente.getListaViaggiCreati().get(i).getIdViaggio() == Integer.parseInt(idViaggio)){
                for(int j=0; j<utente.getListaViaggiCreati().get(i).getListaPasseggeri().size(); j++){
                    if(utente.getListaViaggiCreati().get(i).getListaPasseggeri().get(j).getEmail().equalsIgnoreCase(emailPasseggero)){
                        utente.getListaViaggiCreati().get(i).getListaPasseggeri().remove(j);
                        break;
                    }
                }
            }
        }

        model.addAttribute("utenteLoggato", utente);

        return "redirect:/dettagli-viaggio?idViaggio="+idViaggio;
    }

    @RequestMapping("/dettagli-viaggio")
    public String dettagliViaggio(@RequestParam(name = "idViaggio") String idViaggio, Model model){

        model.addAttribute("viaggio", viaggioService.dettagliViaggio(Integer.parseInt(idViaggio)));

        return "dettagliViaggio.html";
    }

    @RequestMapping("/cerca-viaggio-page")
    public String cercaViaggioPage(){
        return "cercaViaggio.html";
    }

    @RequestMapping("/crea-viaggio-page")
    public String creaViaggioPage(){
        return "creaViaggio.html";
    }
}
