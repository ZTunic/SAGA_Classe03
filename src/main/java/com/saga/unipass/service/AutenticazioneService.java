package com.saga.unipass.service;

import com.saga.unipass.model.beans.Utente;
import com.saga.unipass.model.dao.AutenticazioneDAO;
import org.springframework.stereotype.Service;

@Service
public class AutenticazioneService {

    private AutenticazioneDAO autenticazioneDAO;

    public AutenticazioneService(){
        autenticazioneDAO = new AutenticazioneDAO();
    }

    public Utente login(String email, String password){
        Utente utente;

        if ((utente = autenticazioneDAO.doRetriveByCredentials(email, password)) != null)
            return utente;

        return null;
    }

    public Boolean modificaProfilo(String emailUserEdit, Utente editUser){
        autenticazioneDAO.doUpdate(emailUserEdit, editUser);

        return true;
    }

    /*
    public List<Viaggio> visualizzaStorico(String email){
        List<Viaggio> listaViaggiCreati =
                autenticazioneDAO.doRetriveViaggiCreati(email);
        List<Viaggio> listaViaggiPartecipati =
                autenticazioneDAO.doRetriveViaggiPartecipati(email);

        List<Viaggio> listaViaggi = new ArrayList<>();

        if(listaViaggiCreati != null){
            for(int i=0; i<listaViaggiCreati.size(); i++){
                Viaggio viaggio = listaViaggiCreati.get(i);
                listaViaggi.add(viaggio);
            }
        }

        if(listaViaggiPartecipati != null){
            for(int i=0; i<listaViaggiPartecipati.size(); i++){
                Viaggio viaggio = listaViaggiPartecipati.get(i);
                listaViaggi.add(viaggio);
            }
        }

        return listaViaggi;
    }
     */
}
