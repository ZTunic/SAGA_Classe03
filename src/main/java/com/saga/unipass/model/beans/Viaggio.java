package com.saga.unipass.model.beans;

import java.util.Date;
import java.util.ArrayList;


/**
 * @author SAGA
 *
 * La classe "Viaggio" mantiene le informazione di un Viaggio.
 * Inoltre, la medesima classe offre dei metodi utili per la gestione dei viaggi.
 *
 */

public class Viaggio {

    /**
     * L'id del Viaggio
     */
    private int idViaggio;

    /**
     * Il luogo di destinazione del Viaggio
     */
    private String destinazione;

    /**
     * La data e l'ora di partenza del Viaggio
     */
    private Date dataOraPartenza;

    /**
     * I posti disponibili per il Viaggio
     */
    private int posti;

    /**
     * Il prezzo in euro da corrispondere al Guidatore
     */
    private double prezzo;

    /**
     * Indica se il Viaggio è prenotabile o no
     */
    private boolean prenotabile;

    /**
     * Il Guidatore il quale ha creato il Viaggio
     */
    private Utente guidatore;

    /**
     * La lista dei passeggeri del Viaggio
     */
    private ArrayList<Utente> listaPasseggeri;

    /**
     * <h2>Costruttori della classe</h2>
     */

    /**
     * Costruttore senza parametri
     */
    public Viaggio() {
        this.prenotabile = true;
        this.guidatore = null;
        this.listaPasseggeri = new ArrayList<>();
    }

    /**
     * Costruttore con parametri
     * @param destinazione La destinazione del Viaggio
     * @param dataOraPartenza La data e l'ora di partenza del Viaggio
     * @param posti I posti disponibili del Viaggio
     * @param prezzo Il prezzo da pagare al Guidatore
     * @param guidatore Il guidatore, colui che ha creato il Viaggio
     */
    public Viaggio(String destinazione, Date dataOraPartenza, int posti, double prezzo, Utente guidatore) {
        this.destinazione = destinazione;
        this.dataOraPartenza = dataOraPartenza;
        this.posti = posti;
        this.prezzo = prezzo;
        this.guidatore = guidatore;
        this.prenotabile = true;
        this.listaPasseggeri = new ArrayList<>();
    }

    /**
     * Getter per l'id del Viaggio
     * @return idViaggio L'id del Viaggio
     */
    public int getIdViaggio() {
        return idViaggio;
    }

    /**
     * Setter per l'id del Viaggio
     * @param idViaggio L'id del Viaggio
     */
    public void setIdViaggio(int idViaggio) {
        this.idViaggio = idViaggio;
    }

    /**
     * Getter per la destinazione del Viaggio
     * @return destinazione La destinazione del Viaggio
     */
    public String getDestinazione() {
        return destinazione;
    }

    /**
     * Setter per la destinazione del Viaggio
     * @param destinazione La destinazione del Viaggio
     */
    public void setDestinazione(String destinazione) {
        this.destinazione = destinazione;
    }

    /**
     * Getter per la data e l'ora di partenza del Viaggio
     * @return dataOraPartenza La data e l'ora di partenza del Viaggio
     */
    public Date getDataOraPartenza() {
        return dataOraPartenza;
    }

    /**
     * Setter per la data e l'ora di partenza del Viaggio
     * @param dataOraPartenza La data e l'ora di partenza del Viaggio
     */
    public void setDataOraPartenza(Date dataOraPartenza) {
        this.dataOraPartenza = dataOraPartenza;
    }

    /**
     * Getter per i posti disponibili del Viaggio
     * @return posti Il numero di posti disponibili
     */
    public int getPosti() {
        return posti;
    }

    /**
     * Setter per i posti disponibili del Viaggio
     * @param posti Il numero di posti disponibili
     */
    public void setPosti(int posti) {
        this.posti = posti;
    }

    /**
     * Getter per il prezzo del Viaggio
     * @return prezzo Il prezzo del Viaggio
     */
    public double getPrezzo() {
        return prezzo;
    }

    /**
     * Setter per il prezzo del Viaggio
     * @param prezzo Il prezzo del Viaggio
     */
    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    /**
     * Getter per la verifica della disponibilità della prenotazione
     * @return preonotabile True/False in base a se il viaggio è prenotabile o no
     */
    public boolean isPrenotabile() {
        return prenotabile;
    }

    /**
     * Setter per la prenotazione del Viaggio
     * @param prenotabile Booleano indicante lo stato del Viaggio
     */
    public void setPrenotabile(boolean prenotabile) {
        this.prenotabile = prenotabile;
    }

    /**
     * Getter per il Guidatore del Viaggio
     * @return guidatore Il Guidatore del Viaggio
     */
    public Utente getGuidatore() {
        return guidatore;
    }

    /**
     * Setter per il Guidatore del Viaggio
     * @param guidatore Il Guidatore del Viaggio
     */
    public void setGuidatore(Utente guidatore) {
        this.guidatore = guidatore;
    }

    /**
     * Getter per la lista dei passeggeri
     * @return listaPasseggeri La lista dei Passeggeri prenotati al Viaggio
     */
    public ArrayList<Utente> getListaPasseggeri() {
        return listaPasseggeri;
    }

    /**
     * Setter per la lista dei passeggeri
     * @param listaPasseggeri La lista dei Passeggeri prenotati al Viaggio
     */
    public void setListaPasseggeri(ArrayList<Utente> listaPasseggeri) {
        this.listaPasseggeri = listaPasseggeri;
    }
}
