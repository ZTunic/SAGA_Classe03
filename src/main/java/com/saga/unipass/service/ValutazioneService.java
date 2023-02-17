package com.saga.unipass.service;

import com.saga.unipass.model.beans.Utente;
import com.saga.unipass.model.dao.ValutazioneDAO;

/**
 * Implementa il service relativo al sottosistema Valutazione
 */
public class ValutazioneService {

    /**
     * Il DAO relativo alla valutazione
     */
    private ValutazioneDAO valutazioneDAO;

    /**
     * Il costruttore della classe
     */
    public ValutazioneService() {
        valutazioneDAO = new ValutazioneDAO();
    }

    /**
     * Implementa la funzionalità di valutazione di un Guidatore
     * @param guidatore Il Guidatore da valutare
     * @param num Il numero di valutazioni del Guidatore
     * @param somma La somma delle valutazioni del Guidatore
     */
    public void valutaGuidatore(Utente guidatore, int num, int somma){

        valutazioneDAO.doValutaGuidatore(guidatore, num, somma);
    }

    /**
     * Implementa la funzionalità di valutazione di un Passeggero
     * @param passeggero Il Passeggero da valutare
     * @param num Il numero di valutazioni del Passeggero
     * @param somma La somma delle valutazioni del Passeggero
     */
    public void valutaPasseggero(Utente passeggero, int num, int somma){

        valutazioneDAO.doValutaPasseggero(passeggero, num, somma);
    }
}
