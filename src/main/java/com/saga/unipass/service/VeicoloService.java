package com.saga.unipass.service;

import com.saga.unipass.model.beans.Utente;
import com.saga.unipass.model.beans.Veicolo;
import com.saga.unipass.model.dao.VeicoloDAO;
import org.springframework.stereotype.Service;

@Service
public class VeicoloService {
    private VeicoloDAO veicoloDAO;

    public VeicoloService(){
        veicoloDAO = new VeicoloDAO();
    }

    public Veicolo aggiungiVeicolo(String targa, String marca, String modello, String colore, int postiDisponibili, Utente utente){
        Veicolo veicolo = new Veicolo(targa, marca, modello, colore, postiDisponibili, utente);
        veicoloDAO.doSave(veicolo);
        return veicolo;
    }

    public void modificaVeicolo(String targa, Veicolo veicolo){
        veicoloDAO.doUpdate(targa, veicolo);
    }

    public void rimuoviVeicolo(String targa){
        veicoloDAO.doRemove(targa);
    }
}
