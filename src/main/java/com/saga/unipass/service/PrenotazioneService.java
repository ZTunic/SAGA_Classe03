package com.saga.unipass.service;

import com.saga.unipass.model.beans.Viaggio;
import com.saga.unipass.model.dao.PrenotazioneDAO;
import org.springframework.stereotype.Service;

/**
 * Implementa il service relativo al sottosistema Prenotazione
 */
@Service
public class PrenotazioneService {

    /**
     * Il service relativo al Viaggio
     */
    private ViaggioService viaggioService;

    /**
     * Il DAO relativo alla prenotazione
     */
    private PrenotazioneDAO prenotazioneDAO;

    /**
     * Il costruttore della classe
     */
    public PrenotazioneService(){
        prenotazioneDAO = new PrenotazioneDAO();
        viaggioService = new ViaggioService();
    }

    /**
     * Implementa la funzionalità di prenotazione ad un viaggio
     * @param idViaggio L'id del viaggio a cui prenotarsi
     * @param emailPasseggero L'email del Passeggero che ha intenzione di prenotarsi
     * @return viaggioService.dettagliViaggio(idViaggio) Chiamata di un metodo del service relativo al Viaggio
     */
    public Viaggio prenotaViaggio(int idViaggio, String emailPasseggero){
        prenotazioneDAO.doSave(idViaggio, emailPasseggero);

        return viaggioService.dettagliViaggio(idViaggio);
    }

    /**
     * Implementa la funzionalità di cancellazione di una prenotazione
     * @param idViaggio L'id del viaggio
     * @param emailPasseggero L'email del passeggero
     * @return viaggioService.dettagliViaggio(idViaggio) Chiamata di un metodo del service relativo al Viaggio
     */
    public Viaggio cancellaPrenotazione(int idViaggio, String emailPasseggero){
        prenotazioneDAO.doRemove(idViaggio, emailPasseggero);

        return viaggioService.dettagliViaggio(idViaggio);
    }

    /**
     * Implementa la funzionalità di chiusura delle prenotazioni
     * @param idViaggio L'id del Viaggio
     * @param stato Lo stato del Viaggio
     */
    public void modificaStatoPrenotazioni(int idViaggio, boolean stato){
        prenotazioneDAO.doUpdateStato(idViaggio, stato);
    }
}
