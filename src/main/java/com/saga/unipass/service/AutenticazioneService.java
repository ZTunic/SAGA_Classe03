package com.saga.unipass.service;

import com.saga.unipass.model.beans.Utente;
import com.saga.unipass.model.dao.AutenticazioneDAO;
import org.springframework.stereotype.Service;

@Service
public class AutenticazioneService {

    private AutenticazioneDAO autenticazioneDAO;

    public AutenticazioneService(){
        autenticazioneDAO = new AutenticazioneDAO();
    }

    public Utente login(String email, String password){
        Utente utente;

        if ((utente = autenticazioneDAO.doRetrieveByCredentials(email, password)) != null)
            return utente;

        return null;
    }

    public Boolean modificaProfilo(String emailUserEdit, Utente editUser){
        autenticazioneDAO.doUpdate(emailUserEdit, editUser);

        return true;
    }

    public void modificaTipoUtente(String email, String tipo){
        autenticazioneDAO.doUpdateTipoUtente(email, tipo);
    }
}
