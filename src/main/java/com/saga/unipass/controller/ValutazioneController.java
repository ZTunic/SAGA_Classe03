package com.saga.unipass.controller;

import com.saga.unipass.model.beans.Utente;
import com.saga.unipass.model.beans.Viaggio;
import com.saga.unipass.service.ValutazioneService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.ArrayList;

/**
 * Implementa il controller relativo al sottosistema Valutazione
 */
@Controller
@SessionAttributes("utenteLoggato")
public class ValutazioneController {
    /**
     * Il service relativo alla valutazione
     */
    ValutazioneService valutazioneService;

    /**
     * Il costruttore della classe
     */
    public ValutazioneController(){
        valutazioneService = new ValutazioneService();
    }

    /**
     * Implementa la funzionalità di valutazione di un Guidatore da parte di un Passeggero
     * @param valutazione La valutazione inserita dal Passeggero
     * @param idViaggio L'id del Viaggio
     * @param model Utilizzato per gestire la sessione
     * @return dettagli-viaggio La pagina relativa ai dettagli del Viaggio
     */
    @RequestMapping("/valuta-guidatore")
    public String valutaGuidatore(@RequestParam(name = "valutazione") String valutazione,
                                  @RequestParam(name = "idViaggio") String idViaggio, Model model){

        int indice = -1;
        Utente utente = (Utente) model.getAttribute("utenteLoggato");
        ArrayList<Viaggio> viaggi = utente.getListaViaggiPartecipati();

        for (int i = 0; i < viaggi.size(); i++) {
            if (viaggi.get(i).getIdViaggio()==Integer.parseInt(idViaggio)){
                indice=i;
                break;
            }
        }

        Viaggio v = viaggi.get(indice);
        Utente guidatore = v.getGuidatore();

        int num = guidatore.getNumeroValutazioniGuidatore()+1;
        int somma = guidatore.getSommaValutazioniGuidatore()+Integer.parseInt(valutazione);

        utente.getListaViaggiPartecipati().get(indice).getGuidatore().setNumeroValutazioniGuidatore(num);
        utente.getListaViaggiPartecipati().get(indice).getGuidatore().setSommaValutazioniGuidatore(somma);

        model.addAttribute("utenteLoggato", utente);

        valutazioneService.valutaGuidatore(guidatore, num, somma);

        return "redirect:/dettagli-viaggio?idViaggio="+Integer.parseInt(idViaggio);
    }

    /**
     * Implementa la funzionelità di valutazione di un Passeggero da parte di un Guidatore
     * @param valutazione La valutazione inserita dal Guidatore
     * @param idViaggio L'id del Viaggio
     * @param email L'email del Passeggero
     * @param model Utilizzato per gestire la sessione
     * @return dettagli-viaggio La pagina relativa ai dettagli del Viaggio
     */
    @RequestMapping("/valuta-passeggero")
    public String valutaPasseggero(@RequestParam(name = "valutazione") String valutazione,
                                   @RequestParam(name = "idViaggio") String idViaggio,
                                   @RequestParam(name = "email") String email, Model model){
        ArrayList<Viaggio> viaggi;
        Utente utente = (Utente) model.getAttribute("utenteLoggato");

        int indiceViaggio = -1, indicePasseggero = -1;

        viaggi = utente.getListaViaggiCreati();

        for (int i = 0; i < viaggi.size(); i++) {
            if (viaggi.get(i).getIdViaggio()==Integer.parseInt(idViaggio)){
                indiceViaggio=i;
                break;
            }
        }

        Viaggio v = viaggi.get(indiceViaggio);

        for (int j=0; j<v.getListaPasseggeri().size(); j++){
            if(v.getListaPasseggeri().get(j).getEmail().equalsIgnoreCase(email))
                indicePasseggero = j;

        }

        Utente passeggero = v.getListaPasseggeri().get(indicePasseggero);

        int num = passeggero.getNumeroValutazioniPasseggero()+1;
        int somma = passeggero.getSommaValutazioniPasseggero()+Integer.parseInt(valutazione);


        utente.getListaViaggiCreati().get(indiceViaggio).getListaPasseggeri().get(indicePasseggero).setNumeroValutazioniPasseggero(num);
        utente.getListaViaggiCreati().get(indiceViaggio).getListaPasseggeri().get(indicePasseggero).setSommaValutazioniPasseggero(somma);

        model.addAttribute("utenteLoggato", utente);

        valutazioneService.valutaPasseggero(passeggero, num, somma);

        return "redirect:/dettagli-viaggio?idViaggio="+Integer.parseInt(idViaggio);
    }

}
