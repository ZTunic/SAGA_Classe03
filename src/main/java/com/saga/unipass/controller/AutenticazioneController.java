package com.saga.unipass.controller;

import com.saga.unipass.model.beans.Utente;
import com.saga.unipass.service.AutenticazioneService;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Implementa il controller relativo al sottosistema Autenticazione
 */
@Controller
@SessionAttributes("utenteLoggato")
public class AutenticazioneController {
    private AutenticazioneService autenticazioneService;

    /**
     * Il service relativo all'autenticazione
     */
    public AutenticazioneController(){
        autenticazioneService = new AutenticazioneService();
    }

    /**
     * Il costruttore della classe
     */
    @RequestMapping("/login-page")
    public String loginUtente(Model model) {
        model.addAttribute("utenteLoggato", null);
        return "login.html";
    }

    /**
     * Implementa la funzionalità di reindirizzamento alla pagina storicoViaggi
     * @return storicoViaggi.html La pagina da visualizzare
     */
    @RequestMapping("/storico-viaggi")
    public String visualizzaStorico(){
        return "storicoViaggi.html";
    }

    /**
     * Implementa la funzionalità di reindirizzamento alla pagina profiloPasseggero
     * @return profiloPasseggero.html La pagina da visualizzare
     */
    @RequestMapping("/pagina-utente-passeggero")
    public String paginaProfiloPasseggero(){
        return "profiloPasseggero.html";
    }

    /**
     * Implementa la funzionalità di reindirizzamento alla pagina profiloGuidatore
     * @return profiloGuidatore.html La pagina da visualizzare
     */
    @RequestMapping("/pagina-utente-guidatore")
    public String paginaProfiloGuidatore(){
        return "profiloGuidatore.html";
    }

    /**
     * Implementa la funzionalità di reindirizzamento alla pagina home
     * @return home.html La pagina da visualizzare
     */
    @RequestMapping("/home")
    public String home(){
        return "home.html";
    }

    /**
     * Implementa la funzionalità di login di un Utente
     * @param email l'e-mail inserita dall'Utente
     * @param password la password inserita dall'Utente
     * @param model Utilizzato per gestire la sessione
     * @return login-html se il login non va a buon fine
     * home Se il login va a buon fine
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam String email, @RequestParam String password, Model model){

        Utente utente = autenticazioneService.login(email, password);

        if(utente == null){
            model.addAttribute("error", true);
            return "login.html";
        }

        model.addAttribute("utenteLoggato", utente);
        return "redirect:/home";
    }

    /**
     * Implementa la funzionalità di logout
     * @param sessionStatus Utilizzato per gestire la sessione
     * @return home Ritorna alla pagina home
     */
    @RequestMapping("/logout")
    public String logout(SessionStatus sessionStatus){
        sessionStatus.setComplete();
        return "redirect:/";
    }

    /**
     * Implementa la funzionalità di modifica delle informazioni del profilo
     * @param nome Il nome dell'utente
     * @param email L'e-mail dell'utente
     * @param telefono Il numero di telefono dell'utente
     * @param cognome Il cognome dell'utente
     * @param password La password dell'utente
     * @param model Utilizzato per gestire la sessione
     * @return pagina-utente-passeggero Se l'utente è un passeggero
     * pagina-utente-guidatore Se l'utente è un guidatore
     */
    @RequestMapping("/modifica")
    public String modificaProfilo(@RequestParam(name = "nome") String nome, @RequestParam(name = "email") String email,
                                  @RequestParam(name = "telefono") String telefono, @RequestParam(name = "cognome") String cognome,
                                  @RequestParam(name = "password") String password, Model model){

        Utente utenteLoggato = (Utente) model.getAttribute("utenteLoggato");
        String emailUtenteModifica = utenteLoggato.getEmail();

        ArrayList<String> parametriNonValidi = controlloValori(email, password, nome, cognome, telefono);

        Utente utenteModificato = utenteLoggato;

        if(parametriNonValidi.isEmpty()) {

            if (!emailUtenteModifica.equalsIgnoreCase(email))
                utenteModificato.setEmail(email);
            if (!password.equals("password"))
                utenteModificato.setPassword(password);
            utenteModificato.setNome(nome);
            utenteModificato.setCognome(cognome);
            utenteModificato.setTelefono(telefono);

            if (autenticazioneService.modificaProfilo(emailUtenteModifica, utenteModificato)) {
                model.addAttribute("utenteLoggato", utenteModificato);
                model.addAttribute("success-update", true);
            } else
                model.addAttribute("error", true);
        }

        if(utenteModificato.getTipo().equalsIgnoreCase("passeggero"))
            return "redirect:/pagina-utente-passeggero";
        return "redirect:/pagina-utente-guidatore";
    }

    /**
     * Implementa la funzionalità di modifica del tipo utente (passeggero/guidatore)
     * @param email L'e-mail dell'utente
     * @param tipo Il tipo utente
     */
    public void modificaTipoUtente(String email, String tipo){
        autenticazioneService.modificaTipoUtente(email, tipo);
    }

    /**
     * Implementa il controllo della validità dei valori
     * per l'aggiornamento dei dati dell'utente
     * @param nome Il nuovo nome dell'utente
     * @param email La nuova e-mail dell'utente
     * @param telefono Il nuovo numero di telefono dell'utente
     * @param cognome Il nuovo cognome dell'utente
     * @param password La nuova password dell'utente
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
