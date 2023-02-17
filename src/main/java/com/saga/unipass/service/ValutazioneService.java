package com.saga.unipass.service;

import com.saga.unipass.model.beans.Utente;
import com.saga.unipass.model.dao.ValutazioneDAO;

public class ValutazioneService {

    private ValutazioneDAO valutazioneDAO;

    public ValutazioneService() {
        valutazioneDAO = new ValutazioneDAO();
    }

    public void valutaGuidatore(Utente guidatore, int num, int somma){

        valutazioneDAO.doValutaGuidatore(guidatore, num, somma);
    }

    public void valutaPasseggero(Utente passeggero, int num, int somma, int idViaggio){

        valutazioneDAO.doValutaPasseggero(passeggero, num, somma);
    }
}
