package com.saga.unipass.controller;

import com.saga.unipass.model.beans.Utente;
import com.saga.unipass.service.RegistrazioneService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Implementa il controller relativo al sottosistema Registrazione
 */
@Controller
@SessionAttributes("utenteLoggato")
public class RegistrazioneController {

    /**
     * Il service relativo alla registrazione
     */
    private RegistrazioneService registrazioneService;

    /**
     * Il costruttore della classe
     */
    public RegistrazioneController(){
        registrazioneService = new RegistrazioneService();
    }

    /**
     * Implementa la funzionalità di reindirizzamento alla pagina registrazione
     * @return registrazione.html La pagina da visualizzare
     */
    @RequestMapping("/registrazione")
    public String registrazioneUtente() {

        return "registrazione.html";
    }

    /**
     * Implementa la funzionalità di registrazione di un Utente
     * @param email L'email dell'Utente
     * @param password La password dell'Utente
     * @param nome Il nome dell'Utente
     * @param cognome Il cognome dell'Utente
     * @param telefono Il numero di telefono dell'Utente
     * @param model Utilizzato per gestire la sessione
     * @return home La pagina home
     */
    @RequestMapping("/registrazione-utente")
    public String registrazioneUtente(@RequestParam(name = "email") String email, @RequestParam(name = "password") String password,
                                      @RequestParam(name = "nome") String nome, @RequestParam(name = "cognome") String cognome,
                                      @RequestParam(name = "telefono") String telefono, Model model, final RedirectAttributes redirectAttributes){

        ArrayList<String> parametriNonValidi = controlloValori(email, password, nome, cognome, telefono);

        if(parametriNonValidi.isEmpty()) {
            if (registrazioneService.registrazioneUtente(email, password, nome, cognome, telefono) != null) {
                model.addAttribute("utenteLoggato", new Utente(email, password, nome, cognome, telefono));
                redirectAttributes.addFlashAttribute("success", true);
                return "redirect:/home";
            }
        }

        redirectAttributes.addFlashAttribute("error", true);
        return "redirect:/registrazione";
    }

    /**
     * Implementa il controllo della validità dei valori
     * per la registrazione di un Utente
     * @param email L'email dell'Utente
     * @param password La password dell'Utente
     * @param nome Il nome dell'Utente
     * @param cognome Il cognome dell'Utente
     * @param telefono Il numero di telefono dell'Utente
     * @return ArrayList<String> una lista di stringhe che mantiene le informazioni
     * dei valori che non rispettano il loro formato
     */
    public ArrayList<String> controlloValori(String email, String password, String nome, String cognome, String telefono){

        ArrayList<String> parametriNonValidi =  new ArrayList<>();

        String regExPassword = "^[A-z0-9._//%+-]{8,32}$";
        Pattern patternPassword = Pattern.compile(regExPassword);
        Matcher matcherPassword = patternPassword.matcher(password);

        String regExEmail = "^[A-z0-9._%+-]+@[A-z0-9.-]+\\.[A-z]{2,10}$";
        Pattern patternEmail = Pattern.compile(regExEmail, Pattern.CASE_INSENSITIVE);
        Matcher matcherEmail = patternEmail.matcher(email);

        String regExNomeCognome = "^[A-zÀ-ù ‘-]{1,35}$";
        Pattern patternNomeCognome = Pattern.compile(regExNomeCognome);
        Matcher matcherNome = patternNomeCognome.matcher(nome);
        Matcher matcherCognome = patternNomeCognome.matcher(cognome);

        String regExTelefono = "^[0-9]{10}$";
        Pattern patternTelefono = Pattern.compile(regExTelefono);
        Matcher matcherTelefono = patternTelefono.matcher(telefono);

        if(!matcherPassword.matches())
            parametriNonValidi.add("Password non valida.");

        if(!matcherEmail.matches())
            parametriNonValidi.add("Email non valida.");

        if(!matcherNome.matches())
            parametriNonValidi.add("Nome non valido.");

        if(!matcherCognome.matches())
            parametriNonValidi.add("Cognome non valido.");

        if(!matcherTelefono.matches())
            parametriNonValidi.add("Telefono non valido.");

        return parametriNonValidi;
    }
}
