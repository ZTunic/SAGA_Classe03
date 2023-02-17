package com.saga.unipass.controller;

import com.saga.unipass.model.beans.Utente;
import com.saga.unipass.model.beans.Veicolo;
import com.saga.unipass.model.beans.Viaggio;
import com.saga.unipass.service.ViaggioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.Date;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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


        int y = Integer.parseInt(dataOraPartenza.substring(0,4));
        int m = Integer.parseInt(dataOraPartenza.substring(5,7));
        int d = Integer.parseInt(dataOraPartenza.substring(8,10));
        int h = Integer.parseInt(dataOraPartenza.substring(11,13));
        int mm = Integer.parseInt(dataOraPartenza.substring(14,16));

        String dataSave = y+"-"+m+"-"+d+" "+h+":"+mm;

        ArrayList<String> valoriNonValidi = controlloValoriCreazioneViaggio(destinazione, dataOraPartenza, posti, prezzo, guidatore.getVeicolo());

        if(valoriNonValidi.isEmpty()) {

            Viaggio viaggio = new Viaggio(destinazione, null, Integer.parseInt(posti), Double.parseDouble(prezzo), guidatore);

            Viaggio viaggioCreato = viaggioService.creaViaggio(viaggio, dataSave);

            if (guidatore != null)
                guidatore.getListaViaggiCreati().add(viaggioCreato);

            model.addAttribute("utenteLoggato", guidatore);

            return "redirect:/dettagli-viaggio?idViaggio=" + viaggioCreato.getIdViaggio();
        }

        return "redirect:/crea-viaggio-page";
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

        if(prezzo.length() == 0)
            prezzo = "50";


        ArrayList<String> valoriNonValidi = controlloValoriRicercaViaggio(destinazione, dataOraPartenza,  prezzo);

        if(valoriNonValidi.isEmpty()) {
            ArrayList<Viaggio> viaggiRicerca = viaggioService.cercaViaggio(destinazione, dataSave, Double.parseDouble(prezzo), utente.getEmail());
            model.addAttribute("viaggiRicerca", viaggiRicerca);

            return "risultatiRicerca.html";
        }

        return "redirect:/cerca-viaggio-page";
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

    //controlla se l'anno è bisestile
    private boolean isLeapYear(int anno) {
        return (anno % 4 == 0 && anno % 100 != 0) || anno % 400 == 0;
    }

    public ArrayList<String> controlloValoriCreazioneViaggio(String destinazione, String dataOraPartenza, String posti,
                                                             String prezzo, Veicolo veicoloViaggio){
        ArrayList<String> valoriNonValidi = new ArrayList<>();

        String regExDestinazione = "^[A-zÀ-ù '-]{1,100}$";
        Pattern patternDestinazione = Pattern.compile(regExDestinazione);
        Matcher matcherDestinazione = patternDestinazione.matcher(destinazione);

        String regExPosti = "^[1-" + veicoloViaggio.getPostiDisponibili() + "]$";
        Pattern patternPosti = Pattern.compile(regExPosti);
        Matcher matcherPosti = patternPosti.matcher(posti);

        String regExPrezzo = "^(?:[0-9]|[1-4][0-9]|50)(?:\\.\\d+)?$";
        Pattern patternPrezzo = Pattern.compile(regExPrezzo);
        Matcher matcherPrezzo = patternPrezzo.matcher(prezzo);

        if(!matcherDestinazione.matches())
            valoriNonValidi.add("Destinazione non valida.");

        int y = Integer.parseInt(dataOraPartenza.substring(0,4));
        int m = Integer.parseInt(dataOraPartenza.substring(5,7));
        int d = Integer.parseInt(dataOraPartenza.substring(8,10));
        int h = Integer.parseInt(dataOraPartenza.substring(11,13));
        int mm = Integer.parseInt(dataOraPartenza.substring(14,16)) - 60;

        //Controllo valore minuti
        if(mm < 0){
            h--;
            mm = 60 + mm;
        }

        //Controllo valore ora
        if(h < 0){
            d--;
            h = 23;
        }

        //Controllo valore mese
        if(d < 0){
            if (m == 4 || m == 6 || m == 9 || m == 11) {
                d = 30;
                m--;
            }
            else if(m == 3){
                if(isLeapYear(y))
                    d = 29;
                else
                    d = 28;
            }
            else {
                d = 31;
                m--;
            }
        }

        //Controllo valore anno
        if(m < 0){
            y--;
            m = 12;
        }


        String dataSave = y + "/" + m + "/" + d + " " + h + ":" + mm;

        Date dataDaVerificare = new Date(dataSave);
        Date dataAttuale = new Date();

        if(dataAttuale.after(dataDaVerificare))
            valoriNonValidi.add("Data non valida.");

        if(!matcherPosti.matches())
            valoriNonValidi.add("Numero posti non valido.");

        if(!matcherPrezzo.matches())
            valoriNonValidi.add("Prezzo non valido.");

        return valoriNonValidi;
    }

    public ArrayList<String> controlloValoriRicercaViaggio(String destinazione, String dataOraPartenza, String prezzo){
        ArrayList<String> valoriNonValidi = new ArrayList<>();

        String regExDestinazione = "^[A-zÀ-ù '-]{1,100}$";
        Pattern patternDestinazione = Pattern.compile(regExDestinazione);
        Matcher matcherDestinazione = patternDestinazione.matcher(destinazione);

        String regExPrezzo = "^(?:[0-9]|[1-4][0-9]|50)(?:\\.\\d+)?$";
        Pattern patternPrezzo = Pattern.compile(regExPrezzo);
        Matcher matcherPrezzo = patternPrezzo.matcher(prezzo);

        if(!matcherDestinazione.matches())
            valoriNonValidi.add("Destinazione non valida.");

        int y = Integer.parseInt(dataOraPartenza.substring(0,4));
        int m = Integer.parseInt(dataOraPartenza.substring(5,7));
        int d = Integer.parseInt(dataOraPartenza.substring(8,10));
        int h = Integer.parseInt(dataOraPartenza.substring(11,13));
        int mm = Integer.parseInt(dataOraPartenza.substring(14,16)) - 40;

        //Controllo valore minuti
        if(mm < 0){
            h--;
            mm = 60 + mm;
        }

        //Controllo valore ora
        if(h < 0){
            d--;
            h = 23;
        }

        //Controllo valore mese
        if(d < 0){
            if (m == 4 || m == 6 || m == 9 || m == 11) {
                d = 30;
                m--;
            }
            else if(m == 3){
                if(isLeapYear(y))
                    d = 29;
                else
                    d = 28;
            }
            else {
                d = 31;
                m--;
            }
        }

        //Controllo valore anno
        if(m < 0){
            y--;
            m = 12;
        }


        String dataSave = y + "/" + m + "/" + d + " " + h + ":" + mm;

        Date dataDaVerificare = new Date(dataSave);
        Date dataAttuale = new Date();

        if(dataAttuale.after(dataDaVerificare))
            valoriNonValidi.add("Data non valida.");


        if(!matcherPrezzo.matches())
            valoriNonValidi.add("Prezzo non valido.");

        return valoriNonValidi;
    }
}
