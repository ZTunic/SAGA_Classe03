package com.saga.unipass.dao;

import com.saga.unipass.dao.AutenticazioneDAO;
import com.saga.unipass.model.beans.Utente;
import com.saga.unipass.model.beans.Viaggio;

import java.util.ArrayList;

public class DAOFacade {
    private AutenticazioneDAO autenticazioneDAO;

    public DAOFacade() {
        this.autenticazioneDAO = new AutenticazioneDAO();
    }

    public void doSave(Utente utente) {
        autenticazioneDAO.doSave(utente);
    }

    public Utente doRetrieveByEmail(String email) {
        return autenticazioneDAO.doRetrieveByEmail(email);
    }

    public Utente doRetrieveByCredentials(String email, String password) {
        return autenticazioneDAO.doRetrieveByCredentials(email, password);
    }

    public ArrayList<Viaggio> doRetrieveViaggiCreati(String email) {
        return autenticazioneDAO.doRetrieveViaggiCreati(email);
    }

    public ArrayList<Viaggio> doRetrieveViaggiPartecipati(String email) {
        return autenticazioneDAO.doRetrieveViaggiPartecipati(email);
    }

    public void doUpdate(String emailUtenteModifica, Utente utente) {
        autenticazioneDAO.doUpdate(emailUtenteModifica, utente);
    }

    public void doUpdateTipoUtente(String email, String tipo) {
        autenticazioneDAO.doUpdateTipoUtente(email, tipo);
    }
}
