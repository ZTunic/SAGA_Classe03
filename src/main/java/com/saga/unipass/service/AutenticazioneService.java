package com.saga.unipass.service;

import com.saga.unipass.model.beans.Utente;
import com.saga.unipass.model.dao.DAOFacade;
import org.springframework.stereotype.Service;

/**
 * Implementa il service relativo al sottosistema Autenticazione
 */
@Service
public class AutenticazioneService {

    /**
     * Il DAO relativo all'autenticazione
     */
    private DAOFacade daoFacade;

    /**
     * Il costruttore della classe
     */
    public AutenticazioneService(){
        daoFacade = new DAOFacade();
    }

    /**
     * Implementa la funzionalità di login di un Utente
     * @param email L'email dell'Utente
     * @param password La password dell'Utente
     * @return utente L'Utente appena loggato
     */
    public Utente login(String email, String password){
        Utente utente;

        if ((utente = daoFacade.doRetrieveByCredentials(email, password)) != null)
            return utente;

        return null;
    }

    /**
     * Implementa la funzionalità di modifica del profilo
     * @param emailUserEdit L'email dell'Utente da modificare
     * @param editUser l'Utente da modificare
     * @return true Booleano che indica che la modifica è avvenuta
     */
    public Boolean modificaProfilo(String emailUserEdit, Utente editUser){
        daoFacade.doUpdate(emailUserEdit, editUser);

        return true;
    }

    /**
     * Implementa la funzionalità di modifica del tipo Utente
     * @param email L'email dell'utente del quale modificare il tipo
     * @param tipo Il tipo Utente (passeggero/guidatore)
     */
    public void modificaTipoUtente(String email, String tipo){
        daoFacade.doUpdateTipoUtente(email, tipo);
    }
}
