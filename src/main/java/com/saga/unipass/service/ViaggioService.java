package com.saga.unipass.service;

import com.saga.unipass.model.beans.Utente;
import com.saga.unipass.model.beans.Viaggio;
import com.saga.unipass.model.dao.ViaggioDAO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Implementa il service relativo al sottosistema Viaggio
 */
@Service
public class ViaggioService {
    /**
     * Il DAO relativo al viaggio
     */
    private ViaggioDAO viaggioDAO;

    /**
     * Il costruttore della classe
     */
    public ViaggioService(){
        viaggioDAO = new ViaggioDAO();
    }

    /**
     * Implementa la funzionalità di creazione di un viaggio
     * @param viaggio Il viaggio da creare
     * @param dataSave La data e l'ora del Viaggio
     * @return viaggioDAO.doRetrieveById(viaggio.getIdViaggio()) Chiamata del metodo del DAO di salvataggio di un viaggio
     */
    public Viaggio creaViaggio(Viaggio viaggio, String dataSave){
        viaggioDAO.doSave(viaggio, dataSave);

        return viaggioDAO.doRetrieveById(viaggio.getIdViaggio());
    }

    /**
     * Implementa la funzionalità di eliminazione di un Viaggio
     * @param idViaggio L'id del Viaggio da cancellare
     * @return viaggioDAO.doRemove(idViaggio) Chiamata del metodo del DAO di cancellazione di un viaggio
     */
    public Viaggio eliminaViaggio(int idViaggio){
        return viaggioDAO.doRemove(idViaggio);
    }

    /**
     * Implementa la funzionalità di ricerca di un Viaggio
     * @param destinazione Il luogo di destinazione del Viaggio
     * @param dataOraPartenza La data e l'ora dopo la quale sono presenti Viaggi disponibili
     * @param prezzo Il prezzo massimo consentito
     * @param emailPrenotante L'email del Passeggero il quale vuole ricercare un Viaggio
     * @return viaggioDAO.doSearch(destinazione, dataOraPartenza, prezzo, emailPrenotante) Chiamata
     * del metodo del DAO di ricerca di un viaggio
     */
    public ArrayList<Viaggio> cercaViaggio(String destinazione, String dataOraPartenza, double prezzo, String emailPrenotante){
        return viaggioDAO.doSearch(destinazione, dataOraPartenza, prezzo, emailPrenotante);
    }

    /**
     * Implementa la funzionalità di esclusione di un Passeggero
     * @param idViaggio L'id del Viaggio
     * @param passeggero Il Passeggero da escludere
     */
    public void escludiPasseggero(int idViaggio, Utente passeggero){
        viaggioDAO.doRemovePasseggero(idViaggio, passeggero);
    }

    /**
     * Implementa la funzionalità di ricerca di un Viaggio
     * @param idViaggio L'id del Viaggio
     * @return viaggioDAO.doRetrieveById(idViaggio) Chiamata del metodo del DAO di ricerca di un viaggio in base all'id
     */
    public Viaggio dettagliViaggio(int idViaggio){
        return viaggioDAO.doRetrieveById(idViaggio);
    }

}
