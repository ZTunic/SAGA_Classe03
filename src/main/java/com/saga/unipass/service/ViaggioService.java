package com.saga.unipass.service;

import com.saga.unipass.model.beans.Utente;
import com.saga.unipass.model.beans.Viaggio;
import com.saga.unipass.model.dao.ViaggioDAO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
public class ViaggioService {
    private ViaggioDAO viaggioDAO;

    public ViaggioService(){
        viaggioDAO = new ViaggioDAO();
    }

    public Viaggio creaViaggio(Viaggio viaggio){
        viaggioDAO.doSave(viaggio);

        return viaggioDAO.doRetrieveById(viaggio.getIdViaggio());
    }

    public Viaggio eliminaViaggio(int idViaggio){
        return viaggioDAO.doRemove(idViaggio);
    }

    public ArrayList<Viaggio> cercaViaggio(String destinazione, Date dataOraPartenza, double prezzo){
        return viaggioDAO.doSearch(destinazione, dataOraPartenza, prezzo);
    }

    public void escludiPasseggero(int idViaggio, Utente passeggero){
        viaggioDAO.doRemovePasseggero(idViaggio, passeggero);
    }

    public Viaggio dettagliViaggio(int idViaggio){
        return viaggioDAO.doRetrieveById(idViaggio);
    }

}
