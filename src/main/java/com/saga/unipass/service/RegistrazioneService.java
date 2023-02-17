package com.saga.unipass.service;

import com.saga.unipass.model.beans.Utente;
import com.saga.unipass.model.dao.AutenticazioneDAO;
import org.springframework.stereotype.Service;

@Service
public class RegistrazioneService {

    private AutenticazioneDAO autenticazioneDAO;

    public RegistrazioneService(){
        autenticazioneDAO = new AutenticazioneDAO();
    }

    public Utente registrazioneUtente(String email, String password, String nome, String cognome, String telefono){

        if(autenticazioneDAO.doRetrieveByEmail(email) == null) {
            Utente utente = new Utente(email, password, nome, cognome, telefono);
            autenticazioneDAO.doSave(utente);
            return utente;
        }

        return null;
    }

}
