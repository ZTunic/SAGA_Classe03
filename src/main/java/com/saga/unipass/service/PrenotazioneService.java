package com.saga.unipass.service;

import com.saga.unipass.model.beans.Viaggio;
import com.saga.unipass.model.dao.PrenotazioneDAO;
import org.springframework.stereotype.Service;

@Service
public class PrenotazioneService {

    private ViaggioService viaggioService;
    private PrenotazioneDAO prenotazioneDAO;

    public PrenotazioneService(){
        prenotazioneDAO = new PrenotazioneDAO();
        viaggioService = new ViaggioService();
    }

    public Viaggio prenotaViaggio(int idViaggio, String emailPasseggero){
        prenotazioneDAO.doSave(idViaggio, emailPasseggero);

        return viaggioService.dettagliViaggio(idViaggio);
    }

    public Viaggio cancellaPrenotazione(int idViaggio, String emailPasseggero){
        prenotazioneDAO.doRemove(idViaggio, emailPasseggero);

        return viaggioService.dettagliViaggio(idViaggio);
    }

    public void modificaStatoPrenotazioni(int idViaggio, boolean stato){
        prenotazioneDAO.doUpdateStato(idViaggio, stato);
    }
}
