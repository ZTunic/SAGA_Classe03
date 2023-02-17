package com.saga.unipass.veicolo.service;

import com.saga.unipass.model.beans.Utente;
import com.saga.unipass.model.beans.Veicolo;
import com.saga.unipass.dao.VeicoloDAO;
import org.springframework.stereotype.Service;

/**
 * Implementa il service relativo al sottosistema Veicolo
 */
@Service
public class VeicoloService {
    /**
     * Il DAO relativo al veicolo
     */
    private VeicoloDAO veicoloDAO;

    /**
     * Il costruttore della classe
     */
    public VeicoloService(){
        veicoloDAO = new VeicoloDAO();
    }

    /**
     * Implementa la funzionalità di aggiunta di un Veicolo
     * @param targa La targa del Veicolo
     * @param marca La marca del Veicolo
     * @param modello Il modello del Veicolo
     * @param colore Il colore del Veicolo
     * @param postiDisponibili I posti disponibili del Veicolo
     * @param utente L'utente a cui si deve aggiungere il veicolo
     * @return veicolo Il veicolo appena aggiunto
     */
    public Veicolo aggiungiVeicolo(String targa, String marca, String modello, String colore, int postiDisponibili, Utente utente){
        Veicolo veicolo = new Veicolo(targa, marca, modello, colore, postiDisponibili, utente);
        veicoloDAO.doSave(veicolo);
        return veicolo;
    }

    /**
     * Implementa la funzionalità di modifica di un Veicolo
     * @param targa La targa del Veicolo
     * @param veicolo Il veicolo da modificare
     */
    public void modificaVeicolo(String targa, Veicolo veicolo){
        veicoloDAO.doUpdate(targa, veicolo);
    }

    /**
     * Implementa la funzionalità di rimozione di un Veicolo
     * @param targa La targa del Veicolo da rimuovere
     */
    public void rimuoviVeicolo(String targa){
        veicoloDAO.doRemove(targa);
    }
}
