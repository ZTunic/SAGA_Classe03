package com.saga.unipass.model.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author SAGA
 *
 * La classe "Viaggio" mantiene le informazione di un viaggio.
 * Inoltre, la medesima classe offre dei metodi utili per la gestione dei viaggi.
 *
 */

public class Viaggio {

    private int idViaggio;
    private String destinazione;
    private Date dataOraPartenza;
    private int posti;
    private double prezzo;
    private boolean prenotabile;
    private Utente guidatore;
    private List<Utente> listaPasseggeri;

    /**
     * <h2>Costruttori della classe</h2>
     */

    public Viaggio() {
        this.prenotabile = true;
        this.guidatore = null;
        this.listaPasseggeri = new ArrayList<>();
    }

    public Viaggio(int idViaggio, String destinazione, Date dataOraPartenza, int posti, double prezzo, Utente guidatore) {
        this.idViaggio = idViaggio;
        this.destinazione = destinazione;
        this.dataOraPartenza = dataOraPartenza;
        this.posti = posti;
        this.prezzo = prezzo;
        this.guidatore = guidatore;
        this.prenotabile = true;
        this.listaPasseggeri = new ArrayList<>();
    }

    //GETTER E SETTER

    public int getIdViaggio() {
        return idViaggio;
    }

    public void setIdViaggio(int idViaggio) {
        this.idViaggio = idViaggio;
    }

    public String getDestinazione() {
        return destinazione;
    }

    public void setDestinazione(String destinazione) {
        this.destinazione = destinazione;
    }

    public Date getDataOraPartenza() {
        return dataOraPartenza;
    }

    public void setDataOraPartenza(Date dataOraPartenza) {
        this.dataOraPartenza = dataOraPartenza;
    }

    public int getPosti() {
        return posti;
    }

    public void setPosti(int posti) {
        this.posti = posti;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public boolean isPrenotabile() {
        return prenotabile;
    }

    public void setPrenotabile(boolean prenotabile) {
        this.prenotabile = prenotabile;
    }

    public Utente getGuidatore() {
        return guidatore;
    }

    public void setGuidatore(Utente guidatore) {
        this.guidatore = guidatore;
    }

    public List<Utente> getListaPasseggeri() {
        return listaPasseggeri;
    }

    public void setListaPasseggeri(List<Utente> listaPasseggeri) {
        this.listaPasseggeri = listaPasseggeri;
    }
}
