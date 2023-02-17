package com.saga.unipass.model.beans;

/**
 * @author SAGA
 *
 * La classe "Veicolo" mantiene le informazione di un Veicolo.
 * Inoltre, la medesima classe offre dei metodi utili per la gestione dei veicoli.
 *
 */

public class Veicolo {

    /**
     * La targa del Veicolo
     */
    private String targa;

    /**
     * La marca del Veicolo
     */
    private String marca;

    /**
     * Il modello del Veicolo
     */
    private String modello;

    /**
     * Il colore del Veicolo
     */
    private String colore;

    /**
     * I posti disponibili del Veicolo
     */
    private int postiDisponibili;

    /**
     * il proprietario del Veicolo
     */
    private Utente proprietario;

    /**
     * Costruttore senza parametri
     */
    public Veicolo(){
        this.proprietario = null;
    }

    /**
     * Costruttore con parametri
     * @param targa La targa del Veicolo
     * @param marca La marca del Veicolo
     * @param modello Il modello del Veicolo
     * @param colore Il colore del Veicolo
     * @param postiDisponibili I posti disponibili del Veicolo
     * @param proprietario Il proprietario del Veicolo
     */
    public Veicolo(String targa, String marca, String modello, String colore, int postiDisponibili, Utente proprietario) {
        this.targa = targa;
        this.marca = marca;
        this.modello = modello;
        this.colore = colore;
        this.postiDisponibili = postiDisponibili;
        this.proprietario = proprietario;
    }

    /**
     * Getter per la targa del Veicolo
     * @return targa La targa del Veicolo
     */
    public String getTarga() {
        return targa;
    }

    /**
     * Setter per la targa del Veicolo
     * @param targa La targa del Veicolo
     */
    public void setTarga(String targa) {
        this.targa = targa;
    }

    /**
     * Getter per la marca del Veicolo
     * @return marca La marca del Veicolo
     */
    public String getMarca() {
        return marca;
    }

    /**
     * Setter per la marca del Veicolo
     * @param marca La marca del Veicolo
     */
    public void setMarca(String marca) {
        this.marca = marca;
    }

    /**
     * Getter per il modello del Veicolo
     * @return modello Il modello del Veicolo
     */
    public String getModello() {
        return modello;
    }

    /**
     * Setter per il modello del Veicolo
     * @param modello Il modello del Veicolo
     */
    public void setModello(String modello) {
        this.modello = modello;
    }

    /**
     * Getter per il colore del Veicolo
     * @return colore Il colore del Veicolo
     */
    public String getColore() {
        return colore;
    }

    /**
     * Setter per il colore del Veicolo
     * @param colore Il colore del Veicolo
     */
    public void setColore(String colore) {
        this.colore = colore;
    }

    /**
     * Getter per i posti del Veicolo
     * @return postiDisponibili I posti disponibili del Veicolo
     */
    public int getPostiDisponibili() {
        return postiDisponibili;
    }

    /**
     * Setter per i posti del Veicolo
     * @param postiDisponibili I posti disponibili del Veicolo
     */
    public void setPostiDisponibili(int postiDisponibili) {
        this.postiDisponibili = postiDisponibili;
    }

    /**
     * Getter per il proprietario del Veicolo
     * @return proprietario Il proprietario del Veicolo
     */
    public Utente getProprietario() {
        return proprietario;
    }

    /**
     * Setter per il proprietario del Veicolo
     * @param proprietario Il proprietario del Veicolo
     */
    public void setProprietario(Utente proprietario) {
        this.proprietario = proprietario;
    }
}
