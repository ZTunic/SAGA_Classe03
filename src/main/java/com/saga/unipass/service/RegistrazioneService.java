package com.saga.unipass.service;

import com.saga.unipass.model.beans.Utente;
import com.saga.unipass.model.dao.DAOFacade;
import org.springframework.stereotype.Service;

/**
 * Implementa il service relativo al sottosistema Registrazione
 */
@Service
public class RegistrazioneService {

    /**
     * Il DAO relativo al Facade
     */
    private DAOFacade daoFacade;

    /**
     * Il costruttore della classe
     */
    public RegistrazioneService(){
        daoFacade = new DAOFacade();
    }

    /**
     * Implementa la funzionalità di registrazione
     * @param email L'email dell'Utente
     * @param password La password dell'Utente
     * @param nome Il nome dell'Utente
     * @param cognome Il cognome dell'Utente
     * @param telefono Il numero di telefono dell'Utente
     * @return utente L'Utente appena registrato
     */
    public Utente registrazioneUtente(String email, String password, String nome, String cognome, String telefono){

        if(daoFacade.doRetrieveByEmail(email) == null) {
            Utente utente = new Utente(email, password, nome, cognome, telefono);
            daoFacade.doSave(utente);
            return utente;
        }

        return null;
    }

}
